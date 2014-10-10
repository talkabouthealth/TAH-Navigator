package nav.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import play.db.jpa.JPA;
import util.JPAUtil;
import models.AppointmentAlertDTO;
import models.AppointmentDTO;
import models.DistressAlertDTO;
import models.PatientDistressDTO;
import models.PatientRadiationTreatmentDTO;
import models.PatientSurgeryInfoDTO;
import models.UserDTO;
import models.UserDetailsDTO;

import com.sailthru.client.SailthruClient;
import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.params.Send;
import com.sun.mail.util.MailSSLSocketFactory;




public class PatientAlert {
	public static final String SAILTHRU_API_KEY = "4007bc4d4b48586353eb44012172eaf3";
	public static final String SAILTHRU_API_SECRET = "4ba0a437f0f138fceba76dac5c33e567";
	
	public static final String DISTRESS = "DISTRESS";
	public static final String APPOINTMENT = "APPOINTMENT";
	
	public static final String EMAIL = "EMAIL";
	//Distress Mail in Radiation Treatment
	public static final String DISTRESS_ALERT_IN_RT = "DISTRESS_ALERT_IN_RT";
	//Mail Sent As Distress Info is Not filled after Mail
	public static final String DISTRESS_ALERT_FOR_UNFILLED = "DISTRESS_ALERT_FOR_UNFILLED";
	//Distress Mail After Radiation Treatment
	public static final String DISTRESS_ALERT_AFTER_RT = "DISTRESS_ALERT_AFTER_RT";
	//Appointment Reminder After 1 hour from Scheduled
	public static final String APPOINTMENT_ALERT_SETUP = "APPOINTMENT_ALERT_SETUP";
	//Appointment Reminder Before 1 Week
	public static final String APPOINTMENT_ALERT_BEFORE_WEEK = "APPOINTMENT_ALERT_BEFORE_WEEK";
	//Appointment Reminder Before Day at 7 am
	public static final String APPOINTMENT_ALERT_BEFORE_DAY = "APPOINTMENT_ALERT_BEFORE_DAY";
	
	public static final Long PATIENT_TYPE_ID = 1L;
	public static final int TREATMENT_PROGRESS_CHECKIN_ID = 10;
	public static final Long ONE_WEEK = 604800000L;
	public static final Long TWO_WEEK = 1209600000L;
	public static final Long ONE_DAY = 86400000L;
	public static final Long ONE_HOUR = 3600000L;
	public static final Long TWO_HOUR = 7200000L;
	
	
	
	public static List<UserDetailsDTO> getAllPatient() {
		EntityManager em = JPAUtil.getEntityManager();		
		List<UserDetailsDTO> patients = null;
		TypedQuery<UserDetailsDTO> uQuery = em.createQuery("SELECT u FROM UserDetailsDTO u WHERE u.user.usertypeid.id = :typeId", UserDetailsDTO.class);
		uQuery.setParameter("typeId", PatientAlert.PATIENT_TYPE_ID);			
		try {
			patients = uQuery.getResultList();
		} catch (NoResultException e) {
			//e.printStackTrace();
		}
		return patients;
	}
	
	public static boolean onRadiationTreament(Integer patientId) {
		boolean answer = false;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<PatientRadiationTreatmentDTO> prtQuery = em.createQuery("SELECT p FROM PatientRadiationTreatmentDTO p WHERE p.userId = :userId AND current_date BETWEEN p.startDate AND p.endDate", PatientRadiationTreatmentDTO.class);
		prtQuery.setParameter("userId", patientId);
				
		try {
			int size = prtQuery.getResultList().size();
			if (size > 0) {
				answer = true;
			}
		} catch (NoResultException e) {
			//e.printStackTrace();
		}
		//System.out.println("On RadiationTreatment " + patientId + ": " + answer );
		return answer;
	}
	
	public static String appointmentAlertInfo(AppointmentDTO aDto) {				
		String alertDetail = null;		
		List<AppointmentAlertDTO> alerts = null;
		AppointmentAlertDTO scheduledAlert = null;
		AppointmentAlertDTO beforeWeekAlert = null;
		AppointmentAlertDTO beforeDayAlert = null;
		EntityManager em = JPAUtil.getEntityManager();
		
		Date appointmentDate = aDto.getAppointmentdate();
		Date today = new Date();
		long diff = appointmentDate.getTime() - today.getTime();
		long diff1 = today.getTime() - aDto.getAddedon().getTime(); 
		
		if (diff < 0) {
			return alertDetail;
		}
		
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.id = :appointmentId", AppointmentAlertDTO.class);
		alertQuery.setParameter("appointmentId", aDto.getId());
		try {
			alerts = alertQuery.getResultList();			
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		if (alerts != null && alerts.size() > 0) {
			for (AppointmentAlertDTO alert : alerts) {
				if (alert.getAlertDetail().equalsIgnoreCase(APPOINTMENT_ALERT_SETUP)) {
					scheduledAlert = alert;
				}
				else if (alert.getAlertDetail().equalsIgnoreCase(APPOINTMENT_ALERT_BEFORE_WEEK)) {
					beforeWeekAlert = alert;
				}
				else if (alert.getAlertDetail().equalsIgnoreCase(APPOINTMENT_ALERT_BEFORE_DAY)) {
					beforeDayAlert = alert;
				}
			}
		}
		
		if (scheduledAlert == null && diff1 >= ONE_HOUR && diff1 <= TWO_HOUR) {
			alertDetail = APPOINTMENT_ALERT_SETUP;			
		}
		else if (beforeWeekAlert == null && diff >= ONE_WEEK && diff <= (ONE_WEEK + ONE_DAY) ) {
			alertDetail = APPOINTMENT_ALERT_BEFORE_WEEK;			
		}
		else if (beforeDayAlert == null && diff > (20 * ONE_HOUR) && diff <= ONE_DAY) {
			alertDetail = APPOINTMENT_ALERT_BEFORE_DAY;			
		}
		return alertDetail;
	}
	
	public static String distressAlertInfo(Integer patientId) {
		DateFormat df = DateFormat.getDateInstance();
		String alertDetail = null;		
		EntityManager em = JPAUtil.getEntityManager();
		
		Date signupDate = null;
		Date lastDistressChecked = null;
		Date tpCheckInDate = null;
		DistressAlertDTO lastDistressMail = null;
		TypedQuery<UserDetailsDTO> udQuery = em.createQuery("SELECT u FROM UserDetailsDTO u WHERE u.id = :id", UserDetailsDTO.class);
		udQuery.setParameter("id", patientId.intValue());
		try {
			UserDetailsDTO udDto = udQuery.getSingleResult();
			signupDate = udDto.getCreatedate();
		} catch (NoResultException e) {
			//e.printStackTrace();
		}
		
		TypedQuery<DistressAlertDTO> alertQuery = em.createQuery("SELECT d FROM DistressAlertDTO d WHERE d.userId = :userId ORDER BY d.alertSent DESC", DistressAlertDTO.class);
		alertQuery.setParameter("userId", patientId);				
		try {
			List<DistressAlertDTO> alerts = alertQuery.getResultList();
			if (alerts.size() > 0) {
				lastDistressMail = alerts.get(0);
			}
		} catch (NoResultException e) {
			//e.printStackTrace();
		}
		
		TypedQuery<PatientDistressDTO> pdQuery = em.createQuery("SELECT p FROM PatientDistressDTO p WHERE p.user.id = :userId ORDER BY p.daterecrded DESC", PatientDistressDTO.class);
		pdQuery.setParameter("userId", patientId);
				
		try {
			List<PatientDistressDTO> pdDtos = pdQuery.getResultList();
			if (pdDtos.size() > 0) {
				lastDistressChecked = pdDtos.get(0).getDaterecrded();
			}
		} catch (NoResultException e) {
			//e.printStackTrace();
		}
		
		TypedQuery<AppointmentDTO> apQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.patientid.id = :userId AND a.appointmentid.id = :purposeId ORDER BY a.appointmentdate DESC", AppointmentDTO.class);
		apQuery.setParameter("userId", patientId);
		apQuery.setParameter("purposeId", PatientAlert.TREATMENT_PROGRESS_CHECKIN_ID);		
		try {
			List<AppointmentDTO> appointments = apQuery.getResultList();
			if (appointments.size() > 0) {
				tpCheckInDate = appointments.get(0).getAppointmentdate();
			}
		} catch (NoResultException e) {
			//e.printStackTrace();
		}
		
		Calendar today = Calendar.getInstance();
		Date todayDate = today.getTime();
		if (tpCheckInDate != null) {
			long tpdiff = todayDate.getTime() - tpCheckInDate.getTime() + (9 * ONE_WEEK);			
			if (tpdiff > 0) {
				return alertDetail;
			}
		}		
		// If Distress is Not Checked Within 1 Day
		if (lastDistressMail != null && lastDistressChecked != null) {
			//System.out.println("Distress: " + df.format(lastDistressChecked));
			Date mailSent = lastDistressMail.getAlertSent();
			
			if (lastDistressChecked.compareTo(mailSent) < 0) {				
				if ((todayDate.getTime() - mailSent.getTime()) > ONE_DAY) {					
					alertDetail = PatientAlert.DISTRESS_ALERT_FOR_UNFILLED;					
				}
			}
		}
		
		if ((alertDetail == null) && (signupDate != null || lastDistressMail != null)) {
			//System.out.println("Signup: " + df.format(signupDate));
			if (lastDistressMail != null) {
				//System.out.println("Last Mail: " + df.format(lastDistressMail.getAlertSent()));
			}
			Calendar tmp = Calendar.getInstance();
			if (lastDistressMail != null) {
				tmp.setTime(lastDistressMail.getAlertSent());
			}
			else {
				tmp.setTime(signupDate);
			}
			long diff = today.getTimeInMillis() - tmp.getTimeInMillis();
			//System.out.println("Diff: " + diff);
			boolean isRtOn = PatientAlert.onRadiationTreament(patientId);
			if (isRtOn) {
				if (diff > ONE_WEEK) {					
					alertDetail = PatientAlert.DISTRESS_ALERT_IN_RT;					
				}
			}
			else if (diff > TWO_WEEK) {				
				alertDetail = PatientAlert.DISTRESS_ALERT_AFTER_RT;				
			}			
		}
		return alertDetail;
	}
	
	public static void saveDistressAlertInfo(Integer userId, String alertThru, String alertDetail, Date alertSent) {
		DistressAlertDTO dto = new DistressAlertDTO();
		dto.setUserId(userId);
		dto.setAlertThru(alertThru);		
		dto.setAlertDetail(alertDetail);
		dto.setAlertSent(alertSent);
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(dto);
		em.getTransaction().commit();
	}
	public static void saveAppointmentAlertInfo(Integer appointmentId, String alertThru, String alertDetail, Date alertSent) {
		AppointmentAlertDTO dto = new AppointmentAlertDTO();
		dto.setAppointmentId(appointmentId);
		dto.setAlertThru(alertThru);
		dto.setAlertDetail(alertDetail);
		dto.setAlertSent(alertSent);
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(dto);
		em.getTransaction().commit();
	}
	public static void emailDistressAlert(UserDetailsDTO user, String alertDetail) {
		//saveDistressAlertInfo(user.getUser().getId(), EMAIL, alertDetail, new Date());		
		SailthruClient client = new SailthruClient(PatientAlert.SAILTHRU_API_KEY, PatientAlert.SAILTHRU_API_SECRET);				
		try {
            Send send = new Send();
            Map<String, Object> vars = new HashMap<String, Object>();
            
            if (alertDetail.equalsIgnoreCase(DISTRESS_ALERT_IN_RT) || alertDetail.equalsIgnoreCase(DISTRESS_ALERT_AFTER_RT)) {
            	send.setTemplate("Moffitt-Distress-Checkin");
            	vars.put("username", user.getFirstName());
            }
            else if (alertDetail.equalsIgnoreCase(DISTRESS_ALERT_FOR_UNFILLED)) {
            	send.setTemplate("Moffitt-Distress-Checkin-2");
            	vars.put("username", user.getFirstName());
            }
            send.setEmail(user.getUser().getEmail());
            send.setVars(vars);            
            JsonResponse response = client.send(send);
            if (response.isOK()) {
            	saveDistressAlertInfo(user.getUser().getId(), EMAIL, alertDetail, new Date());
                //System.out.println(response.getResponse());
            } else {
                //System.out.println(response.getResponse().get("error").toString());
            }
        } catch (ApiException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }        
	}
	
	public static void emailAppointmentAlert(UserDetailsDTO user, AppointmentDTO appointment, String alertDetail) {
		//saveAppointmentAlertInfo(appointment.getId(), EMAIL, alertDetail, new Date());		
		SailthruClient client = new SailthruClient(PatientAlert.SAILTHRU_API_KEY, PatientAlert.SAILTHRU_API_SECRET);		
		SimpleDateFormat smf = new SimpleDateFormat("M/dd/yyyy");
		try {
            Send send = new Send();       
            Map<String, Object> vars = new HashMap<String, Object>();
            if (alertDetail.equalsIgnoreCase(APPOINTMENT_ALERT_SETUP)) {
            	send.setTemplate("Moffitt-Appointment-Reminder-As-Soon-As-Scheduled");
            	vars.put("username", user.getFirstName());
            	vars.put("date", smf.format(appointment.getAppointmentdate()));
            	vars.put("appointment-time", appointment.getAppointmenttime());
            }
            else if (alertDetail.equalsIgnoreCase(APPOINTMENT_ALERT_BEFORE_WEEK)) {
            	send.setTemplate("Moffitt-Appointment-Reminder-1-week-before");
            	vars.put("username", user.getFirstName());
            	vars.put("date", smf.format(appointment.getAppointmentdate()));
            	vars.put("appointment-time", appointment.getAppointmenttime());
            }
            else if (alertDetail.equalsIgnoreCase(APPOINTMENT_ALERT_BEFORE_DAY)) {
            	send.setTemplate("Moffitt-Appointment-Reminder-1-day-before");
            	vars.put("username", user.getFirstName());            	
            	vars.put("appointment-time", appointment.getAppointmenttime());
            }
            send.setEmail(user.getUser().getEmail());
            send.setVars(vars);            
            JsonResponse response = client.send(send);
            if (response.isOK()) {
            	saveAppointmentAlertInfo(appointment.getId(), EMAIL, alertDetail, new Date());
                //System.out.println(response.getResponse());
            } else {
                //System.out.println(response.getResponse().get("error").toString());
            }
        } catch (ApiException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }
        
	}
	
	
	
}

package nav.dao;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import nav.dto.DistressBean;
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
	public static final String SECRET_KEY = "48r29WmD5YFB7ywvsLMPdQb63hKTqcXZSGkpxoCVRHEetfNjJUAuiz";
	
	public static final String EMAIL = "EMAIL";	
	
	// Appointment Steps, maintain character case for key and value
	public static final String APPOINTMENT_STEP_FIRST_APPOINTMENT = "First Appointment";
	public static final String APPOINTMENT_STEP_TREATMENT_DECISION = "Make Treatment Decision";
	public static final String APPOINTMENT_STEP_SIMULATION = "Simulation";
	public static final String APPOINTMENT_STEP_DURING_TREATMENTS = "Appointment During Treatments";
	public static final String APPOINTMENT_STEP_AFTER_TREATMENTS = "Post Treatment Appointment";
	
	// Reminder Type, maintain character case for key and value
	public static final String AR_FIRST_APPOINTMENT_AS_SOON_AS_SCHEDULED = "first_appointment_reminder_as_soon_as_scheduled";
	public static final String AR_FIRST_APPOINTMENT_ONE_DAY_BEFORE = "first_appointment_reminder_1_day_before";
	public static final String AR_TREATMENT_DECISION_ONE_DAY_BEFORE = "treatment_decision_reminder_1_day_before";
	public static final String AR_TREATMENT_DECISION_THREE_DAY_BEFORE = "treatment_decision_reminder_3_day_before";
	public static final String AR_SIMULATION_ONE_DAY_BEFORE = "simulation_reminder_1_day_before";
	public static final String AR_SIMULATION_THREE_DAY_BEFORE = "simulation_reminder_3_day_before";
	public static final String AR_DURING_TREATMENTS_ONE_DAY_BEFORE = "during_treatments_reminder_1_day_before";
	public static final String AR_DURING_TREATMENTS_THREE_DAY_BEFORE = "during_treatments_reminder_3_day_before";
	public static final String AR_AFTER_TREATMENTS_ONE_DAY_BEFORE = "after_treatments_reminder_1_day_before";
	public static final String AR_AFTER_TREATMENTS_ONE_WEEK_BEFORE = "after_treatments_reminder_1_week_before";
	
	
	public static final long ONE_DAY = 86400000L;
	public static final long ONE_HOUR = 3600000L;
	
	
	
	public static boolean sendEmail (Send send) {		
		SailthruClient client = new SailthruClient(SAILTHRU_API_KEY, SAILTHRU_API_SECRET);
		JsonResponse response;
		try {
			response = client.send(send);
			if (response.isOK()) {
	        	//System.out.println(response.getResponse());
	        	return true;	            
	        } else {
	            //System.out.println(response.getResponse().get("error").toString());	        	
	        }
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return false;		
	}
	
	public static void firstAppointmentScheduledAlert(UserDTO patient, AppointmentDTO appointment) {
		Date now = new Date();
		long curTime = now.getTime();
		Date appDate = appointment.getAppointmentdate();
		if (curTime < appDate.getTime()) {
			UserDetailsDTO userDetails = UserDAO.getDetailsById(patient.getId());
			String email = patient.getEmail();
			String firstName = userDetails.getFirstName();
			String appointmentTime = appointment.getAppointmenttime();
			String appointmentDate = new SimpleDateFormat("MM/dd/yyyy").format(appointment.getAppointmentdate());
			boolean success = emailAppointmentReminder_asSoonAsScheduled(email, firstName, appointmentDate, appointmentTime);							
			if (success) {
				int appointmentId = appointment.getId();
				logEmailAppointmentReminder(appointmentId, EMAIL, AR_FIRST_APPOINTMENT_AS_SOON_AS_SCHEDULED, new Date());
			}
		}
	}
	
	public static void firstAppointmentAlerts() {		
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();			
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_FIRST_APPOINTMENT);			
			alertQuery.setParameter("alertDetail", AR_FIRST_APPOINTMENT_ONE_DAY_BEFORE);
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}		
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			long minTime = appointmentDate.getTime() - ONE_DAY;
			long maxTime = appointmentDate.getTime() - (12 * ONE_HOUR);			
			if (curTime >= minTime && curTime <= maxTime ) {				
				boolean mailAlreadySent = false;
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();
					String appointmentTime = appointment.getAppointmenttime();
					String url = userId + "/" + getChecksum(userId);
					boolean success = emailAppointmentReminder_oneDayBefore(email, firstName, appointmentTime, url);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_FIRST_APPOINTMENT_ONE_DAY_BEFORE, new Date());
					}					
				}
			}
		}
	}
	
	public static void treatmentDecisionAlerts() {
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();			
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_TREATMENT_DECISION);			
			alertQuery.setParameter("alertDetail", AR_TREATMENT_DECISION_ONE_DAY_BEFORE);			
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			if (isDistressCheckedWithIn3Days(appointment.getPatientid(), appointmentDate)) {
				continue;
			}
			long minTime = appointmentDate.getTime() - ONE_DAY;
			long maxTime = appointmentDate.getTime() - (12 * ONE_HOUR);
			if (curTime >= minTime && curTime <= maxTime ) {
				boolean mailAlreadySent = false;
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();
					String appointmentTime = appointment.getAppointmenttime();
					String url = userId + "/" + getChecksum(userId);
					boolean success = emailAppointmentReminder_oneDayBefore(email, firstName, appointmentTime, url);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_TREATMENT_DECISION_ONE_DAY_BEFORE, new Date());
					}
				}
			}
		}
	}
	
	public static void treatmentDecision_beforeThreeDayAlerts() {
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();			
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_TREATMENT_DECISION);			
			alertQuery.setParameter("alertDetail", AR_TREATMENT_DECISION_THREE_DAY_BEFORE);
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			if (isDistressCheckedWithIn3Days(appointment.getPatientid(), appointmentDate)) {
				continue;
			}
			long minTime = appointmentDate.getTime() - (3 * ONE_DAY);
			long maxTime = appointmentDate.getTime() - (2 * ONE_DAY);
			if (curTime >= minTime && curTime <= maxTime ) {
				boolean mailAlreadySent = false;
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();	
					String url = userId + "/" + getChecksum(userId);
					String appointmentTime = appointment.getAppointmenttime();
					String date = new SimpleDateFormat("MM/dd/yyyy").format(appointmentDate);
					boolean success = emailDistressCheckin(email, firstName, url, date, appointmentTime);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_TREATMENT_DECISION_THREE_DAY_BEFORE, new Date());
					}
				}
			}
		}
	}
	
	public static void simulationAlerts() {
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_SIMULATION);
			alertQuery.setParameter("alertDetail", AR_SIMULATION_ONE_DAY_BEFORE);			
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			if (isDistressCheckedWithIn3Days(appointment.getPatientid(), appointmentDate)) {
				continue;
			}
			long minTime = appointmentDate.getTime() - ONE_DAY;
			long maxTime = appointmentDate.getTime() - (12 * ONE_HOUR);			
			if (curTime >= minTime && curTime <= maxTime ) {
				boolean mailAlreadySent = false;
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();
					String appointmentTime = appointment.getAppointmenttime();
					String url = userId + "/" + getChecksum(userId);
					boolean success = emailAppointmentReminder_oneDayBefore(email, firstName, appointmentTime, url);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_SIMULATION_ONE_DAY_BEFORE, new Date());
					}
				}
			}
		}
	}
	
	public static void simulation_beforeThreeDayAlerts() {
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_SIMULATION);
			alertQuery.setParameter("alertDetail", AR_SIMULATION_THREE_DAY_BEFORE);
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			if (isDistressCheckedWithIn3Days(appointment.getPatientid(), appointmentDate)) {
				continue;
			}
			long minTime = appointmentDate.getTime() - (3 * ONE_DAY);
			long maxTime = appointmentDate.getTime() - (2 * ONE_DAY);
			if (curTime >= minTime && curTime <= maxTime ) {
				boolean mailAlreadySent = false;
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();	
					String url = userId + "/" + getChecksum(userId);
					String appointmentTime = appointment.getAppointmenttime();
					String date = new SimpleDateFormat("MM/dd/yyyy").format(appointmentDate);
					boolean success = emailDistressCheckin(email, firstName, url, date, appointmentTime);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_SIMULATION_THREE_DAY_BEFORE, new Date());
					}
				}
			}
		}
	}
	
	//  check for distress check within 3 days before next appointment
	public static boolean isDistressCheckedWithIn3Days(UserDTO user, Date appointmentDate) {
		DistressBean distress = DistressDAO.getLastDistress(user);
		Date distressLastChecked = distress.getDistressDate();			
		long minTime = appointmentDate.getTime() - (3 * ONE_DAY);			
		long lastTime = distressLastChecked.getTime();
		// if distress reported in last 3 days before appointment, then email should not be sent
		if (lastTime >= minTime && lastTime <= appointmentDate.getTime()) {
			return true;
		}
		return false;
	}
	
	
	public static void duringTreatmentAlerts() {
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_DURING_TREATMENTS);
			alertQuery.setParameter("alertDetail", AR_DURING_TREATMENTS_ONE_DAY_BEFORE);			
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			if (isDistressCheckedWithIn3Days(appointment.getPatientid(), appointmentDate)) {
				continue;
			}						
			long minTime = appointmentDate.getTime() - ONE_DAY;
			long maxTime = appointmentDate.getTime() - (12 * ONE_HOUR);															
			if (curTime >= minTime && curTime <= maxTime ) {
				boolean mailAlreadySent = false;
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();
					String appointmentTime = appointment.getAppointmenttime();
					String url = userId + "/" + getChecksum(userId);
					boolean success = emailAppointmentReminder_oneDayBefore(email, firstName, appointmentTime, url);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_DURING_TREATMENTS_ONE_DAY_BEFORE, new Date());
					}
				}
			}
		}
	}
	
	public static void duringTreatment_beforeThreeDayAlerts() {
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_DURING_TREATMENTS);
			alertQuery.setParameter("alertDetail", AR_DURING_TREATMENTS_THREE_DAY_BEFORE);
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			if (isDistressCheckedWithIn3Days(appointment.getPatientid(), appointmentDate)) {
				continue;
			}
			long minTime = appointmentDate.getTime() - (3 * ONE_DAY);
			long maxTime = appointmentDate.getTime() - (2 * ONE_DAY);
			if (curTime >= minTime && curTime <= maxTime ) {
				boolean mailAlreadySent = false;								
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}				
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();			
					String url = userId + "/" + getChecksum(userId);
					String appointmentTime = appointment.getAppointmenttime();
					String date = new SimpleDateFormat("MM/dd/yyyy").format(appointmentDate);
					boolean success = emailDistressCheckin(email, firstName, url, date, appointmentTime);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_DURING_TREATMENTS_THREE_DAY_BEFORE, new Date());
					}
				}
			}
		}
	}
	
	public static void afterTreatmentBeforeOneDayAlerts() {
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_AFTER_TREATMENTS);
			alertQuery.setParameter("alertDetail", AR_AFTER_TREATMENTS_ONE_DAY_BEFORE);
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			long minTime = appointmentDate.getTime() - ONE_DAY;
			long maxTime = appointmentDate.getTime() - (12 * ONE_HOUR);
			if (curTime >= minTime && curTime <= maxTime ) {
				boolean mailAlreadySent = false;
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();
					String appointmentTime = appointment.getAppointmenttime();
					String url = userId + "/" + getChecksum(userId);
					boolean success = emailAppointmentReminder_oneDayBefore(email, firstName, appointmentTime, url);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_AFTER_TREATMENTS_ONE_DAY_BEFORE, new Date());
					}
				}
			}
		}
	}
	
	public static void afterTreatmentBeforeOneWeekAlerts() {
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		List<AppointmentAlertDTO> sentAlerts = new ArrayList<AppointmentAlertDTO>();		
		TypedQuery<AppointmentDTO> appointmentQuery = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.treatementStep = :treatementStep AND a.appointmentdate > current_timestamp AND a.deleteflag = false", AppointmentDTO.class);
		TypedQuery<AppointmentAlertDTO> alertQuery = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		Date now = new Date();
		long curTime = now.getTime();
		try {
			appointmentQuery.setParameter("treatementStep", APPOINTMENT_STEP_AFTER_TREATMENTS);
			alertQuery.setParameter("alertDetail", AR_AFTER_TREATMENTS_ONE_WEEK_BEFORE);
			appointments = appointmentQuery.getResultList();
			sentAlerts = alertQuery.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		for (AppointmentDTO appointment : appointments) {
			Date appointmentDate = appointment.getAppointmentdate();
			long minTime = appointmentDate.getTime() - (7 * ONE_DAY);
			long maxTime = appointmentDate.getTime() - (6 * ONE_DAY);
			if (curTime >= minTime && curTime <= maxTime ) {
				boolean mailAlreadySent = false;
				for (AppointmentAlertDTO alert: sentAlerts) {
					if (alert.getAppointmentId().intValue() == appointment.getId()) {
						mailAlreadySent = true;
						break;
					}
				}
				if (!mailAlreadySent) {
					String userId = String.valueOf(appointment.getPatientid().getId());
					UserDetailsDTO userDetails = UserDAO.getDetailsById(userId);
					String email = userDetails.getUser().getEmail();
					String firstName = userDetails.getFirstName();
					String appointmentTime = appointment.getAppointmenttime();
					String date = new SimpleDateFormat("MM/dd/yyyy").format(appointmentDate);
					boolean success = emailAppointmentReminder_oneWeekBefore(email, firstName, date, appointmentTime);
					if (success) {
						logEmailAppointmentReminder(appointment.getId(), EMAIL, AR_AFTER_TREATMENTS_ONE_WEEK_BEFORE, new Date());
					}
				}
			}
		}
	}
	
	
	public static void logEmailAppointmentReminder(Integer appointmentId, String alertThru, String alertDetail, Date alertSent) {		
		EntityManager em = JPAUtil.getEntityManager();
		AppointmentAlertDTO dto = new AppointmentAlertDTO();
		dto.setAppointmentId(appointmentId);
		dto.setAlertThru(alertThru);
		dto.setAlertDetail(alertDetail);
		dto.setAlertSent(alertSent);
		em.getTransaction().begin();
		em.persist(dto);
		em.getTransaction().commit();		
	}
	
	public static boolean emailAppointmentReminder_asSoonAsScheduled(String email, String ph1, String ph2, String ph3) {
		String TPL = "Moffitt-Appointment-Reminder-As-Soon-As-Scheduled";
		String PH1 = "username";
		String PH2 = "date";
		String PH3 = "appointment_time";				     
		Map<String, Object> vars = new HashMap<String, Object>();		
		Send send = new Send(); 
		send.setTemplate(TPL);
		vars.put(PH1, ph1);
		vars.put(PH2, ph2);
		vars.put(PH3, ph3);
		send.setEmail(email);
        send.setVars(vars);            
        return sendEmail(send); 		
	}
	public static boolean emailAppointmentReminder_oneDayBefore(String email, String ph1, String ph2, String ph3) {
		String TPL = "Moffitt-Appointment-Reminder-1-Day-Before";
		String PH1 = "username";
		String PH2 = "appointment_time";
		String PH3 = "generate_url";
		Map<String, Object> vars = new HashMap<String, Object>();		
		Send send = new Send(); 
		send.setTemplate(TPL);
		vars.put(PH1, ph1);
		vars.put(PH2, ph2);
		vars.put(PH3, ph3);
		send.setEmail(email);
        send.setVars(vars);            
        return sendEmail(send); 		
	}
	public static boolean emailAppointmentReminder_oneWeekBefore(String email, String ph1, String ph2, String ph3) {
		String TPL = "Moffitt-Appointment-Reminder-1-Week-Before";
		String PH1 = "username";
		String PH2 = "date";
		String PH3 = "appointment_time";		
		Map<String, Object> vars = new HashMap<String, Object>();		
		Send send = new Send(); 
		send.setTemplate(TPL);
		vars.put(PH1, ph1);
		vars.put(PH2, ph2);
		vars.put(PH3, ph3);
		send.setEmail(email);
        send.setVars(vars);            
        return sendEmail(send); 		
	}	
	public static String byteArrayToHex(byte[] a) {
	   StringBuilder sb = new StringBuilder(a.length * 2);
	   for(byte b: a)
	      sb.append(String.format("%02x", b & 0xff));
	   return sb.toString();
	}
	public static String getChecksum(String userId) {
		MessageDigest md;
		String str = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			String key = userId + "_" + SECRET_KEY;
			byte [] sha256 = md.digest(key.getBytes());
			str = byteArrayToHex(sha256);			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	public static boolean emailDistressCheckin(String email, String ph1, String ph2, String ph3, String ph4) {
		String TPL = "Moffitt-Distress-Checkin-RT-3Days-Before-Appointment";
		String PH1 = "username";
		String PH2 = "generate_url";
		String PH3 = "date";
		String PH4 = "appointment_time";
		Map<String, Object> vars = new HashMap<String, Object>();		
		Send send = new Send(); 
		send.setTemplate(TPL);
		vars.put(PH1, ph1);
		vars.put(PH2, ph2);
		vars.put(PH3, ph3);
		vars.put(PH4, ph4);
		send.setEmail(email);
        send.setVars(vars);          
        return sendEmail(send); 		
	}
}

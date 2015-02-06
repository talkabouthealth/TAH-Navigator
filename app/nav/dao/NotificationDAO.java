package nav.dao;

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

import nav.dto.CareMember;
import nav.dto.DistressBean;
import nav.dto.TemplateVars;
import models.AddressDTO;
import models.AppointmentDTO;
import models.CareTeamMemberDTO;
import models.InvitedDTO;
import models.NotificationDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import util.EmailUtil;
import util.JPAUtil;
import util.TemplateExtensions;

public class NotificationDAO {
	public static Integer COMMUNICATION_EMAIL = 1;
	public static boolean DEBUG = false;
	public static String SECRET_KEY = "48r29WmD5YFB7ywvsLMPdQb63hKTqcXZSGkpxoCVRHEetfNjJUAuiz";
	
	public static String APPOINTMENT = "APPOINTMENT";
	public static String INVITATION = "INVITATION";
	
	
	// Invitation
	public static String INVITED_APPOINTMENT_FIRST_MAIL = "INVITED_APPOINTMENT_FIRST_MAIL";
	public static String INVITED_APPOINTMENT_SECOND_MAIL = "INVITED_APPOINTMENT_SECOND_MAIL";
	public static String INVITED_APPOINTMENT_THIRD_MAIL = "INVITED_APPOINTMENT_THIRD_MAIL";	
	public static String INVITED_FIRST_MAIL = "INVITED_FIRST_MAIL";
	public static String INVITED_SECOND_MAIL = "INVITED_SECOND_MAIL";
	public static String INVITED_THIRD_MAIL = "INVITED_THIRD_MAIL";
	
	// Radiation Oncology Appointment
	public static String APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_FIRST_MAIL = "FIRST_APPOINTMENT_WITH_RADIATION_ONCOLOGIST_FIRST_MAIL";
	public static String APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_REMINDER_MAIL = "FIRST_APPOINTMENT_WITH_RADIATION_ONCOLOGIST_REMINDER_MAIL";	
	public static String APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL = "MAKE_TREATMENT_DECISION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL = "MAKE_TREATMENT_DECISION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL";	
	public static String APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL = "SIMULATION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL = "SIMULATION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL";	
	public static String APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL = "ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL = "ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL";	
	public static String APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL = "END_OF_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL = "END_OF_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL";	
	public static String APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_SEVEN_DAYS_BEFORE_MAIL = "ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_SEVEN_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL = "ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL = "ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL";
	
	// Sugery Appointment
	public static String APPOINTMENT_FIRST_WITH_SURGEON_ONE_HOUR_AFTER_SCHUDULED = "FIRST_APPOINTMENT_WITH_SURGEON_ONE_HOUR_AFTER_SCHUDULED";
	public static String APPOINTMENT_FIRST_WITH_SURGEON_ONE_DAY_BEFORE_MAIL = "FIRST_APPOINTMENT_WITH_SURGEON_ONE_DAY_BEFORE_MAIL";	
	public static String APPOINTMENT_TREATMENT_DECISION_SURGERY_THREE_DAYS_BEFORE_MAIL = "MAKE_TREATMENT_DECISION_SURGERY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_TREATMENT_DECISION_SURGERY_ONE_DAY_BEFORE_MAIL = "MAKE_TREATMENT_DECISION_SURGERY_ONE_DAY_BEFORE_MAIL";	
	public static String APPOINTMENT_SURGERY_THREE_DAYS_BEFORE_MAIL = "SURGERY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_SURGERY_ONE_DAY_BEFORE_MAIL = "SURGERY_ONE_DAY_BEFORE_MAIL";
	public static String APPOINTMENT_SURGERY_ONE_DAY_AFTER_MAIL = "SURGERY_ONE_DAY_AFTER_MAIL";
	public static String APPOINTMENT_SURGERY_THREE_DAYS_AFTER_MAIL = "SURGERY_THREE_DAYS_AFTER_MAIL";
	public static String APPOINTMENT_SURGERY_SEVEN_DAYS_AFTER_MAIL = "SURGERY_SEVEN_DAYS_AFTER_MAIL";	
	public static String APPOINTMENT_FOLLOW_UP_SURGERY_THREE_DAYS_BEFORE_MAIL = "FOLLOW_UP_APPOINTMENT_SURGERY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_FOLLOW_UP_SURGERY_ONE_DAY_BEFORE_MAIL = "FOLLOW_UP_APPOINTMENT_SURGERY_ONE_DAY_BEFORE_MAIL";
	public static String APPOINTMENT_ONGOING_SURGERY_SEVEN_DAYS_BEFORE_MAIL = "ONGOING_APPOINTMENT_SURGERY_SEVEN_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_ONGOING_SURGERY_THREE_DAYS_BEFORE_MAIL = "ONGOING_APPOINTMENT_SURGERY_THREE_DAYS_BEFORE_MAIL";
	public static String APPOINTMENT_ONGOING_SURGERY_ONE_DAY_BEFORE_MAIL = "ONGOING_APPOINTMENT_SURGERY_ONE_DAY_BEFORE_MAIL";
		
	public static void setNotified(NotificationDTO notification) {	
		notification.setNotified(true);
		BaseDAO.update(notification);
		if (DEBUG) {
			System.out.println("Notified");
		}
	}
	
	public static void discardNotification(NotificationDTO notification) {		
		notification.setDiscard(true);
		BaseDAO.update(notification);
	}
	
	/*
	public static boolean isMailSentRecently(List<NotificationDTO> recentNotifications, Integer patientId) {
		boolean flag = false;
		for (NotificationDTO notification : recentNotifications) {
			if (notification.getNotifiedTo().equals(patientId)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	*/
	
	public static boolean isMailSentRecently(String category, Integer notifiedTo, Date compareWith) {
		EntityManager em = JPAUtil.getEntityManager();
		boolean flag = false;
		TypedQuery<NotificationDTO> query = em.createQuery("SELECT n FROM NotificationDTO n WHERE n.notified = :notified AND n.category = :category AND n.notifiedTo = :notifiedTo AND age(:compareWith, n.scheduledTime) >= '0 second' AND age(:compareWith, n.scheduledTime) < '2 day'", NotificationDTO.class);
		query.setParameter("notified", true);
		query.setParameter("category", category);
		query.setParameter("notifiedTo", notifiedTo);
		query.setParameter("compareWith", compareWith);
		try {			
			List<NotificationDTO> recentNotifications = query.getResultList();
			if (recentNotifications.size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean isDistressCheckedBeforeAppointment(Integer patientId, Date appointmentDate, int day) {
		UserDTO patient = UserDAO.getUserBasicByField("id", patientId);		
		DistressBean distress = DistressDAO.getLastDistress(patient);		
		Calendar cal = Calendar.getInstance();
		cal.setTime(appointmentDate);
		cal.add(Calendar.DAY_OF_MONTH, -day);
		if (distress != null) {
			if (distress.getDistressDate().after(cal.getTime())) {
				return true;
			}
		}
		return false;		
	}
	
	
	public static List<NotificationDTO> appointmentNotifications(Integer appointmentId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<NotificationDTO> notifications = new ArrayList<NotificationDTO>();		
		
		TypedQuery<NotificationDTO> query = em.createQuery("SELECT n FROM NotificationDTO n WHERE n.relatedId = :appointmentId AND n.category = :category", NotificationDTO.class);
		query.setParameter("appointmentId", appointmentId);
		query.setParameter("category", APPOINTMENT);
		try {			
			notifications = query.getResultList();			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return notifications;
	}
	
	public static void addEmailNotification(String category, Integer relatedId, String description, Date scheduleTime, Integer priority, Integer notifiedTo) {
		NotificationDTO notification = new NotificationDTO();			
		notification.setCategory(category);
		notification.setRelatedId(relatedId);
		notification.setCommunication(COMMUNICATION_EMAIL);
		notification.setDescription(description);			
		notification.setScheduledTime(scheduleTime);
		notification.setPriority(priority);
		notification.setNotifiedTo(notifiedTo);
		notification.setNotified(false);
		notification.setDiscard(false);
		BaseDAO.save(notification);
	}
	
	// First Appointment With Radiation Oncologist
	public static void scheduleAppointmentType01Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(now);
			cal.add(Calendar.HOUR_OF_DAY, 1);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_FIRST_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_REMINDER_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledReminderMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_FIRST_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_REMINDER_MAIL)) {
						scheduledReminderMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_FIRST_MAIL)) {
							cal.setTime(now);
							cal.add(Calendar.HOUR_OF_DAY, 1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_REMINDER_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(now);
					cal.add(Calendar.HOUR_OF_DAY, 1);
					if (now.before(cal.getTime())) {						
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_FIRST_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledReminderMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_REMINDER_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	// Make Treatment Decision (Radiation Oncology)
	public static void scheduleAppointmentType02Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {						
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	// Simulation (Radiation Oncology)
	public static void scheduleAppointmentType03Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	
	// On Treatment Visit (Radiation Oncology)
	public static void scheduleAppointmentType04Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	// End of Treatment Visit (Radiation Oncology)
	public static void scheduleAppointmentType05Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	// Ongoing Follow-up Appointment (Radiation Oncology)
	public static void scheduleAppointmentType06Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -7);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_SEVEN_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}			
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false, scheduledThirdMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_SEVEN_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
						scheduledThirdMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_SEVEN_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -7);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -7);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_SEVEN_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledThirdMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	// First Appointment With Surgeon
	public static void scheduleAppointmentType07Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(now);
			cal.add(Calendar.HOUR_OF_DAY, 1);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FIRST_WITH_SURGEON_ONE_HOUR_AFTER_SCHUDULED, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FIRST_WITH_SURGEON_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_SURGEON_ONE_HOUR_AFTER_SCHUDULED)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_SURGEON_ONE_DAY_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_SURGEON_ONE_HOUR_AFTER_SCHUDULED)) {
							cal.setTime(now);
							cal.add(Calendar.HOUR_OF_DAY, 1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_SURGEON_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(now);
					cal.add(Calendar.HOUR_OF_DAY, 1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FIRST_WITH_SURGEON_ONE_HOUR_AFTER_SCHUDULED, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FIRST_WITH_SURGEON_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	// Make Treatment Decision (Surgery)
	public static void scheduleAppointmentType08Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_TREATMENT_DECISION_SURGERY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_TREATMENT_DECISION_SURGERY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_SURGERY_ONE_DAY_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_SURGERY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_TREATMENT_DECISION_SURGERY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_TREATMENT_DECISION_SURGERY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	// Surgery
	public static void scheduleAppointmentType09Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_ONE_DAY_AFTER_MAIL, cal.getTime(), 20, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, 3);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_THREE_DAYS_AFTER_MAIL, cal.getTime(), 20, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, 7);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_SEVEN_DAYS_AFTER_MAIL, cal.getTime(), 20, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false, scheduledThirdMail = false, scheduledFourthMail = false, scheduledFifthMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_ONE_DAY_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_ONE_DAY_AFTER_MAIL)) {
						scheduledThirdMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_THREE_DAYS_AFTER_MAIL)) {
						scheduledFourthMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_SEVEN_DAYS_AFTER_MAIL)) {
						scheduledFifthMail = true;
					}
					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_ONE_DAY_AFTER_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, 1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_THREE_DAYS_AFTER_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, 3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_SEVEN_DAYS_AFTER_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, 7);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledThirdMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, 1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_ONE_DAY_AFTER_MAIL, cal.getTime(), 20, patientId);
					}
				}
				if (!scheduledFourthMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, 3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_THREE_DAYS_AFTER_MAIL, cal.getTime(), 20, patientId);
					}
				}
				if (!scheduledFifthMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, 7);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_SURGERY_SEVEN_DAYS_AFTER_MAIL, cal.getTime(), 20, patientId);
					}
				}
			}						
		}
	}
		
	// Follow-up Appointment (Surgery)
	public static void scheduleAppointmentType10Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {			
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FOLLOW_UP_SURGERY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FOLLOW_UP_SURGERY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false; 
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_FOLLOW_UP_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_FOLLOW_UP_SURGERY_ONE_DAY_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_FOLLOW_UP_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_FOLLOW_UP_SURGERY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FOLLOW_UP_SURGERY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_FOLLOW_UP_SURGERY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	// Ongoing Follow-up Appointment (Surgery)
	public static void scheduleAppointmentType11Emails(AppointmentDTO appointment, String op) {
		Integer patientId = appointment.getPatientid().getId();
		Integer appointmentId = appointment.getId();
		Date appointmentDate = appointment.getAppointmentdate();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (op.equalsIgnoreCase("add")) {
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -7);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_SURGERY_SEVEN_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -3);			
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_SURGERY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
			cal.setTime(appointmentDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (now.before(cal.getTime())) {				
				addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_SURGERY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
			}
		}
		else {
			List<NotificationDTO> notifications = appointmentNotifications(appointmentId);
			if (op.equalsIgnoreCase("remove") || (op.equalsIgnoreCase("edit") && appointmentDate.before(now))) {
				for (NotificationDTO notification : notifications) {
					if (!notification.getNotified() && !notification.getDiscard()) {
						discardNotification(notification);
					}
				}
			}
			else if (op.equalsIgnoreCase("edit")) {
				boolean scheduledFirstMail = false, scheduledSecondMail = false, scheduledThirdMail = false;
				for (NotificationDTO notification : notifications) {
					String description = notification.getDescription().trim();
					if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_SEVEN_DAYS_BEFORE_MAIL)) {
						scheduledFirstMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
						scheduledSecondMail = true;
					}
					if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_ONE_DAY_BEFORE_MAIL)) {
						scheduledThirdMail = true;
					}					
					if (!notification.getNotified() && !notification.getDiscard()) {
						if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_SEVEN_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -7);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -3);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_ONE_DAY_BEFORE_MAIL)) {
							cal.setTime(appointmentDate);
							cal.add(Calendar.DAY_OF_MONTH, -1);
							if (now.before(cal.getTime())) {
								notification.setScheduledTime(cal.getTime());
								BaseDAO.update(notification);
							}
							else {
								discardNotification(notification);
							}
						}
						else {
							discardNotification(notification);
						}
					}
				}
				if (!scheduledFirstMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -7);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_SURGERY_SEVEN_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledSecondMail) {					
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -3);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_SURGERY_THREE_DAYS_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
				if (!scheduledThirdMail) {
					cal.setTime(appointmentDate);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					if (now.before(cal.getTime())) {
						addEmailNotification(APPOINTMENT, appointmentId, APPOINTMENT_ONGOING_SURGERY_ONE_DAY_BEFORE_MAIL, cal.getTime(), 10, patientId);
					}
				}
			}						
		}
	}
	
	public static void scheduleAppointmentEmails(AppointmentDTO appointment, String op) {
		Date appointmentDate = appointment.getAppointmentdate();
		Date now = new Date();
		if (op.equalsIgnoreCase("add") &&  appointmentDate.before(now)) {
			return;
		}	
		
		String treatmentStep = appointment.getTreatementStep().trim();
		
		if (treatmentStep.equalsIgnoreCase("First Appointment With Radiation Oncologist")) {
			scheduleAppointmentType01Emails(appointment, op);			
		}
		else if (treatmentStep.equalsIgnoreCase("Make Treatment Decision (Radiation Oncology)")) {
			scheduleAppointmentType02Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("Simulation (Radiation Oncology)")) {
			scheduleAppointmentType03Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("On Treatment Visit (Radiation Oncology)")) {
			scheduleAppointmentType04Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("End of Treatment Visit (Radiation Oncology)")) {
			scheduleAppointmentType05Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("Ongoing Follow-up Appointment (Radiation Oncology)")) {
			scheduleAppointmentType06Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("First Appointment With Surgeon")) {
			scheduleAppointmentType07Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("Make Treatment Decision (Surgery)")) {
			scheduleAppointmentType08Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("Surgery")) {
			scheduleAppointmentType09Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("Follow-up Appointment (Surgery)")) {
			scheduleAppointmentType10Emails(appointment, op);
		}
		else if (treatmentStep.equalsIgnoreCase("Ongoing Follow-up Appointment (Surgery)")) {
			scheduleAppointmentType11Emails(appointment, op);
		}
	}
	
	public static void scheduleInviteEmails(InvitedDTO invitation, UserDTO user, boolean hasAppointment) {			
		Integer notifiedTo = null;		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();		
		if (user != null) {			
			notifiedTo = user.getId();
		}		
		if (hasAppointment) {			
			addEmailNotification(INVITATION, invitation.getId(), INVITED_APPOINTMENT_FIRST_MAIL, now, 0, notifiedTo);
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_MONTH, 2);
			addEmailNotification(INVITATION, invitation.getId(), INVITED_APPOINTMENT_SECOND_MAIL, cal.getTime(), 100, notifiedTo);
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_MONTH, 7);
			addEmailNotification(INVITATION, invitation.getId(), INVITED_APPOINTMENT_THIRD_MAIL, cal.getTime(), 100, notifiedTo);
		}
		else {			
			addEmailNotification(INVITATION, invitation.getId(), INVITED_FIRST_MAIL, now, 0, notifiedTo);
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_MONTH, 2);
			addEmailNotification(INVITATION, invitation.getId(), INVITED_SECOND_MAIL, cal.getTime(), 100, notifiedTo);
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_MONTH, 7);
			addEmailNotification(INVITATION, invitation.getId(), INVITED_THIRD_MAIL, cal.getTime(), 100, notifiedTo);
		}				
	}

	public static void scheduleInviteEmailOnce(InvitedDTO invitation, UserDTO user, boolean hasAppointment) {			
		Integer notifiedTo = null;
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		if (user != null) {
			notifiedTo = user.getId();
		}
		if (hasAppointment) {
			addEmailNotification(INVITATION, invitation.getId(), INVITED_APPOINTMENT_FIRST_MAIL, now, 0, notifiedTo);
		} else {
			addEmailNotification(INVITATION, invitation.getId(), INVITED_FIRST_MAIL, now, 0, notifiedTo);
		}
	}

	public static String byteArrayToHex(byte[] a) {
	   StringBuilder sb = new StringBuilder(a.length * 2);
	   for(byte b: a)
	      sb.append(String.format("%02x", b & 0xff));
	   return sb.toString();
	}
	
	public static String getChecksum(Integer userId) {
		MessageDigest md;
		String str = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			String key = userId.toString() + "_" + SECRET_KEY;
			byte [] sha256 = md.digest(key.getBytes());
			str = byteArrayToHex(sha256);			
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		}
		return str;
	}
	
	public static String getReferenceNo(NotificationDTO notification) {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String reference = df.format(now) + "-" + notification.getId();
		return reference;
	}
	
	public static TemplateVars getInviteTemplatesData(NotificationDTO notification) {
		TemplateVars data = new TemplateVars();	
		String email = null;
		String userName = null;
		String signupURL = null;			
		String clinicPhone = null;
		StringBuilder clinicAddress = new StringBuilder();
		String doctorName = null;
		String appointmentDate = null;
		String appointmentDay = null;
		String appointmentTime = null;				
		
		EntityManager em = JPAUtil.getEntityManager();
		InvitedDTO invited = null;
		TypedQuery<InvitedDTO> query = em.createQuery("SELECT o FROM InvitedDTO o WHERE o.id = :id", InvitedDTO.class);
		query.setParameter("id", notification.getRelatedId());
		try {
			invited = query.getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (invited != null) {		
			email = invited.getEmail();
			userName = invited.getFirstname() + " " + invited.getLastname();
			signupURL = "http://tvrhnavigator.com/invited-registration/" + invited.getId();			
			UserDTO doctor = invited.getCaremember();	 	
			AddressDTO address = invited.getAddressid();			
			if (address != null && address.getPhone() != null) {
		 		clinicPhone = address.getPhone();
		 	}
			if (clinicPhone == null && doctor != null) {		 	
			 	UserDetailsDTO details = null;
		    	TypedQuery<UserDetailsDTO> userQuery = em.createQuery("SELECT c FROM UserDetailsDTO c WHERE c.id = :id", UserDetailsDTO.class); 
		    	userQuery.setParameter("id", doctor.getId());
				try {
					details = userQuery.getSingleResult();
					if (details.getMobile() != null) {
			    		clinicPhone = details.getMobile();
			    	}
			    	else if (details.getHomePhone() != null){
			    		clinicPhone = details.getHomePhone();
			    	}
				} catch (NoResultException e1) {
					e1.printStackTrace();
				}		 	
			}
			if (clinicPhone == null && doctor != null) {
				CareTeamMemberDTO careTeamMember = null;
		    	TypedQuery<CareTeamMemberDTO> query1 = em.createQuery("SELECT c FROM CareTeamMemberDTO c WHERE c.member.id = :id", CareTeamMemberDTO.class); 
		    	query1.setParameter("id", doctor.getId());
		    	try {
		    		careTeamMember = query1.getSingleResult();
		    		AddressDTO teamAddress = careTeamMember.getCareteam().getAddress();
		    		if (teamAddress != null && teamAddress.getPhone() != null) {
		    	 		clinicPhone = teamAddress.getPhone();
		    	 	}
				} catch (NoResultException e1) {
					e1.printStackTrace();
				}
			}									
			if (doctor != null) {
				doctorName = TemplateExtensions.usreNameNew(invited.getCareMemberName(), doctor.getId()).toString();
			}
			else {
				doctorName = invited.getCareMemberName();
			}
			if (invited.getAppointmentdate() != null) {
				appointmentDate = new SimpleDateFormat("M/d/yyyy").format(invited.getAppointmentdate());				
				appointmentDay = new SimpleDateFormat("EEEE").format(invited.getAppointmentdate());
				
			}
			appointmentTime = invited.getAppointmenttime();			
			
			if (address != null) {				
				clinicAddress.append(address.getLine1());
				if (address.getLine2() != null && !address.getLine2().isEmpty()) {
					clinicAddress.append(", " + address.getLine2());
				}
				if (address.getCity() != null && !address.getCity().isEmpty()) {
					clinicAddress.append(", " + address.getCity());
				}
				if (address.getState() != null && !address.getState().isEmpty()) {
					clinicAddress.append(", " + address.getState());
				}
			}			
		}	
		
		data.setEmail(email);
		data.setUserName(userName);
		data.setSignupURL(signupURL);
		data.setAppointmentDate(appointmentDate);
		data.setAppointmentDay(appointmentDay);
		data.setAppointmentTime(appointmentTime);		
		data.setClinicPhone(clinicPhone);
		data.setClinicAddress(clinicAddress.toString());
		data.setDoctorName(doctorName);
		
		return data;
	}
	
	
	public static TemplateVars getAppointmentTemplatesData(NotificationDTO notification) {
		TemplateVars data = new TemplateVars();
		String email = null;
		String userName = null;		
		String distressCheckInURL;		
		String doctorName = null;
		String appointmentDate = null;
		String appointmentTime = null;
		String appointmentDay = null; 
		String clinicPhone = null;
		StringBuilder clinicAddress = new StringBuilder();
		
		EntityManager em = JPAUtil.getEntityManager();
		Integer patientId = notification.getNotifiedTo();
		UserDetailsDTO userDetails = UserDAO.getDetailsById(patientId);
		AppointmentDTO appointment = AppointmentDAO.getAppointmentByField("id", notification.getRelatedId());
		UserDTO doctor = appointment.getCaremember();
		AddressDTO address = appointment.getAddressid();
		
		
		email = userDetails.getUser().getEmail();
		userName = userDetails.getFirstName();		
		clinicPhone = appointment.getPhone();
		
		if (clinicPhone == null && address != null && address.getPhone() != null) {
	 		clinicPhone = address.getPhone();
	 	}
		
		if (clinicPhone == null && doctor != null) {
			UserDetailsDTO doctorDetails = UserDAO.getDetailsById(doctor.getId());
			if (doctorDetails.getMobile() != null) {
	    		clinicPhone = doctorDetails.getMobile();
	    	}
	    	else if (doctorDetails.getHomePhone() != null){
	    		clinicPhone = doctorDetails.getHomePhone();
	    	}					 	
		}
		
		if (clinicPhone == null && doctor != null) {			
			CareTeamMemberDTO careTeamMember = null;
	    	TypedQuery<CareTeamMemberDTO> query1 = em.createQuery("SELECT c FROM CareTeamMemberDTO c WHERE c.member.id = :id", CareTeamMemberDTO.class); 
	    	query1.setParameter("id", doctor.getId());
	    	try {
	    		careTeamMember = query1.getSingleResult();
	    		AddressDTO teamAddress = careTeamMember.getCareteam().getAddress();
	    		if (teamAddress != null && teamAddress.getPhone() != null) {
	    	 		clinicPhone = teamAddress.getPhone();
	    	 	}
			} catch (NoResultException e1) {
				e1.printStackTrace();
			}
		}									
		if (doctor != null) {
			doctorName = TemplateExtensions.usreNameNew(appointment.getCareMemberName(), doctor.getId()).toString();
		}
		else {
			doctorName = appointment.getCareMemberName();
		}
		
		if (appointment.getAppointmentdate() != null) {
			appointmentDate = new SimpleDateFormat("M/d/yyyy").format(appointment.getAppointmentdate());			
			appointmentDay = new SimpleDateFormat("EEEE").format(appointment.getAppointmentdate());
		}
		appointmentTime = appointment.getAppointmenttime();		
		
		if (address != null) {				
			clinicAddress.append(address.getLine1());
			if (address.getLine2() != null && !address.getLine2().isEmpty()) {
				clinicAddress.append(", " + address.getLine2());
			}
			if (address.getCity() != null && !address.getCity().isEmpty()) {
				clinicAddress.append(", " + address.getCity());
			}
			if (address.getState() != null && !address.getState().isEmpty()) {
				clinicAddress.append(", " + address.getState());
			}
		}
		distressCheckInURL = patientId.toString() + "/" + getChecksum(patientId);
		
		
		data.setEmail(email);
		data.setUserName(userName);
		data.setDistressCheckInURL(distressCheckInURL);
		data.setAppointmentDate(appointmentDate);
		data.setAppointmentDay(appointmentDay);
		data.setAppointmentTime(appointmentTime);		
		data.setClinicPhone(clinicPhone);
		data.setClinicAddress(clinicAddress.toString());
		data.setDoctorName(doctorName);
		
		return data;
	}
	public static void invitedFirstMail(NotificationDTO notification) {
		TemplateVars data = getInviteTemplatesData(notification);
		Map<String, Object> vars = new HashMap<String, Object>();
		
		vars.put("username", data.getUserName());
		vars.put("signupurl", data.getSignupURL());
		vars.put("clinic_phone", data.getClinicPhone());
		vars.put("reference_no", getReferenceNo(notification));
		if (!DEBUG) {		
			EmailUtil.sendEmail(EmailUtil.TVRH_INVITE_NO_APPOINTMENT_SCHEDULED, vars, data.getEmail());
		}
		setNotified(notification);			
	}
	
	public static void invitedReminderMail(NotificationDTO notification) {
		TemplateVars data = getInviteTemplatesData(notification);
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		boolean flag = false;
		
		if (notification.getNotifiedTo() != null) {
			appointments = AppointmentDAO.patientAllAppointments(notification.getNotifiedTo());
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(INVITATION, notification.getNotifiedTo(), notification.getScheduledTime());
		}
		
		if (flag || data.getAppointmentTime() != null || appointments.size() > 0) {			
			discardNotification(notification);
		}
		else {
			Map<String, Object> vars = new HashMap<String, Object>();			
			vars.put("username", data.getUserName());
			vars.put("signupurl", data.getSignupURL());
			vars.put("clinic_phone", data.getClinicPhone());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_INVITE_REMINDER_NO_APPOINTMENT_SCHEDULED, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	
	
	
	public static void invitedAppointmentFirstMail(NotificationDTO notification) {
		TemplateVars data = getInviteTemplatesData(notification);
		Map<String, Object> vars = new HashMap<String, Object>();				 
		
		vars.put("username", data.getUserName());
		vars.put("signupurl", data.getSignupURL());
		vars.put("clinic_phone", data.getClinicPhone());
		vars.put("doctor_name", data.getDoctorName());
		vars.put("date", data.getAppointmentDate());
		vars.put("appointment_day", data.getAppointmentDay());		
		vars.put("appointment_time", data.getAppointmentTime());			
		vars.put("clinic_address", data.getClinicAddress());
		vars.put("reference_no", getReferenceNo(notification));
		if (!DEBUG) {		
			EmailUtil.sendEmail(EmailUtil.TVRH_INVITE_APPOINTMENT_SCHEDULED, vars, data.getEmail());
		}
		setNotified(notification);			
	}
	
	public static void invitedAppointmentReminderMail(NotificationDTO notification) {
		TemplateVars data = getInviteTemplatesData(notification);		
		boolean flag = false;
		
		if (notification.getNotifiedTo() != null) {
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(INVITATION, notification.getNotifiedTo(), notification.getScheduledTime());
		}
		
		if (flag || data.getAppointmentTime() == null) {	
			discardNotification(notification);
		}
		else {		
			Map<String, Object> vars = new HashMap<String, Object>();						
			vars.put("username", data.getUserName());
			vars.put("signupurl", data.getSignupURL());
			vars.put("clinic_phone", data.getClinicPhone());
			vars.put("doctor_name", data.getDoctorName());
			vars.put("date", data.getAppointmentDate());
			vars.put("appointment_day", data.getAppointmentDay());
			vars.put("appointment_time", data.getAppointmentTime());			
			vars.put("clinic_address", data.getClinicAddress());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_INVITE_REMINDER_APPOINTMENT_SCHEDULED, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	
	// as soon as scheduled
	public static void sendEmailAsSoonAs(NotificationDTO notification) {			
		boolean flag = false;
		
		if (notification.getNotifiedTo() != null) {
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(APPOINTMENT, notification.getNotifiedTo(), notification.getScheduledTime());
		}
		
		if (flag) {	
			discardNotification(notification);
		}
		else {		
			TemplateVars data = getAppointmentTemplatesData(notification);
			Map<String, Object> vars = new HashMap<String, Object>();						
			vars.put("username", data.getUserName());			
			vars.put("clinic_phone", data.getClinicPhone());
			vars.put("doctor_name", data.getDoctorName());
			vars.put("date", data.getAppointmentDate());
			vars.put("appointment_day", data.getAppointmentDay());
			vars.put("appointment_time", data.getAppointmentTime());			
			vars.put("clinic_address", data.getClinicAddress());
			vars.put("generate_url", data.getDistressCheckInURL());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_APPOINTMENT_REMINDER_AS_SOON_AS_SCHEDULED, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	
	// 1 day before Appointment
	public static void sendEmailOneDayBefore(NotificationDTO notification) {			
		boolean flag = false;
		if (notification.getNotifiedTo() != null) {
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(APPOINTMENT, notification.getNotifiedTo(), notification.getScheduledTime());
		}			
		if (flag) {	
			discardNotification(notification);
		}
		else {		
			TemplateVars data = getAppointmentTemplatesData(notification);
			Map<String, Object> vars = new HashMap<String, Object>();						
			vars.put("username", data.getUserName());			
			vars.put("clinic_phone", data.getClinicPhone());
			vars.put("doctor_name", data.getDoctorName());	
			vars.put("date", data.getAppointmentDate());
			vars.put("appointment_day", data.getAppointmentDay());
			vars.put("appointment_time", data.getAppointmentTime());			
			vars.put("clinic_address", data.getClinicAddress());
			vars.put("generate_url", data.getDistressCheckInURL());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_APPOINTMENT_REMINDER_ONE_DAY_BEFORE, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	
	// 3 day before appointment
	public static void sendEmailThreeDayBefore(NotificationDTO notification) {			
		boolean flag = false;
		if (notification.getNotifiedTo() != null) {
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(APPOINTMENT, notification.getNotifiedTo(), notification.getScheduledTime());
		}			
		if (flag) {	
			discardNotification(notification);
		}
		else {		
			TemplateVars data = getAppointmentTemplatesData(notification);
			Map<String, Object> vars = new HashMap<String, Object>();						
			vars.put("username", data.getUserName());			
			vars.put("clinic_phone", data.getClinicPhone());
			vars.put("doctor_name", data.getDoctorName());		
			vars.put("date", data.getAppointmentDate());
			vars.put("appointment_day", data.getAppointmentDay());
			vars.put("appointment_time", data.getAppointmentTime());			
			vars.put("clinic_address", data.getClinicAddress());
			vars.put("generate_url", data.getDistressCheckInURL());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_APPOINTMENT_REMINDER_THREE_DAY_BEFORE, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	
	// 1 day before appointment with distress check
	public static void sendEmailOneDayBeforeDistressCheck(NotificationDTO notification) {		
		boolean flag = false;		
		if (notification.getNotifiedTo() != null) {
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(APPOINTMENT, notification.getNotifiedTo(), notification.getScheduledTime());
			if (!flag) {
				AppointmentDTO appointment = AppointmentDAO.getAppointmentByField("id", notification.getRelatedId());
				flag = isDistressCheckedBeforeAppointment(notification.getNotifiedTo(), appointment.getAppointmentdate(), 3);
			}
		}			
		if (flag) {	
			discardNotification(notification);
		}
		else {
			TemplateVars data = getAppointmentTemplatesData(notification);
			Map<String, Object> vars = new HashMap<String, Object>();						
			vars.put("username", data.getUserName());			
			vars.put("clinic_phone", data.getClinicPhone());
			vars.put("doctor_name", data.getDoctorName());		
			vars.put("date", data.getAppointmentDate());
			vars.put("appointment_day", data.getAppointmentDay());
			vars.put("appointment_time", data.getAppointmentTime());			
			vars.put("clinic_address", data.getClinicAddress());
			vars.put("generate_url", data.getDistressCheckInURL());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_APPOINTMENT_REMINDER_ONE_DAY_BEFORE, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	
	// 3 day before appointment with distress check
	public static void sendEmailThreeDayBeforeDistressCheck(NotificationDTO notification) {			
		boolean flag = false;
		if (notification.getNotifiedTo() != null) {
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(APPOINTMENT, notification.getNotifiedTo(), notification.getScheduledTime());			
			if (!flag) {				
				flag = isDistressCheckedBeforeAppointment(notification.getNotifiedTo(), notification.getScheduledTime(), 3);
			}
		}			
		if (flag) {	
			discardNotification(notification);
		}
		else {		
			TemplateVars data = getAppointmentTemplatesData(notification);
			Map<String, Object> vars = new HashMap<String, Object>();						
			vars.put("username", data.getUserName());			
			vars.put("clinic_phone", data.getClinicPhone());
			vars.put("doctor_name", data.getDoctorName());		
			vars.put("date", data.getAppointmentDate());
			vars.put("appointment_day", data.getAppointmentDay());
			vars.put("appointment_time", data.getAppointmentTime());			
			vars.put("clinic_address", data.getClinicAddress());
			vars.put("generate_url", data.getDistressCheckInURL());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_APPOINTMENT_REMINDER_THREE_DAY_BEFORE, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	
	// 1 week before
	public static void sendEmailOneWeekBefore(NotificationDTO notification) {			
		boolean flag = false;
		if (notification.getNotifiedTo() != null) {
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(APPOINTMENT, notification.getNotifiedTo(), notification.getScheduledTime());
		}
		
		if (flag) {	
			discardNotification(notification);
		}
		else {		
			TemplateVars data = getAppointmentTemplatesData(notification);
			Map<String, Object> vars = new HashMap<String, Object>();						
			vars.put("username", data.getUserName());			
			vars.put("clinic_phone", data.getClinicPhone());
			vars.put("doctor_name", data.getDoctorName());
			vars.put("date", data.getAppointmentDate());
			vars.put("appointment_day", data.getAppointmentDay());
			vars.put("appointment_time", data.getAppointmentTime());			
			vars.put("clinic_address", data.getClinicAddress());
			vars.put("generate_url", data.getDistressCheckInURL());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_APPOINTMENT_REMINDER_ONE_WEEK_BEFORE, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	// Surgery follow up
	public static void sendEmailSurgeryFollowUp(NotificationDTO notification) {			
		boolean flag = false;
		if (notification.getNotifiedTo() != null) {
			//flag = isMailSentRecently(recentNotifications, notification.getNotifiedTo());
			flag = isMailSentRecently(APPOINTMENT, notification.getNotifiedTo(), notification.getScheduledTime());
		}
		
		if (flag) {	
			discardNotification(notification);
		}
		else {		
			TemplateVars data = getAppointmentTemplatesData(notification);
			Map<String, Object> vars = new HashMap<String, Object>();						
			vars.put("username", data.getUserName());			
			vars.put("generate_url", data.getDistressCheckInURL());
			vars.put("reference_no", getReferenceNo(notification));
			if (!DEBUG) {		
				EmailUtil.sendEmail(EmailUtil.TVRH_SURGERY_FOLLOWUP, vars, data.getEmail());
			}
			setNotified(notification);
		}
	}
	
	public static void sendEmails() {		
		EntityManager em = JPAUtil.getEntityManager();
		List<NotificationDTO> notifications = new ArrayList<NotificationDTO>();
		//List<NotificationDTO> recentNotifications = new ArrayList<NotificationDTO>();
		
		TypedQuery<NotificationDTO> query = em.createQuery("SELECT n FROM NotificationDTO n WHERE n.notified = :notified AND n.discard = :discard AND age(LOCALTIMESTAMP, n.scheduledTime) > '30 second' ORDER BY n.priority ASC, n.scheduledTime ASC", NotificationDTO.class);
		query.setParameter("notified", false);
		query.setParameter("discard", false);
		try {			
			notifications = query.getResultList();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		TypedQuery<NotificationDTO> query2 = em.createQuery("SELECT n FROM NotificationDTO n WHERE n.notified = :notified AND age(LOCALTIMESTAMP, n.scheduledTime) > '0 second' AND age(LOCALTIMESTAMP, n.scheduledTime) <= '2 day'", NotificationDTO.class);
		query2.setParameter("notified", true);
		try {			
			recentNotifications = query2.getResultList();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		if (DEBUG) {
			System.out.println("----------------------");
		}
		for (NotificationDTO notification : notifications) {				
			if (DEBUG) {
				System.out.println(notification.getScheduledTime());
			}			
			String description = notification.getDescription().trim();
			
			if (description.equalsIgnoreCase(INVITED_FIRST_MAIL)) {
				invitedFirstMail(notification);				
			}
			else if (description.equalsIgnoreCase(INVITED_SECOND_MAIL)) {
				invitedReminderMail(notification);
			}
			else if (description.equalsIgnoreCase(INVITED_THIRD_MAIL)) {
				invitedReminderMail(notification);
			}
			else if (description.equalsIgnoreCase(INVITED_APPOINTMENT_FIRST_MAIL)) {
				invitedAppointmentFirstMail(notification);
			}
			else if (description.equalsIgnoreCase(INVITED_APPOINTMENT_SECOND_MAIL)) {
				invitedAppointmentReminderMail(notification);
			}
			else if (description.equalsIgnoreCase(INVITED_APPOINTMENT_THIRD_MAIL)) {
				invitedAppointmentReminderMail(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_FIRST_MAIL)) {
				sendEmailAsSoonAs(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_RADIATION_ONCOLOGIST_REMINDER_MAIL)) {
				sendEmailOneDayBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_SIMULATION_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}			
			else if (description.equalsIgnoreCase(APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_ON_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_END_TREATMENT_VISIT_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_SEVEN_DAYS_BEFORE_MAIL)) {
				sendEmailOneWeekBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_FOLLOW_UP_RADIATION_ONCOLOGY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_SURGEON_ONE_HOUR_AFTER_SCHUDULED)) {				
				sendEmailAsSoonAs(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_FIRST_WITH_SURGEON_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_TREATMENT_DECISION_SURGERY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_ONE_DAY_AFTER_MAIL)) {
				sendEmailSurgeryFollowUp(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_THREE_DAYS_AFTER_MAIL)) {
				sendEmailSurgeryFollowUp(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_SURGERY_SEVEN_DAYS_AFTER_MAIL)) {
				sendEmailSurgeryFollowUp(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_FOLLOW_UP_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_FOLLOW_UP_SURGERY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_SEVEN_DAYS_BEFORE_MAIL)) {
				sendEmailOneWeekBefore(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_THREE_DAYS_BEFORE_MAIL)) {
				sendEmailThreeDayBeforeDistressCheck(notification);
			}
			else if (description.equalsIgnoreCase(APPOINTMENT_ONGOING_SURGERY_ONE_DAY_BEFORE_MAIL)) {
				sendEmailOneDayBeforeDistressCheck(notification);
			}			
		}
		if (DEBUG) {
			System.out.println("----------------------");
		}
	}
}

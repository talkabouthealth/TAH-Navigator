import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.AppointmentAlertDTO;
import models.AppointmentDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import nav.dao.AppointmentDAO;
import nav.dao.PatientAlert;
import nav.dao.UserDAO;

import org.junit.*;

import play.test.*;
import util.JPAUtil;
import util.TemplateExtensions;


public class AppointmentReminderTest extends UnitTest{
	private final String patientEmail = "syedinothing@gmail.com";
	private final String doctorEmail = "deboraholsen@example.com";
	
	public static UserDTO getUser(String email) {
		UserDTO user = UserDAO.getAccountByUserEmail(email);
		return user;
	}
	
	public static AppointmentAlertDTO getAlert(Integer appointmentId, String alertDetail) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<AppointmentAlertDTO> query = em.createQuery("SELECT a FROM AppointmentAlertDTO a WHERE a.appointmentId = :appointmentId AND a.alertDetail = :alertDetail", AppointmentAlertDTO.class);
		AppointmentAlertDTO alert = null;				
		try {
			query.setParameter("appointmentId", appointmentId);
			query.setParameter("alertDetail", alertDetail);
			alert = query.getSingleResult();					
		} catch (NoResultException e) {
			
		}
		return alert;
	}
	
	public static String getName(UserDTO user) {
		return TemplateExtensions.usreName(user.getName(), user.getId());
	}
	
	@Test
	public void isUserExist() {
		UserDTO user1 = getUser(patientEmail);
		UserDTO user2 = getUser(doctorEmail);
		assertNotNull(user1);
		assertNotNull(user2);
	}
	
	@Test
	public void firstAppointmentTests() {
		UserDTO patient = getUser(patientEmail);
		UserDTO doctor = getUser(doctorEmail);		
		AppointmentDTO appointment = new AppointmentDTO();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		appointment.setAddedon(date);
		long time = date.getTime() + PatientAlert.ONE_DAY;
		date.setTime(time);
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		date = cal.getTime();
		appointment.setAppointmentdate(date);
		appointment.setAppointmenttime("10 AM");
		appointment.setDeleteflag(false);
		appointment.setCaremember(doctor);
		appointment.setCareMemberName(getName(doctor));
		appointment.setPatientid(patient);
		appointment.setAppointmentcenter("Moffitt Cancer Center TVRH");
		appointment.setTreatementStep(PatientAlert.APPOINTMENT_STEP_FIRST_APPOINTMENT);
		
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(appointment);
		em.getTransaction().commit();
		PatientAlert.firstAppointmentScheduledAlert(patient, appointment);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AppointmentAlertDTO alert1 = getAlert(appointment.getId(), PatientAlert.AR_FIRST_APPOINTMENT_AS_SOON_AS_SCHEDULED);
		assertNotNull(alert1);
		PatientAlert.firstAppointmentAlerts();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AppointmentAlertDTO alert2 = getAlert(appointment.getId(), PatientAlert.AR_FIRST_APPOINTMENT_ONE_DAY_BEFORE);
		assertNotNull(alert2);
		// clean data
		/*
		em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(appointment);
		em.remove(alert1);
		em.remove(alert2);
		em.getTransaction().commit();
		*/
	}
	
}

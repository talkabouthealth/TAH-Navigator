package nav.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import util.JPAUtil;
import models.AppointmentDTO;
import models.NoteDTO;
import models.PatientMedicationDTO;
import models.UserDTO;


public class AppointmentDAO {

	public static List<AppointmentDTO> getAppointmentListByField(String fieldName, Object param) {
		List<AppointmentDTO> dtoList = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c WHERE c."+fieldName+" = :field and deleteflag = false order by appointmentdate asc", AppointmentDTO.class); 
			query.setParameter("field", param);
			dtoList = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dtoList;
	}
	
	public static List<AppointmentDTO> getAppointmentListByField(String fieldName, Object param ,Date date, String status) {
		List<AppointmentDTO> dtoList = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c " +
					" WHERE c."+fieldName+" = :field and deleteflag = false and c.appointmentdate "+(status.equalsIgnoreCase("past")?"<":" >=")+" :date and deleteflag = false order by appointmentdate asc", AppointmentDTO.class); 
			query.setParameter("field", param);
			query.setParameter("date", date);
			dtoList = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dtoList;
	}
	
	public static AppointmentDTO getAppointmentByField(String fieldName, Object param) {
		AppointmentDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c WHERE c."+fieldName+" = :field", AppointmentDTO.class);
			query.setMaxResults(1);
			query.setParameter("field", param);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	/**
	 * get last appointment info of patient
	 * @param fieldName
	 * @param param
	 * @return
	 */
	public static AppointmentDTO getLastAppointment(UserDTO patient,Date date) {
		AppointmentDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		Integer in = new Integer(patient.getId());
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c WHERE c.patientid.id = :field and  c.appointmentdate < :date and deleteflag = false ORDER BY appointmentdate  desc, appointmenttime DESC ", AppointmentDTO.class);
			query.setMaxResults(1);
			query.setParameter("field",in);
			query.setParameter("date",date);
			dto = query.getSingleResult();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return dto;
	}
	
	/**
	 * get last appointment info of patient
	 * @param fieldName
	 * @param param
	 * @return
	 */
	public static AppointmentDTO getLatestAppointment(String fieldName, Object param ,Date date, String status) {
		
		AppointmentDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c " +
					" WHERE c."+fieldName+" = :field and deleteflag = false and c.appointmentdate "+(status.equalsIgnoreCase("past")?"<":" >=")+" :date and deleteflag = false", AppointmentDTO.class); 
			query.setMaxResults(1);
			query.setParameter("field", param);
			query.setParameter("date", date);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static AppointmentDTO nextAppointment(Integer patientId) {
		AppointmentDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<AppointmentDTO> query = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.patientid.id = :patientId and a.deleteflag = false and a.appointmentdate >= current_date ORDER BY a.appointmentdate ASC", AppointmentDTO.class);
		query.setMaxResults(1);
		query.setParameter("patientId", patientId);
		try {
			dto = query.getSingleResult();
		} catch (NoResultException e) {			
			e.printStackTrace();
		}
		return dto;
	}
}
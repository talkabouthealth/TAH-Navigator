package nav.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPAUtil;

import models.AppointmentDTO;
import models.NoteDTO;
import models.PatientMedicationDTO;


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
					" WHERE c."+fieldName+" = :field and deleteflag = false and c.appointmentdate "+(status.equalsIgnoreCase("past")?"<":" >=")+" :date order by appointmentdate asc", AppointmentDTO.class); 
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
}
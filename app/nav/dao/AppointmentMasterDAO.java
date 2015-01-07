package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPAUtil;

import models.AppointmentMasterDTO;
import models.MedicineCatlogDTO;
import models.PatienCareTeamDTO;
import models.PatientMedicationDTO;
import models.UserDTO;

public class AppointmentMasterDAO extends BaseDAO{

	public static List<AppointmentMasterDTO> getAllAppointments() {
		List<AppointmentMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentMasterDTO> query = em.createQuery("SELECT c FROM AppointmentMasterDTO c where c.active = true order by name", AppointmentMasterDTO.class); 
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<AppointmentMasterDTO> getAllAppointmentsByAppointmentType(String teamName) {
		System.out.println(teamName);
		List<AppointmentMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentMasterDTO> query = em.createQuery("SELECT c FROM AppointmentMasterDTO c where c.active = true order by name", AppointmentMasterDTO.class);
			if(teamName.equalsIgnoreCase("Surgery")) {
				String arrayNames = "'1st Appointment With Surgeon','Make Treatment Decision','Surgery','Follow-up Appointment'";
				query = em.createQuery("SELECT c FROM AppointmentMasterDTO c where c.name in (" + arrayNames + ") order by name", AppointmentMasterDTO.class);
			}
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<AppointmentMasterDTO> getAppointments(String fieldName, Object value) {
		List<AppointmentMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentMasterDTO> query = em.createQuery("SELECT c FROM AppointmentMasterDTO c WHERE c."+fieldName+" = :field order by id", AppointmentMasterDTO.class); 
			query.setParameter("field", value);

			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static AppointmentMasterDTO getAppointmentByField(String fieldName, Object value) {
		AppointmentMasterDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentMasterDTO> query = em.createQuery("SELECT c FROM AppointmentMasterDTO c WHERE c."+fieldName+" = :field", AppointmentMasterDTO.class); 
			query.setParameter("field", value);
			query.setMaxResults(1);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
}
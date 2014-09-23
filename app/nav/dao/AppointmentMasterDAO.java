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
			TypedQuery<AppointmentMasterDTO> query = em.createQuery("SELECT c FROM AppointmentMasterDTO c order by id", AppointmentMasterDTO.class); 
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
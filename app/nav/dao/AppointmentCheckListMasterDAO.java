package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPAUtil;

import models.AppointmentCheckListMasterDTO;
import models.AppointmentMasterDTO;
import models.MedicineCatlogDTO;
import models.PatienCareTeamDTO;
import models.PatientMedicationDTO;
import models.UserDTO;

public class AppointmentCheckListMasterDAO extends BaseDAO{

	public static List<AppointmentCheckListMasterDTO> getAllAppointmentCheckList() {
		List<AppointmentCheckListMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentCheckListMasterDTO> query = em.createQuery("SELECT c FROM AppointmentCheckListMasterDTO c order by id", AppointmentCheckListMasterDTO.class); 
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<AppointmentCheckListMasterDTO> getAppointmentCheckList(String fieldName, Object value) {
		List<AppointmentCheckListMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentCheckListMasterDTO> query = em.createQuery("SELECT c FROM AppointmentCheckListMasterDTO c WHERE c."+fieldName+" = :field order by id", AppointmentCheckListMasterDTO.class); 
			query.setParameter("field", value);

			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static AppointmentCheckListMasterDTO getAppointmentByField(String fieldName, Object value) {
		AppointmentCheckListMasterDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentCheckListMasterDTO> query = em.createQuery("SELECT c FROM AppointmentMasterDTO c WHERE c."+fieldName+" = :field", AppointmentCheckListMasterDTO.class); 
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
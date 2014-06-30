package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.CareTeamMasterDTO;
import models.ExpertDetailDTO;
import models.PatientDetailDTO;
import models.UserCertificateDTO;
import models.UserEducationDTO;
import models.UserExpertiesDTO;
import util.JPAUtil;

public class ProfileDAO {

	public static ExpertDetailDTO getExpertByField(String fieldName, Object value) {
		ExpertDetailDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<ExpertDetailDTO> query = em.createQuery("SELECT c FROM ExpertDetailDTO c WHERE c."+fieldName+" = :field", ExpertDetailDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static CareTeamMasterDTO getCareTeamByField(String fieldName, Object value) {
		CareTeamMasterDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMasterDTO> query = em.createQuery("SELECT c FROM CareTeamMasterDTO c WHERE c."+fieldName+" = :field", CareTeamMasterDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<UserEducationDTO> getEducationByField(String fieldName, Object value) {
		List<UserEducationDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserEducationDTO> query = em.createQuery("SELECT c FROM UserEducationDTO c WHERE c."+fieldName+" = :field order by id", UserEducationDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<UserExpertiesDTO> getExpertiesByField(String fieldName, Object value) {
		List<UserExpertiesDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserExpertiesDTO> query = em.createQuery("SELECT c FROM UserExpertiesDTO c WHERE c."+fieldName+" = :field order by id", UserExpertiesDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<UserCertificateDTO> getCertificateByField(String fieldName, Object value) {
		List<UserCertificateDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserCertificateDTO> query = em.createQuery("SELECT c FROM UserCertificateDTO c WHERE c."+fieldName+" = :field order by id", UserCertificateDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	
	public static PatientDetailDTO getPatientByField(String fieldName, Object value) {
		PatientDetailDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientDetailDTO> query = em.createQuery("SELECT c FROM PatientDetailDTO c WHERE c."+fieldName+" = :field", PatientDetailDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
			//e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
}

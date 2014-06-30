package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.CareTeamMasterDTO;
import models.CareTeamMemberDTO;
import models.PatienCareTeamDTO;
import models.UserCertificateDTO;
import models.UserDTO;
import util.JPAUtil;

public class CareTeamDAO {

	public static boolean createPatienCareTeamAll(UserDTO patien) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="INSERT INTO PatienCareTeamDTO(careteamid, patienid) SELECT id, "+patien.getId()+" FROM CareTeamMasterDTO where id=2 or id=3";
		em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		int result = query.executeUpdate();
		em.getTransaction().commit();
		System.out.println("result : " + result);
		return true;
	}

	public static List<PatienCareTeamDTO> getPatienCareTeamByField(String fieldName, Object value) {
		List<PatienCareTeamDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatienCareTeamDTO> query = em.createQuery("SELECT c FROM PatienCareTeamDTO c WHERE c."+fieldName+" = :field order by id", PatienCareTeamDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
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
	
	public static List<CareTeamMemberDTO> getCareTeamMembersByField(String fieldName, Object value) {
		List<CareTeamMemberDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMemberDTO> query = em.createQuery("FROM CareTeamMemberDTO c WHERE c."+fieldName+" = :field order by primary", CareTeamMemberDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static CareTeamMemberDTO getCareTeamMembersByMemberId(Object value) {
		List<CareTeamMemberDTO> list = getCareTeamMembersByField("memberid",value);
		if(list!=null && list.size()>0)
			return list.get(0);
		else
			return null;
	}
	
	
}

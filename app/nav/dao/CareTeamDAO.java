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

	public static List<CareTeamMasterDTO> getAllCareTeam() {
		List<CareTeamMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMasterDTO> query = em.createQuery("FROM CareTeamMasterDTO", CareTeamMasterDTO.class); 
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static boolean addMember(CareTeamMasterDTO careTeam,UserDTO usr) {
		
		EntityManager em = JPAUtil.getEntityManager();
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		
 		CareTeamMemberDTO dto = new CareTeamMemberDTO();
		dto.setCareteamid(careTeam.getId());
		dto.setCareteam(careTeam);
		dto.setMemberid(usr.getId());
		dto.setMember(usr);
		dto.setPrimary(false);
		em.persist(dto);
		em.getTransaction().commit();
		return true;
	}
	public static boolean removeMember(int teamid,int memberid) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="delete from CareTeamMemberDTO where careteamid = :f0 and memberid= :f1";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		
		query.setParameter("f0", teamid);
		query.setParameter("f1", memberid);
		query.executeUpdate();
		em.getTransaction().commit();
		return true;
	}
	
	public static boolean makePrimary(int teamid,int memberid) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="update CareTeamMemberDTO set primary = :fp1 where careteamid = :f0";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		
		query.setParameter("fp1", false);
		query.setParameter("f0", teamid);
		int result = query.executeUpdate();
		em.getTransaction().commit();

		hql ="update CareTeamMemberDTO set primary= :fp2 where careteamid = :f1 and memberid= :f2";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		query = em.createQuery(hql);
		query.setParameter("fp2", true);
		query.setParameter("f1", teamid);
		query.setParameter("f2", memberid);
		result = query.executeUpdate();
		em.getTransaction().commit();
		
		System.out.println("result Edit care team : " + result);
		return true;
	}

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
			TypedQuery<PatienCareTeamDTO> query = em.createQuery("SELECT c FROM PatienCareTeamDTO c WHERE c."+fieldName+" = :field and c.patien.isActive = true order by id", PatienCareTeamDTO.class);
			// and c.patien.isverified = true
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
			TypedQuery<CareTeamMemberDTO> query = em.createQuery("FROM CareTeamMemberDTO c WHERE c."+fieldName+" = :field order by primary desc", CareTeamMemberDTO.class); 
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

package nav.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;
import models.InputDefaultDTO;

public class InputDefaultDAO {

	public static  List<InputDefaultDTO> getInputDefaultByPage(String page,Integer diseaseId) {
		List<InputDefaultDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<InputDefaultDTO> query = em.createQuery("SELECT c FROM InputDefaultDTO c where c.diseaseid = :f1 and c.page = :f2", InputDefaultDTO.class);
			query.setParameter("f1", diseaseId);
			query.setParameter("f2", page);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}
	
	public static  List<InputDefaultDTO> getInputDefaultByPageField(String page,Integer diseaseId,String field) {
		List<InputDefaultDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			String hql = "SELECT c FROM InputDefaultDTO c where c.diseaseid = :f1 and c.page = :f2 and c.field = :f3 order by c.fieldtext";
			if(diseaseId == null) {
				hql = "SELECT c FROM InputDefaultDTO c where c.diseaseid is null and c.page = :f2 and c.field = :f3 order by c.fieldtext";
			}
			TypedQuery<InputDefaultDTO> query = em.createQuery(hql, InputDefaultDTO.class);
			if(diseaseId != null) {
				query.setParameter("f1", diseaseId);
			}
			query.setParameter("f2", page);
			query.setParameter("f3", field);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public static  List<InputDefaultDTO> getInputDefaultByPageField(String page,String field) {
		List<InputDefaultDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<InputDefaultDTO> query = em.createQuery("SELECT c FROM InputDefaultDTO c where c.page = :f2 and c.field = :f3 order by c.fieldtext", InputDefaultDTO.class);
			query.setParameter("f2", page);
			query.setParameter("f3", field);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public static InputDefaultDTO getInputTipTextDefaultByFieldName(Integer diseaseId,String page,String fieldName) {
		InputDefaultDTO types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<InputDefaultDTO> query = em.createQuery("SELECT c FROM InputDefaultDTO c where c.page = :f2 and upper(c.fieldtext) = upper(:f3)", InputDefaultDTO.class);
			// c.diseaseid = :f1 and
//			query.setParameter("f1", diseaseId);
			query.setParameter("f2", page);
			query.setParameter("f3", fieldName.trim());
			query.setMaxResults(1);
			types = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types; 
	}
}
package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import models.DesignationMasterDTO;
import util.JPAUtil;

public class DesignationMasterDAO {

	public static List<DesignationMasterDTO> getUserTypeList() {
		EntityManager em = JPAUtil.getEntityManager();
		List<DesignationMasterDTO> result = em.createQuery( "from DesignationMasterDTO where id>0", DesignationMasterDTO.class ).getResultList();
		return result;
	}
	
	public static DesignationMasterDTO getEntityById(String contactId) {
		DesignationMasterDTO dto = null;
		try {
			EntityManager em = JPAUtil.getEntityManager();
			dto = em.createQuery(createQueryByProperty("id", contactId)).getSingleResult();
		}catch(Exception e) {
		}
		return dto;
	}
	
	private static CriteriaQuery<DesignationMasterDTO> createQueryByProperty(String property, Object value) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DesignationMasterDTO> cq = cb.createQuery(DesignationMasterDTO.class);
		Root<DesignationMasterDTO> root = cq.from(DesignationMasterDTO.class);
		cq = cq.where(cb.equal(root.get(property), value));
		return cq;
	}
}

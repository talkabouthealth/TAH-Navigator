package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import models.ContactTypeDTO;
import models.SecurityQuestionDTO;
import util.JPAUtil;

public class SecurityQuestionDAO {

	public static List<SecurityQuestionDTO> getSecurityQuestions() {
		EntityManager em = JPAUtil.getEntityManager();
		List<SecurityQuestionDTO> result = em.createQuery( "from SecurityQuestionDTO", SecurityQuestionDTO.class ).getResultList();
		return result;
	}

	public static SecurityQuestionDTO getEntityById(String contactId) {
		EntityManager em = JPAUtil.getEntityManager();
		SecurityQuestionDTO dto = em.createQuery(createQueryByProperty("id", contactId)).getSingleResult();
		return dto;
	}
	
	private static CriteriaQuery<SecurityQuestionDTO> createQueryByProperty(String property, Object value) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SecurityQuestionDTO> cq = cb.createQuery(SecurityQuestionDTO.class);
		Root<SecurityQuestionDTO> root = cq.from(SecurityQuestionDTO.class);
		cq = cq.where(cb.equal(root.get(property), value));
		return cq;
	}
}
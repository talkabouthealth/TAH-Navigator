package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import util.JPAUtil;

import models.SecurityQuestionDTO;
import models.UserTypeDTO;

public class UserTypeDAO {

	public static List<UserTypeDTO> getUserTypeList() {
		EntityManager em = JPAUtil.getEntityManager();
		List<UserTypeDTO> result = em.createQuery( "from UserTypeDTO where id>0 order by id ASC", UserTypeDTO.class ).getResultList();
		return result;
	}
	
	public static UserTypeDTO getEntityById(String contactId) {
		EntityManager em = JPAUtil.getEntityManager();
		UserTypeDTO dto = em.createQuery(createQueryByProperty("id", contactId)).getSingleResult();
		return dto;
	}
	
	private static CriteriaQuery<UserTypeDTO> createQueryByProperty(String property, Object value) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserTypeDTO> cq = cb.createQuery(UserTypeDTO.class);
		Root<UserTypeDTO> root = cq.from(UserTypeDTO.class);
		cq = cq.where(cb.equal(root.get(property), value));
		return cq;
	}
}

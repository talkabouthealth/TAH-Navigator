package nav.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.ejb.packaging.Entry;

import models.ContactTypeDTO;
import models.UserTypeDTO;
import util.JPAUtil;

public class ContactTypeDAO {

	public static List<ContactTypeDTO> getContactTypeList() {
		EntityManager em = JPAUtil.getEntityManager();
		List<ContactTypeDTO> result = em.createQuery( "from ContactTypeDTO", ContactTypeDTO.class ).getResultList();
		return result;
	}

	public static ContactTypeDTO getEntityById(String contactId) {
		EntityManager em = JPAUtil.getEntityManager();
		ContactTypeDTO dto = em.createQuery(createQueryByProperty("id", contactId)).getSingleResult();
		return dto;
	}

	/* 
	private CriteriaQuery<ContactTypeDTO> createQueryByProperties(Map<String, Object> properties) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ContactTypeDTO> cq = cb.createQuery(ContactTypeDTO.class);
		Root<ContactTypeDTO> root = cq.from(ContactTypeDTO.class);
		for (java.util.Map.Entry<String, Object> entry : properties.entrySet()) {
			cq = cq.where(cb.equal(root.get(entry.getKey()), entry.getValue()));
		}
		return cq;
	}
	*/
	private static CriteriaQuery<ContactTypeDTO> createQueryByProperty(String property, Object value) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ContactTypeDTO> cq = cb.createQuery(ContactTypeDTO.class);
		Root<ContactTypeDTO> root = cq.from(ContactTypeDTO.class);
		cq = cq.where(cb.equal(root.get(property), value));
		return cq;
	}
}
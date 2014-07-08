package nav.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.AddressDTO;
import models.UserDetailsDTO;
import util.JPAUtil;

public class AddressDAO {

	public static AddressDTO getDetailsByField(String fieldName, Object value) {
		AddressDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AddressDTO> query = em.createQuery("SELECT c FROM AddressDTO c WHERE c."+fieldName+" = :field", AddressDTO.class); 
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}

	public static AddressDTO update(AddressDTO dto) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(dto);
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public static AddressDTO save(AddressDTO dto) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(dto);
		em.getTransaction().commit();
		return dto;
	}
}
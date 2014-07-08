package nav.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import models.AddressDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserOtherEmailDTO;
import util.JPAUtil;

public class UserOtherEmailDAO {

	public static UserOtherEmailDTO getDetailsByField(String fieldName, Object value) {
		UserOtherEmailDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserOtherEmailDTO> query = em.createQuery("SELECT c FROM UserOtherEmailDTO c WHERE c."+fieldName+" = :field", UserOtherEmailDTO.class); 
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}

	public static List<UserOtherEmailDTO> getAllByField(String fieldName, Object value) {
		List<UserOtherEmailDTO> dtoList = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserOtherEmailDTO> query = em.createQuery("SELECT c FROM UserOtherEmailDTO c WHERE c."+fieldName+" = :field", UserOtherEmailDTO.class); 
			query.setParameter("field", value);
			dtoList = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dtoList;
	}

	public static UserOtherEmailDTO update(UserOtherEmailDTO dto) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(dto);
			em.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public static UserOtherEmailDTO save(UserOtherEmailDTO emailDto) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(emailDto);
		em.getTransaction().commit();
		return emailDto;
	}

	public static boolean remove(UserOtherEmailDTO emailDto) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.getReference(UserOtherEmailDTO.class, emailDto.getId()));
//		em.remove(emailDto);
		em.getTransaction().commit();
		return true;
	}
}
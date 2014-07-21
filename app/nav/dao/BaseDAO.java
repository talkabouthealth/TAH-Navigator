package nav.dao;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.PatientDetailDTO;
import models.UserDTO;
import nav.dto.UserBean;
import util.JPAUtil;

public class BaseDAO {
	
	public static Object update(Object dto) {
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

	public static Object save(Object dto) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(dto);
		em.getTransaction().commit();
		return dto;
	}
}

package nav.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import models.AddressDTO;
import models.ExpertDetailDTO;
import models.PatientDetailDTO;
import models.UserDetailsDTO;
import util.JPAUtil;

public class ExpertDetailDAO {

	public static ExpertDetailDTO getDetailsByField(String fieldName, Object value) {
		ExpertDetailDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<ExpertDetailDTO> query = em.createQuery("SELECT c FROM ExpertDetailDTO c WHERE c."+fieldName+" = :field", ExpertDetailDTO.class); 
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}

	public static ExpertDetailDTO update(ExpertDetailDTO dto) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(dto);
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}

	public static ExpertDetailDTO save(ExpertDetailDTO dto) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(dto);
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
}
package nav.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nav.dto.UserBean;

import util.JPAUtil;
import models.UserDTO;
import models.UserImageDTO;

public class UserImageDAO {

	public static boolean updateUserImage(UserDTO user,byte[] image,String imagename, String fileExt) {
		UserImageDTO imageDTO = new UserImageDTO();
		imageDTO.setId(user.getId());
		imageDTO.setUser(user);
		imageDTO.setImagename(imagename);
		imageDTO.setImagetype(fileExt);
		imageDTO.setByteImage(image);
		boolean isImg = isUserImage(user);
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		if(isImg) {
			em.merge(imageDTO);
		} else {
			em.persist(imageDTO);
		}
		em.getTransaction().commit();
		return true;
	}
	
	public static boolean isUserImage(UserDTO user) {
		Integer in = new Integer(user.getId());
		UserImageDTO imgDto = getUserByField("id",in);
		if(imgDto != null)
			return true;
		else 
			return false;
	}
	
	public static UserImageDTO getByUserId(int usreId) {
		Integer in = new Integer(usreId);
		return getUserByField("id",in);
	}
	public static UserImageDTO getUserByField(String fieldName, Object value) {
		UserImageDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserImageDTO> query = em.createQuery("SELECT c FROM UserImageDTO c WHERE c."+fieldName+" = :field", UserImageDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
//			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
}

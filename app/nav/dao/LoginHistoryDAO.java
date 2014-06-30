package nav.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPAUtil;

import models.LoginHistoryDTO;
import models.UserDTO;

public class LoginHistoryDAO {

	public static LoginHistoryDTO getLastLoginByUserId(String sessionUser) {
		UserDTO user =  UserDAO.getAccountByUserEmail(sessionUser);
		EntityManager em = JPAUtil.getEntityManager();
		LoginHistoryDTO historyDTO = null;
		try {
			TypedQuery<LoginHistoryDTO> query = em.createQuery("SELECT c FROM LoginHistoryDTO c WHERE c.user.id = :field order by c.logintime desc", LoginHistoryDTO.class); 
			query.setMaxResults(1);
			query.setParameter("field", new Integer(user.getId()));
			historyDTO = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return historyDTO;
	}
	
	public static String getIsFirstTime(String sessionUser) {
		UserDTO user =  UserDAO.getAccountByUserEmail(sessionUser);
		EntityManager em = JPAUtil.getEntityManager();
		boolean isFirstTime = true;
		try {
			TypedQuery<LoginHistoryDTO> query = em.createQuery("FROM LoginHistoryDTO c WHERE c.user.id = :field order by c.logintime desc", LoginHistoryDTO.class);
			query.setMaxResults(3);
			query.setParameter("field", new Integer(user.getId()));
			if(query.getResultList().size()>1)
				isFirstTime = false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return Boolean.toString(isFirstTime);
	}
	
	public static boolean saveLogin(String sessionUser,String loginFrom,boolean rememberChecked,String sessionid) {
		LoginHistoryDTO historyDTO = new LoginHistoryDTO();
		UserDTO user =  UserDAO.getAccountByUserEmail(sessionUser);
		EntityManager em = JPAUtil.getEntityManager();
		try {
			historyDTO.setFrom(loginFrom);
			historyDTO.setLogintime(new Date());
			historyDTO.setRefrer("");
			historyDTO.setRememberChecked(rememberChecked);
			historyDTO.setSessionid(sessionid);
			historyDTO.setUser(user);
			em.getTransaction().begin();
			em.persist(historyDTO);
			em.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return true;
	}
}
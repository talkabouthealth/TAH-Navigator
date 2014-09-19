package nav.dao;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import util.JPAUtil;

import models.UserDTO;

import antlr.collections.List;


public class AdminDAO {

	public static UserDTO getAdminAuth(String email, String password) {
		UserDTO account = null;
		try {
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<UserDTO> query = em.createQuery("FROM UserDTO c WHERE (c.email = :email or c.name = :name) and c.password = :pass ", UserDTO.class); //and c.userType = :userType
			//and c.isActive = :isActive 
			query.setParameter("email", email);
			query.setParameter("name", email);
			query.setParameter("pass", password);
//			query.setParameter("isActive", true);
//			query.setParameter("userType",'a');
			account = query.getSingleResult();
//			if(account != null) {
//				return true;
//			} else {
//				return false;
//			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return account;
	}
	/*
	public static boolean getCareAuth(String email, String password) {
		try {
			UserDTO account = new UserDTO();
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<UserDTO> query = em.createQuery("FROM UserDTO c WHERE c.email = :email and c.password = :pass and c.isActive = :isActive and c.userType = :userType", UserDTO.class); 
			query.setParameter("email", email);
			query.setParameter("pass", password);
			query.setParameter("isActive", true);
			query.setParameter("userType",'c');
			account = query.getSingleResult();
			if(account != null) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static boolean getPatientAuth(String email, String password) {
		try {
			UserDTO account = new UserDTO();
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<UserDTO> query = em.createQuery("FROM UserDTO c WHERE c.email = :email and c.password = :pass and c.isActive = :isActive and c.userType = :userType", UserDTO.class); 
			query.setParameter("email", email);
			query.setParameter("pass", password);
			query.setParameter("isActive", true);
			query.setParameter("userType",'p');
			account = query.getSingleResult();
			if(account != null) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	*/
}

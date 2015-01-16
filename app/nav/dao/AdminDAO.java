package nav.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.AppointmentDTO;
import models.InvitedDTO;
import models.UserDTO;
import util.JPAUtil;


public class AdminDAO {

	public static UserDTO getAdminAuth(String email, String password) {
		UserDTO account = null;
		try {
			EntityManager em = JPAUtil.getEntityManager();
//			TypedQuery<UserDTO> query = em.createQuery("FROM UserDTO c WHERE (c.email = :email or c.name = :name) and c.password = :pass ", UserDTO.class); //and c.userType = :userType
//			TypedQuery<UserDTO> query = em.createQuery("FROM UserDTO c WHERE (upper(c.email) = upper(:email) or c.name = :name) and c.password = :pass ", UserDTO.class); //and c.userType = :userType
			TypedQuery<UserDTO> query = em.createQuery("FROM UserDTO c WHERE (upper(c.email) = upper(:email) or c.name = :name)", UserDTO.class); //and c.isActive= :isActive 
			//and c.isActive = :isActive 
			query.setParameter("email", email);
			query.setParameter("name", email);
//			query.setMaxResults(1);
//			query.setParameter("isActive", true);
//			query.setParameter("pass", password);
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
	
	public static int createInvitedAccounts() {
		int accounts = 0;
		try {
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<InvitedDTO> query = em.createQuery("FROM InvitedDTO c WHERE c.activateOnSignup = false and c.email not in (select email from UserDTO)", InvitedDTO.class);
			java.util.List<InvitedDTO> account = query.getResultList();
			for (InvitedDTO app : account) {
				System.out.println("Email: " + app.getEmail());
				UserDTO newUser =  UserDAO.createPatientAccount(app);
				if(newUser!=null && app.getAddressid() != null) {
					AppointmentDTO appointment = new AppointmentDTO();
					appointment.setAddedby(app.getAddedby());
					appointment.setAddedon(app.getAddedon());
					appointment.setAddressid(app.getAddressid());
					appointment.setAppointmentcenter(app.getAddressid().getLine1());
					
					appointment.setAppointmentdate(app.getAppointmentdate());
					appointment.setAppointmenttime(app.getAppointmenttime());
					
					appointment.setPurposeText(app.getPurposeText());
					appointment.setTreatementStep(app.getTreatementStep());
					appointment.setPatientid(newUser);
					if (app.getCaremember() != null) {
						appointment.setCaremember(app.getCaremember());
					}
					appointment.setCareMemberName(app.getCareMemberName());
					BaseDAO.save(appointment);
				}
				accounts++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}
}

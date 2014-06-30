package nav.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;

import models.PatienCareTeamDTO;
import models.SecurityQuestionDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserTypeDTO;
import util.CommonUtil;
import util.JPAUtil;
import nav.dto.SignUpMemberBean;
import nav.dto.UserBean;
public class UserDAO {

	public static UserBean getByUserEmail(String email) {
		return getUserByField("email",email);
	}
	
	public static UserBean getByUserId(String usreId) {
		Integer in = new Integer(usreId);
		return getUserByField("id",in);
	}
	public static UserBean getUserByField(String fieldName, Object value) {
		UserBean account = null;
		UserDTO dto = null;
		try {
			dto = getUserBasicByField(fieldName,value);
			if(dto != null) {
				account = new UserBean();
				account.setId(dto.getId());
				account.setName(dto.getName().trim());
				account.setEmail(dto.getEmail().trim());
				account.setUserType(dto.getUserType());
				account.setActive(dto.isActive());
				account.setVerifiedFlag(dto.isIsverified());
			}
		} catch(Exception e) {
		}
		return account;
	}
	
	public static UserDTO getUserBasicByField(String fieldName, Object value) {
		UserDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserDTO> query = em.createQuery("SELECT c FROM UserDTO c WHERE c."+fieldName+" = :field", UserDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static boolean updateUserActivationFlag(String id, boolean isverified,String op) {
		UserDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Integer in = new Integer(id);
			TypedQuery<UserDTO> query = em.createQuery("SELECT c FROM UserDTO c WHERE c.id = :field", UserDTO.class); 
			query.setParameter("field", in);
			dto = query.getSingleResult();
			if(op.equals("v")) {
				dto.setIsverified(isverified);
			} else {
				dto.setActive(isverified);
			}
			em.getTransaction().begin();
			em.persist(dto);
			em.getTransaction().commit();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return true;
	}
	
	public static UserDetailsDTO getDetailsByVerificationCode(String vCode) {
		java.util.UUID uid =  UUID.fromString(vCode);
		UserDetailsDTO dto = getDetailsByField("verificationcode", uid);
		return dto;
	}
	
	public static UserDetailsDTO getDetailsById(String usreId) {
		Integer in = new Integer(usreId);
		UserDetailsDTO dto = getDetailsByField("id", in);
		return dto;
	}
	
	public static UserDetailsDTO getDetailsById(int usreId) {
		Integer in = new Integer(usreId);
		UserDetailsDTO dto = getDetailsByField("id", in);
		return dto;
	}
	
	public static UserDetailsDTO getDetailsByField(String fieldName, Object value) {
		UserDetailsDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserDetailsDTO> query = em.createQuery("SELECT c FROM UserDetailsDTO c WHERE c."+fieldName+" = :field", UserDetailsDTO.class); 
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static <Y> List<UserDTO> getAll(String userType,String uname) {
		
		EntityManager em = JPAUtil.getEntityManager();
	try{
		 
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<UserDTO> cq = cb.createQuery(UserDTO.class);
	    Root<UserDTO> from = cq.from(UserDTO.class);
	    cq.select(from);
	    
	    List criteriaList = new ArrayList();
	    Predicate predicate = cb.gt(from.<Integer> get("id"), 1);
	    criteriaList.add(predicate);

	    if(userType != null &&  !"0".equals(userType)){
	    	Predicate predicate2= cb.equal(from.get("usertypeid").get("id"), Integer.parseInt(userType));
	    	criteriaList.add(predicate2);
	    }

	    if(StringUtils.isNotBlank(uname) ) {
	    	Expression<String> path = from.get("name");
	    	Predicate predicate3 = cb.like(path, uname+"%");
	    	criteriaList.add(predicate3);
	    }
        cq.where(cb.and((Predicate[]) criteriaList.toArray(new Predicate[0])));

	    cq.orderBy(cb.asc(from.get("id")));
	    
	    TypedQuery<UserDTO> query = em.createQuery(cq);
	    
	    return query.getResultList();
	}catch(Exception e) {
		e.printStackTrace();
		
	}
	    return null;
	}
	
	public static UserDTO getAccountByUserEmail(String email) {
		UserDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<UserDTO> query = em.createQuery("SELECT c FROM UserDTO c WHERE c.email = :email", UserDTO.class); 
			query.setParameter("email", email);
			dto = query.getSingleResult();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return dto;
	}

	public static UserDTO parseUserObject(SignUpMemberBean memberBean) {
		UserDTO dto = new UserDTO();
		dto.setEmail(memberBean.getEmail());
		dto.setName(memberBean.getUserName());
		dto.setPassword(CommonUtil.hashPassword(memberBean.getPassword()));
		UserTypeDTO userTypedto = UserTypeDAO.getEntityById(memberBean.getUserType());
		if(userTypedto.getAbbravation() == 'p') {
			dto.setIsverified(false);
			dto.setActive(false);
		} else {
			dto.setIsverified(true);
			dto.setActive(true);
		}
		dto.setUserType(userTypedto.getAbbravation());
		dto.setUsertypeid(userTypedto);
		return dto;
	}

	public static UserDetailsDTO parseUserDetailObject(SignUpMemberBean memberBean) {
		UserDetailsDTO dto = new UserDetailsDTO();
		dto.setEmail(memberBean.getEmail());
		return dto;
	}
	
	public static boolean updateUserDetails(UserDetailsDTO detailsDTO) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(detailsDTO.getUser());
			em.merge(detailsDTO);
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean updateUserBasic(UserDTO detailsDTO) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(detailsDTO);
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean parseAndSaveMember(SignUpMemberBean memberBean) {

		DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
		Calendar cl = Calendar.getInstance();

		UserDTO dto = parseUserObject(memberBean);
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(dto);
		em.getTransaction().commit();

		try {
			UserDetailsDTO detailsDTO = new UserDetailsDTO();
			detailsDTO.setId(dto.getId());
			detailsDTO.setUser(dto);
			detailsDTO.setContactMethod(ContactTypeDAO.getEntityById(memberBean.getContactMethod()));
			detailsDTO.setCreatedate(cl.getTime());
			detailsDTO.setEditdate(cl.getTime());
			detailsDTO.setEditedBy(dto);
			detailsDTO.setEmail(memberBean.getEmail());
			
			if(StringUtils.isNotBlank(memberBean.getDob())) {
				detailsDTO.setDob(df.parse(memberBean.getDob()));
			}
			if(StringUtils.isNotBlank(memberBean.getFirstName())) {
				detailsDTO.setFirstName(memberBean.getFirstName());
			}
			if(StringUtils.isNotBlank(memberBean.getHomePhone())) {
				detailsDTO.setHomePhone(memberBean.getHomePhone());
			}
			if(StringUtils.isNotBlank(memberBean.getLastName())) {
				detailsDTO.setLastName(memberBean.getLastName());
			}
			if(StringUtils.isNotBlank(memberBean.getMobile())) {
				detailsDTO.setMobile(memberBean.getMobile());
			}
			if(StringUtils.isNotBlank(memberBean.getSea1())) {
				detailsDTO.setSea1(memberBean.getSea1());
			}
			if(StringUtils.isNotBlank(memberBean.getSea2())) {
				detailsDTO.setSea2(memberBean.getSea2());
			}
			detailsDTO.setSeq1(SecurityQuestionDAO.getEntityById(memberBean.getSeq1()));
			detailsDTO.setSeq2(SecurityQuestionDAO.getEntityById(memberBean.getSeq2()));
			detailsDTO.setTocflag(memberBean.isTosFlag());
			detailsDTO.setTosflag(memberBean.isSmtFlag());
			detailsDTO.setVerificationcode(UUID.randomUUID());
			em.getTransaction().begin();
			em.persist(detailsDTO);
			em.getTransaction().commit();

			//Create care team by default if patient.
			System.out.println(dto.getUsertypeid().getAbbravation());
			if(dto.getUsertypeid().getAbbravation() == 'p') {
				System.out.println("Adding care team");
				CareTeamDAO.createPatienCareTeamAll(dto);
			}
			System.out.println("care team Added");
		} catch(ParseException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("New ID id " + dto.getId());
		return true;
	}
}
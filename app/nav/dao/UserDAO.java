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
import javax.persistence.NoResultException;
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

import models.ExpertDetailDTO;
import models.PatienCareTeamDTO;
import models.PatientConcernDTO;
import models.PatientDetailDTO;
import models.SecurityQuestionDTO;
import models.SideEffectDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserTypeDTO;
import util.CommonUtil;
import util.JPAUtil;
import nav.dto.CareMember;
import nav.dto.SignUpMemberBean;
import nav.dto.UserBean;
public class UserDAO {

//	public static UserBean getByUserEmail(String email) {
//		return getUserByField("email",email);
//	}
	
	public static UserBean getUserVerified(String email) {
		UserDTO dto = null;
		UserBean account = null;
		try {
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<UserDTO> query = em.createQuery("FROM UserDTO c WHERE upper(c.email) = upper(:email)", UserDTO.class);
			query.setMaxResults(1);
			query.setParameter("email", email);
			dto = query.getSingleResult();
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
			e.printStackTrace();
		}
		return account;
	}
	
	public static UserBean getByUserEmail(String email) {
		UserDTO dto = null;
		UserBean account = null;
		try {
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<UserDTO> query = em.createQuery("FROM UserDTO c WHERE upper(c.email) = upper(:email) and c.isActive=true", UserDTO.class); 
			query.setParameter("email", email);
			dto = query.getSingleResult();
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
			e.printStackTrace();
		}
		return account;
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
		try {
			Integer in = new Integer(usreId);
		
			UserDetailsDTO dto = getDetailsByField("id", in);
			return dto;
		} catch(Exception e) {}
		return null;
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
	
	public static List<CareMember> verifiedCareMembers() {
		EntityManager em = JPAUtil.getEntityManager();		
		List<UserDTO> users = null;
		List<CareMember> members = new ArrayList<CareMember>();
		try {						
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<UserDTO> c = cb.createQuery(UserDTO.class);
		    Root<UserDTO> user = c.from(UserDTO.class);		    		   
		    c.select(user).where(cb.and(cb.or(cb.equal(user.get("usertypeid").get("id"), new Long(4)), cb.equal(user.get("usertypeid").get("id"), new Long(5))), cb.equal(user.get("isverified"), true)));		    
		    TypedQuery<UserDTO> query = em.createQuery(c);
		    users = query.getResultList();		    
		    for (UserDTO u : users) {
		    	CareMember cm = new CareMember();		    	
		    	ExpertDetailDTO expert = null;
		    	UserDetailsDTO details = null;
		    	TypedQuery<UserDetailsDTO> userQuery = em.createQuery("SELECT c FROM UserDetailsDTO c WHERE c.id = :id", UserDetailsDTO.class); 
		    	userQuery.setParameter("id", u.getId());
				try {
					details = userQuery.getSingleResult();
				} catch (NoResultException e1) {
					e1.printStackTrace();
				}		    	
		    	TypedQuery<ExpertDetailDTO> expertQuery = em.createQuery("SELECT e FROM ExpertDetailDTO e WHERE e.id = :id", ExpertDetailDTO.class);
		    	expertQuery.setParameter("id", u.getId());
		    	try {
					expert = expertQuery.getSingleResult();
				} catch (NoResultException e2) {
					e2.printStackTrace();
				}				
		    	cm.setId(u.getId());
		    	cm.setFirstName(details.getFirstName());
		    	cm.setLastName(details.getLastName());		    	
		    	if (expert != null) {
		    		cm.setDesignation(expert.getDesignation().getAbbr());
		    	}		    	
		    	members.add(cm);
		    }						
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		return members;
	}
	
	public static List<CareMember> verifiedDoctors() {
		EntityManager em = JPAUtil.getEntityManager();		
		List<UserDTO> users = null;
		List<CareMember> members = new ArrayList<CareMember>();
		try {						
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<UserDTO> c = cb.createQuery(UserDTO.class);
		    Root<UserDTO> user = c.from(UserDTO.class);		    		   
		    c.select(user).where(cb.and(cb.equal(user.get("usertypeid").get("id"), new Long(5)), cb.equal(user.get("isverified"), true)));		    
		    TypedQuery<UserDTO> query = em.createQuery(c);
		    users = query.getResultList();		    
		    for (UserDTO u : users) {
		    	CareMember cm = new CareMember();		    	
		    	ExpertDetailDTO expert = null;
		    	UserDetailsDTO details = null;
		    	TypedQuery<UserDetailsDTO> userQuery = em.createQuery("SELECT c FROM UserDetailsDTO c WHERE c.id = :id", UserDetailsDTO.class); 
		    	userQuery.setParameter("id", u.getId());
				try {
					details = userQuery.getSingleResult();
				} catch (NoResultException e1) {
					e1.printStackTrace();
				}		    	
		    	TypedQuery<ExpertDetailDTO> expertQuery = em.createQuery("SELECT e FROM ExpertDetailDTO e WHERE e.id = :id", ExpertDetailDTO.class);
		    	expertQuery.setParameter("id", u.getId());
		    	try {
					expert = expertQuery.getSingleResult();
				} catch (NoResultException e2) {
					e2.printStackTrace();
				}				
		    	cm.setId(u.getId());
		    	cm.setFirstName(details.getFirstName());
		    	cm.setLastName(details.getLastName());		    	
		    	if (expert != null) {
		    		cm.setDesignation(expert.getDesignation().getAbbr());
		    	}		    	
		    	members.add(cm);
		    }						
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		return members;
	}
	
	public static <Y> List<UserDTO> getAllForAdmin(String userType,String uname) {
		
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

	    if(userType != null && "5".equals(userType)) {
//	    	Predicate predicate2= cb.equal(from.get("usertypeid").get("id"), Integer.parseInt(userType));
	    	Predicate predicate2= cb.or(cb.equal(from.get("usertypeid").get("id"), new Long(4)), cb.equal(from.get("usertypeid").get("id"), new Long(5)));
	    	criteriaList.add(predicate2);
	    } else if(userType != null &&  !"0".equals(userType)){
	    	Predicate predicate2= cb.equal(from.get("usertypeid").get("id"), Integer.parseInt(userType));
	    	criteriaList.add(predicate2);
	    }
	    
	    criteriaList.add(cb.equal(from.get("isverified"), true));

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
		if(memberBean.getUserName() != null)
			dto.setName(memberBean.getUserName());
		else
			dto.setName("");
		dto.setPassword(CommonUtil.hashPassword(memberBean.getPassword()));
		UserTypeDTO userTypedto = UserTypeDAO.getEntityById(memberBean.getUserType());
		if(userTypedto.getAbbravation() == 'p') {
			dto.setIsverified(true);
			//dto.setActive(false);
			//Set activated as user should to login without access to other pages than dashboard 
			dto.setActive(true);
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
			if(StringUtils.isNotBlank(memberBean.getContactMethod())) {
				detailsDTO.setContactMethod(ContactTypeDAO.getEntityById(memberBean.getContactMethod()));
			} else {
				detailsDTO.setContactMethod(ContactTypeDAO.getEntityById("1"));
			}
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
			/*if(StringUtils.isNotBlank(memberBean.getSea2())) {
				detailsDTO.setSea2(memberBean.getSea2());
			}*/
			detailsDTO.setSsnLast4(memberBean.getSsnLast4());
			if(StringUtils.isNotBlank(memberBean.getSeq1())) {
				detailsDTO.setSeq1(SecurityQuestionDAO.getEntityById(memberBean.getSeq1()));
			}
			//detailsDTO.setSeq2(SecurityQuestionDAO.getEntityById(memberBean.getSeq2()));
			detailsDTO.setTocflag(memberBean.isTosFlag());
			detailsDTO.setTosflag(memberBean.isSmtFlag());
			detailsDTO.setVerificationcode(UUID.randomUUID());
						
			em.getTransaction().begin();
			em.persist(detailsDTO);
			em.getTransaction().commit();
			
			PatientDetailDTO patientDetailDTO = new PatientDetailDTO();
			patientDetailDTO.setId(dto.getId());
			if(StringUtils.isNotBlank(memberBean.getSupportContactName())) {
				patientDetailDTO.setEc1name(memberBean.getSupportContactName());
			}
			if(StringUtils.isNotBlank(memberBean.getSupportContactPhone())) {
				patientDetailDTO.setEc1number(memberBean.getSupportContactPhone());
			}
			em.getTransaction().begin();
			em.persist(patientDetailDTO);
			em.getTransaction().commit();
			
			//Create care team by default if patient.
			System.out.println(dto.getUsertypeid().getAbbravation());
			if(dto.getUsertypeid().getAbbravation() == 'p') {
				System.out.println("Adding care team");
				CareTeamDAO.createPatienCareTeamAll(dto);
			} else if(dto.getUsertypeid().getAbbravation() == 'c') {
				ExpertDetailDTO expdetails = new ExpertDetailDTO();
				if("5".equalsIgnoreCase(memberBean.getUserType())) { //DR
					expdetails.setDesignation(DesignationMasterDAO.getEntityById("1"));	
				} else { //RN
					expdetails.setDesignation(DesignationMasterDAO.getEntityById("2"));
				}
	    		expdetails.setId(dto.getId());
	    		expdetails.setHomeAddr(null);
	    		expdetails.setPracticeAddr(null);
	    		expdetails.setStatement("");
	    		expdetails.setUser(dto);
        		ExpertDetailDAO.save(expdetails);
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
	
	public static String getUserName(Integer id) {
		String name="";
		UserDetailsDTO details = UserDAO.getDetailsById(id);
		if(StringUtils.isBlank(details.getFirstName()) && StringUtils.isBlank(details.getLastName())) {
			name = details.getUser().getName();
		} else {
			if(StringUtils.isBlank(details.getFirstName()) && !StringUtils.isBlank(details.getLastName()))
				name = details.getLastName();
			else if(!StringUtils.isBlank(details.getFirstName()) && StringUtils.isBlank(details.getLastName()))
				name = details.getFirstName();
			else {
				name = details.getFirstName() + " " + details.getLastName();
			}
		}
		if(details.getUser().getUserType() == 'c') {
			name = details.getFirstName();
		}
		if(StringUtils.isBlank(name)) {
			name = "";
		}
		return name;
	}
}
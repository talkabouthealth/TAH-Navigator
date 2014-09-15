package nav.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.gson.JsonObject;

import nav.dto.DistressBean;

import util.GlobalConstant;
import util.JPAUtil;

import models.PatientDistressDTO;
import models.PatientDistressDetailDTO;
import models.UserDTO;

public class DistressDAO {

	public static PatientDistressDTO savePatientDistress(int distressValue,UserDTO user,String otherDetail) {

		PatientDistressDTO distressDTO = new PatientDistressDTO();
		distressDTO.setDaterecrded(new Date());
		distressDTO.setDistressvalue(distressValue);
		distressDTO.setThrough(GlobalConstant.DISTRESS_MODE_WEB);
		distressDTO.setUser(user);
		distressDTO.setOtherdetail(otherDetail);
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(distressDTO);
			em.getTransaction().commit();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return distressDTO;
	}
	
	public static boolean savePatientDistressDetails(int distresstypeid,boolean distressvalue,PatientDistressDTO patiendistress) {

		PatientDistressDetailDTO distressDTO = new PatientDistressDetailDTO();
		distressDTO.setDistresstypeid(distresstypeid);
		distressDTO.setDistressvalue(distressvalue);
		distressDTO.setPatiendistress(patiendistress);

		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(distressDTO);
			em.getTransaction().commit();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return true;
	}
	
	public static DistressBean getLastDistress(UserDTO patientId,int offset) {
		EntityManager em = JPAUtil.getEntityManager();
		DistressBean bean = null;
		try {
			Integer in = new Integer(patientId.getId());
			TypedQuery<PatientDistressDTO> query = em.createQuery("SELECT c FROM PatientDistressDTO c WHERE c.user.id = :field order by daterecrded desc", PatientDistressDTO.class);
			query.setFirstResult(offset);
			query.setMaxResults(1);
			query.setParameter("field", in);
			PatientDistressDTO dto = query.getSingleResult();
			bean = new DistressBean();
			bean.setCurDist(dto.getDistressvalue());
			bean.setDistressDate(dto.getDaterecrded());
			bean.setOtherdetail(getDistressList(dto.getId()));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return bean;
	}
	
	public static DistressBean getLastDistress(UserDTO patientId) {
		return getLastDistress(patientId,0);
	}
	
	public static String getDistressList(int distressId) {
		String rep = "";
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Integer in = new Integer(distressId);
			TypedQuery<String> query = em.createQuery("select dm.name from DistressTypeMasterDTO dm,PatientDistressDetailDTO pd "
			+"where pd.distresstypeid = dm.id and pd.patiendistress.id = :field", 
					String.class);
//			query.setMaxResults(1);
			query.setParameter("field", in);
			List<String> dto = query.getResultList();
			for (String string : dto) {
				rep += string +",<br/>";
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
		//	em.close();
		}
		if(rep.endsWith(",<br/>")) {
			rep = rep.substring(0,rep.lastIndexOf(",<br/>"));
		}
		return rep;
	}
	
	public static JsonObject getLastDistressData(String patientId) {
		
		EntityManager em = JPAUtil.getEntityManager();
		JsonObject json = new JsonObject();
		try {
			Integer in = new Integer(patientId);
			TypedQuery<Integer> query = em.createQuery("SELECT c.distressvalue FROM PatientDistressDTO c WHERE c.user.id = :field order by daterecrded desc",
					Integer.class);
			query.setMaxResults(10);
			query.setParameter("field", in);
			int index = 10;
			List<Integer> list = query.getResultList();
			for (Integer val : list) {
				json.addProperty(""+(index--), val);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return  json;
	}
}

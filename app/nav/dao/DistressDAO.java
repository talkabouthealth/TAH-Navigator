package nav.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.gson.JsonObject;

import nav.dto.DistressBean;
import util.GlobalConstant;
import util.JPAUtil;
import models.DistressTypeMasterDTO;
import models.PatientDistressDTO;
import models.PatientDistressDetailDTO;
import models.UserDTO;

public class DistressDAO {

	public static PatientDistressDTO savePatientDistress(int distressValue,UserDTO user,String otherDetail,Date daterecrded) {

		PatientDistressDTO distressDTO = new PatientDistressDTO();
		distressDTO.setDaterecrded(daterecrded);
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
	
	public static PatientDistressDTO updateDistressByCareTeam(int distressValue, UserDTO user, Integer updateBy, Date daterecrded, String otherDetail) {
		PatientDistressDTO distressDTO = new PatientDistressDTO();
		distressDTO.setDaterecrded(daterecrded);
		distressDTO.setDistressvalue(distressValue);
		distressDTO.setThrough(GlobalConstant.DISTRESS_MODE_WEB);
		distressDTO.setUser(user);
		distressDTO.setUpdateBy(updateBy);
		distressDTO.setOtherdetail(otherDetail);
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(distressDTO);
			em.getTransaction().commit();
		} catch(Exception e) {
			System.out.println(e.getMessage());
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
	
	public static Map<Long, Integer> distressValues(int patientId, int days) {
		EntityManager em = JPAUtil.getEntityManager();
		Map<Long, Integer> values = new HashMap<Long, Integer>();
		List<PatientDistressDTO> list = new ArrayList<PatientDistressDTO>();
		if (days > 0) {
			Date curDate = new Date();
			Date prevDays = new Date(curDate.getTime() - (days * 86400000L));
			list = em.createQuery("SELECT p FROM PatientDistressDTO p WHERE p.user.id = ?1 AND p.daterecrded >= ?2 ORDER BY p.daterecrded ASC", PatientDistressDTO.class).setParameter(1, patientId).setParameter(2, prevDays).getResultList();
		}
		else {
			list = em.createQuery("SELECT p FROM PatientDistressDTO p WHERE p.user.id = ?1  ORDER BY p.daterecrded ASC", PatientDistressDTO.class).setParameter(1, patientId).getResultList();
		}
		
		
		for (PatientDistressDTO pd : list) {
			Date d = pd.getDaterecrded();
			int distress = pd.getDistressvalue();
			values.put(d.getTime(), distress);
		}		
		return values;		
	}
	
	public static List<String> problemList(int patientId, int days) {
		EntityManager em = JPAUtil.getEntityManager();				
		List result = null;
		List<String> problems = new ArrayList<String>();
		if (days > 0) {
			Date curDate = new Date();
			Date prevDays = new Date(curDate.getTime() - (days * 86400000L));							
			result  = em.createQuery("select dn.name, pdd.patiendistress.otherdetail from PatientDistressDetailDTO pdd JOIN pdd.distressName dn WHERE pdd.patiendistress.user.id = ?1 AND pdd.patiendistress.daterecrded >= ?2").setParameter(1, patientId).setParameter(2, prevDays).getResultList();	
		}
		else {			
			result  = em.createQuery("select dn.name, pdd.patiendistress.otherdetail from PatientDistressDetailDTO pdd JOIN pdd.distressName dn WHERE pdd.patiendistress.user.id = ?1").setParameter(1, patientId).getResultList();  
		}
		Iterator i = result.iterator();		
		while(i.hasNext()) {
			Object[] values = (Object[]) i.next();
			String value1 = "";
			String value2 = "";
			if(values != null)
			{
				if(values.length==2) {
					if(values[0] != null)
						value1 = values[0].toString();
					if(values[1] != null)
						value2 = values[1].toString();
				} else if(values.length==1) {
					if(values[0] != null)
					value1 = values[0].toString();
				}
			}
						
			boolean flag1 = false, flag2 = false;
			for (String problem : problems) {
				if(problem.equalsIgnoreCase(value1)) {
					flag1 = true;
				}
				if (problem.equalsIgnoreCase(value2)) {
					flag2 = true;
				}
			}
			if (!value1.isEmpty() && !flag1) {
				problems.add(value1);
			}
			if (!value2.isEmpty() && !flag2) {
				problems.add(value2);
			}
		}
		return problems;
	}
	
}

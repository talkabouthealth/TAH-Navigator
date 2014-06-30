package nav.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
}

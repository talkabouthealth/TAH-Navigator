package nav.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import util.JPAUtil;
import models.CarePlanPrintDTO;

public class CarePlanPrintDAO {

	public static void saveCarePlanPrint(CarePlanPrintDTO carePlanPrintDTO)
	{
		BaseDAO.save(carePlanPrintDTO);
	}
	
	//new addition
		public static int getPrintCount(int patientId)
		{
			EntityManager em = JPAUtil.getEntityManager();
			List<CarePlanPrintDTO> list = new ArrayList<CarePlanPrintDTO>();
			list = em.createQuery("SELECT p FROM CarePlanPrintDTO p WHERE p.patientId = ?1", CarePlanPrintDTO.class).setParameter(1, patientId).getResultList();
			return list.size();
		}
}

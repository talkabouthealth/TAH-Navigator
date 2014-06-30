package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPAUtil;

import models.PatienCareTeamDTO;
import models.PatientMedicationDTO;
import models.UserDTO;

public class MedicationDAO {

	public static List<PatientMedicationDTO> getMedicine(String fieldName, Object value) {
		System.out.println(fieldName +" : " + value.toString());
		List<PatientMedicationDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientMedicationDTO> query = em.createQuery("SELECT c FROM PatientMedicationDTO c WHERE c."+fieldName+" = :field", PatientMedicationDTO.class); 
			query.setParameter("field", value);

			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
}
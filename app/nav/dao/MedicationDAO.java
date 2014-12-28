package nav.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPAUtil;
import models.MedicineCatlogDTO;
import models.PatienCareTeamDTO;
import models.PatientMedicationDTO;
import models.UserDTO;

public class MedicationDAO extends BaseDAO{

	public static List<PatientMedicationDTO> getMedicine(String fieldName, Object value) {
		System.out.println(fieldName +" : " + value.toString());
		List<PatientMedicationDTO> dto = new ArrayList<PatientMedicationDTO>();
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientMedicationDTO> query = em.createQuery("SELECT c FROM PatientMedicationDTO c WHERE c."+fieldName+" = :field and c.active=true order by id", PatientMedicationDTO.class); 
			query.setParameter("field", value);

			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static PatientMedicationDTO getMedicineByField(String fieldName, Object value) {
		PatientMedicationDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientMedicationDTO> query = em.createQuery("SELECT c FROM PatientMedicationDTO c WHERE c."+fieldName+" = :field and c.active=true", PatientMedicationDTO.class); 
			query.setParameter("field", value);
			query.setMaxResults(1);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<MedicineCatlogDTO> searchMedicineCatlog(String fieldName, Object value) {
		List<MedicineCatlogDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<MedicineCatlogDTO> query = em.createQuery("SELECT new MedicineCatlogDTO(c.id,c.label,c.brandname,c.frequency,c.specialinstruction) FROM MedicineCatlogDTO c WHERE lower(c."+fieldName+") like lower(:field) ", MedicineCatlogDTO.class).setMaxResults(10); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static MedicineCatlogDTO getMedicineCatloagByField(String fieldName, Object value) {
		System.out.println(fieldName +" : " + value.toString());
		MedicineCatlogDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<MedicineCatlogDTO> query = em.createQuery("SELECT c FROM MedicineCatlogDTO c WHERE c."+fieldName+" = :field", MedicineCatlogDTO.class); 
			query.setParameter("field", value);
			query.setMaxResults(1);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<PatientMedicationDTO> futureMedications(int patientId) {
		List<PatientMedicationDTO> medications = new ArrayList<PatientMedicationDTO>();
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientMedicationDTO> query = em.createQuery("SELECT p FROM PatientMedicationDTO p WHERE p.patientid = :patientId and p.active= :active and p.startdate > :currentdate order by p.startdate asc", PatientMedicationDTO.class); 
			query.setParameter("patientId", patientId);
			query.setParameter("active", true);
			query.setParameter("currentdate", new Date());
			medications = query.getResultList();
		} catch(Exception e) {
			//e.printStackTrace();
		} 
		return medications;
	}
	
	public static List<PatientMedicationDTO> currentMedications(int patientId) {
		List<PatientMedicationDTO> medications = new ArrayList<PatientMedicationDTO>();
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientMedicationDTO> query = em.createQuery("SELECT p FROM PatientMedicationDTO p WHERE p.patientid = :patientId and p.active= :active and p.startdate <= :currentdate and p.enddate >= :currentdate order by p.enddate asc", PatientMedicationDTO.class); 
			query.setParameter("patientId", patientId);
			query.setParameter("active", true);
			query.setParameter("currentdate", new Date());
			medications = query.getResultList();
		} catch(Exception e) {
			//e.printStackTrace();
		} 
		return medications;
	}
	
	public static List<PatientMedicationDTO> pastMedications(int patientId) {
		List<PatientMedicationDTO> medications = new ArrayList<PatientMedicationDTO>();
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientMedicationDTO> query = em.createQuery("SELECT p FROM PatientMedicationDTO p WHERE p.patientid = :patientId and p.active= :active and p.enddate < :currentdate order by p.enddate desc", PatientMedicationDTO.class); 
			query.setParameter("patientId", patientId);
			query.setParameter("active", true);
			query.setParameter("currentdate", new Date());
			medications = query.getResultList();
		} catch(Exception e) {
			//e.printStackTrace();
		} 
		return medications;
	}
	
}
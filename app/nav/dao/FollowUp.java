package nav.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.PatientConcernDTO;
import models.PatientFollowUpCareItemDTO;
import models.PatientGoalDTO;
import models.PatientStSideEffectDTO;
import models.PatientSurgeryInfoDTO;
import models.SideEffectDTO;
import models.SurgeryTypeDTO;
import models.TreatmentRegionDTO;
import util.JPAUtil;

public class FollowUp {
	public static List<PatientConcernDTO> getPatientConcerns(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientConcernDTO> concerns = null;
		try {
			TypedQuery<PatientConcernDTO> query = em.createQuery("FROM PatientConcernDTO p WHERE p.userId = :userId", PatientConcernDTO.class);
			query.setParameter("userId", patientId);
			concerns = query.getResultList();
		} 
		catch(Exception e) {
			//e.printStackTrace();
		}
		return concerns;
	}
	
	public static List<PatientGoalDTO> getPatientGoals(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientGoalDTO> goals = null;
		try {
			TypedQuery<PatientGoalDTO> query = em.createQuery("FROM PatientGoalDTO p WHERE p.userId = :userId", PatientGoalDTO.class);
			query.setParameter("userId", patientId);
			goals = query.getResultList();
		} 
		catch(Exception e) {
			//e.printStackTrace();
		}
		return goals;
	}
	
	public static List<PatientFollowUpCareItemDTO> getPatientCareItems(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientFollowUpCareItemDTO> careItems = null;
		try {
			TypedQuery<PatientFollowUpCareItemDTO> query = em.createQuery("FROM PatientFollowUpCareItemDTO p WHERE p.userId = :userId", PatientFollowUpCareItemDTO.class);
			query.setParameter("userId", patientId);
			careItems = query.getResultList();
		} 
		catch(Exception e) {
			//e.printStackTrace();
		}
		return careItems;
	}
	
	public static PatientConcernDTO getConcern(Integer concernId) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientConcernDTO concern = null;
		try {
			TypedQuery<PatientConcernDTO> query = em.createQuery("FROM PatientConcernDTO p WHERE p.id = :id", PatientConcernDTO.class);
			query.setParameter("id", concernId);
			concern = query.getSingleResult();
		} 
		catch(Exception e) {
			//e.printStackTrace();
		}
		return concern;
	}
	
	public static PatientGoalDTO getGoal(Integer goalId) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientGoalDTO goal = null;
		try {
			TypedQuery<PatientGoalDTO> query = em.createQuery("FROM PatientGoalDTO p WHERE p.id = :id", PatientGoalDTO.class);
			query.setParameter("id", goalId);
			goal = query.getSingleResult();
		} 
		catch(Exception e) {
			//e.printStackTrace();
		}
		return goal;
	}
	
	public static PatientFollowUpCareItemDTO getCareItem(Integer careItemId) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientFollowUpCareItemDTO careItem = null;
		try {
			TypedQuery<PatientFollowUpCareItemDTO> query = em.createQuery("FROM PatientFollowUpCareItemDTO p WHERE p.id = :id", PatientFollowUpCareItemDTO.class);
			query.setParameter("id", careItemId);
			careItem = query.getSingleResult();
		} 
		catch(Exception e) {
			//e.printStackTrace();
		}
		return careItem;
	}
	
	public static void removeConcern(Integer concernId) {
		EntityManager em = JPAUtil.getEntityManager();
		if (concernId != null) {
			Query query = em.createQuery("DELETE FROM PatientConcernDTO p WHERE p.id = :id");
			query.setParameter("id", concernId);
			em.getTransaction().begin();
			query.executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public static void removeGoal(Integer goalId) {
		EntityManager em = JPAUtil.getEntityManager();
		if (goalId != null) {
			Query query = em.createQuery("DELETE FROM PatientGoalDTO p WHERE p.id = :id");
			query.setParameter("id", goalId);
			em.getTransaction().begin();
			query.executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public static void removeCareItem(Integer careItemId) {
		EntityManager em = JPAUtil.getEntityManager();
		if (careItemId != null) {
			Query query = em.createQuery("DELETE FROM PatientFollowUpCareItemDTO p WHERE p.id = :id");
			query.setParameter("id", careItemId);
			em.getTransaction().begin();
			query.executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public static void saveConcern(Integer patientId, Integer concernId, Map<String, String> fupConcern) {
		EntityManager em = JPAUtil.getEntityManager();
		String concern = fupConcern.get("concern");
		String nextStep = fupConcern.get("nextStep");
		String notes = fupConcern.get("notes");
		
		
		PatientConcernDTO pcDto = null;
		if (concernId != null) {
			TypedQuery<PatientConcernDTO> concernQuery = em.createQuery("SELECT p FROM PatientConcernDTO p WHERE p.id = :id", PatientConcernDTO.class); 
			concernQuery.setParameter("id", concernId);
			try {
				pcDto = concernQuery.getSingleResult();
			} catch (NoResultException e) {
				
			}
		}
		else {
			pcDto = new PatientConcernDTO();
			pcDto.setConcernDate(new Date());
			pcDto.setUserId(patientId);
			
		}
		pcDto.setConcern(concern);
		if (nextStep != null) {
			pcDto.setNextStep(nextStep);
		}
		if (notes != null) {
			pcDto.setNotes(notes);
		}
		em.getTransaction().begin();
		em.persist(pcDto);
		em.getTransaction().commit();
	}
	
	public static void saveGoal(Integer patientId, Integer goalId, Map<String, String> fupGoal) {
		EntityManager em = JPAUtil.getEntityManager();
		String goal = fupGoal.get("goal");
		String nextStep = fupGoal.get("nextStep");
		String goalDeadline = fupGoal.get("goalDeadline");
		String notes = fupGoal.get("notes");
		
		
		PatientGoalDTO pgDto = null;
		if (goalId != null) {
			TypedQuery<PatientGoalDTO> goalQuery = em.createQuery("SELECT p FROM PatientGoalDTO p WHERE p.id = :id", PatientGoalDTO.class); 
			goalQuery.setParameter("id", goalId);
			try {
				pgDto = goalQuery.getSingleResult();
			} catch (NoResultException e) {
				
			}
		}
		else {
			pgDto = new PatientGoalDTO();
			pgDto.setUserId(patientId);
			
		}
		pgDto.setGoal(goal);
		if (nextStep != null) {
			pgDto.setNextStep(nextStep);
		}
		if (notes != null) {
			pgDto.setNotes(notes);
		}
		
		if (goalDeadline != null) {
			if (!goalDeadline.isEmpty()) {
				//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				Date date;
				try {
					date = df.parse(goalDeadline);
					pgDto.setGoalDeadline(date);
				} catch (ParseException e) {
					//e.printStackTrace();
				}
			}
			else {
				pgDto.setGoalDeadline(null);
			}
		}
		
		em.getTransaction().begin();
		em.persist(pgDto);
		em.getTransaction().commit();
	}
	
	public static void saveCareItem(Integer patientId, Integer careItemId, Map<String, String> fupCareItem) {
		EntityManager em = JPAUtil.getEntityManager();
		String activity = fupCareItem.get("activity");
		String frequency = fupCareItem.get("frequency");
		String endDate = fupCareItem.get("endDate");
		String ongoing = fupCareItem.get("ongoing");
		String purpose = fupCareItem.get("purpose");
		String doctor = fupCareItem.get("doctor");
		
		
		PatientFollowUpCareItemDTO pciDto = null;
		if (careItemId != null) {
			TypedQuery<PatientFollowUpCareItemDTO> careItemQuery = em.createQuery("SELECT p FROM PatientFollowUpCareItemDTO p WHERE p.id = :id", PatientFollowUpCareItemDTO.class); 
			careItemQuery.setParameter("id", careItemId);
			try {
				pciDto = careItemQuery.getSingleResult();
			} catch (NoResultException e) {
				
			}
		}
		else {
			pciDto = new PatientFollowUpCareItemDTO();
			pciDto.setUserId(patientId);
			
		}
		pciDto.setActivity(activity);
		if (frequency != null) {
			pciDto.setFrequency(frequency);
		}
		if (purpose != null) {
			pciDto.setPurpose(purpose);
		}
		if (doctor != null) {
			pciDto.setDoctor(doctor);
		}
		
		if (ongoing != null && !ongoing.isEmpty()) {
			pciDto.setOngoing(ongoing);
			pciDto.setEndDate(null);
		}
		else if (endDate != null){
			if (!endDate.isEmpty()) {
				//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				Date date;
				try {
					date = df.parse(endDate);
					pciDto.setEndDate(date);
				} catch (ParseException e) {
					//e.printStackTrace();
				}
			}
			else {
				pciDto.setEndDate(null);
			}
			pciDto.setOngoing(null);
		}
		
		em.getTransaction().begin();
		em.persist(pciDto);
		em.getTransaction().commit();
	}
	
}

package nav.dao;

import javax.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.*;
import util.*;

public class Treatment {
	
	public static List<RadiationTypeDTO> allRadiationTypes() {
		List<RadiationTypeDTO> radiationTypes = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<RadiationTypeDTO> query = em.createQuery("FROM RadiationTypeDTO", RadiationTypeDTO.class); 
			radiationTypes = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return radiationTypes;
	}
	
	public static List<SurgeryTypeDTO> allSurgeryTypes() {
		List<SurgeryTypeDTO> surgeryTypes = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<SurgeryTypeDTO> query = em.createQuery("FROM SurgeryTypeDTO", SurgeryTypeDTO.class); 
			surgeryTypes = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return surgeryTypes;
	}
	
	public static List<RadiationScheduleDTO> allRadiationSchedules() {
		List<RadiationScheduleDTO> radiationSchedules = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<RadiationScheduleDTO> query = em.createQuery("FROM RadiationScheduleDTO", RadiationScheduleDTO.class); 
			radiationSchedules = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return radiationSchedules;
	}
	
	public static List<ChemoScheduleDTO> allChemoSchedules() {
		List<ChemoScheduleDTO> chemoSchedules = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<ChemoScheduleDTO> query = em.createQuery("FROM ChemoScheduleDTO", ChemoScheduleDTO.class); 
			chemoSchedules = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return chemoSchedules;
	}
	
	public static List<TreatmentRegionDTO> allTreatementRegions() {
		List<TreatmentRegionDTO> treatmentRegions = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<TreatmentRegionDTO> query = em.createQuery("FROM TreatmentRegionDTO", TreatmentRegionDTO.class); 
			treatmentRegions = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return treatmentRegions;
	}
	public static List<SideEffectDTO> allSideEffects() {
		List<SideEffectDTO> sideEffects = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<SideEffectDTO> query = em.createQuery("FROM SideEffectDTO", SideEffectDTO.class); 
			sideEffects = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return sideEffects;
	}
	
	public static List<MedicationDTO> getAllMedications() {
		List<MedicationDTO> medications = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<MedicationDTO> query = em.createQuery("FROM MedicationDTO", MedicationDTO.class); 
			medications = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return medications;
	}
	public static void saveSurgeryInfo(Integer patientId, Map<String, String> siInfo, Map<Integer, Integer> sideEffect) {
		EntityManager em = JPAUtil.getEntityManager();
		String stId = siInfo.get("stId");
		String surgeryDate = siInfo.get("surgeryDate");
		String trId = siInfo.get("trId");
		String notes = siInfo.get("notes");
		
		PatientSurgeryInfoDTO psiDto = new PatientSurgeryInfoDTO();
		psiDto.setUserId(patientId);
		
		if (stId != null && !stId.isEmpty()) {
			psiDto.setStId(Integer.valueOf(stId));
		}
		if (surgeryDate != null && !surgeryDate.isEmpty()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = df.parse(surgeryDate);
				psiDto.setSurgeryDate(date);
			} catch (ParseException e) {
				
			}
			
		}
		if (trId != null && !trId.isEmpty()) {
			psiDto.setTrId(Integer.valueOf(trId));
		}
		if (notes != null && !notes.isEmpty()) {
			psiDto.setNotes(notes);
		}
		em.getTransaction().begin();
		em.persist(psiDto);
		em.getTransaction().commit();
		
		Integer psiId = psiDto.getId();
		if (psiId != null && sideEffect != null) {
			PatientStSideEffectDTO pstSeDto;
			for (Integer i: sideEffect.keySet()) {
				Integer id = sideEffect.get(i);
				pstSeDto = new PatientStSideEffectDTO();
				pstSeDto.setPsiId(psiId);
				pstSeDto.setSeId(id);
				em.getTransaction().begin();
				em.persist(pstSeDto);
				em.getTransaction().commit();
			}
		}
	}
	public static void saveChemoTreatment(Integer patientId, Map<String, String> cttInfo, Map<Integer, Integer> sideEffect) {
		EntityManager em = JPAUtil.getEntityManager();
		String medicationId = cttInfo.get("medicationId");
		String cycleNo = cttInfo.get("cycleNo");
		String csId = cttInfo.get("csId");
		String doseReduction = cttInfo.get("doseReduction");
		String startDate = cttInfo.get("startDate");
		String endDate = cttInfo.get("endDate");
		String notes = cttInfo.get("notes");
		
		PatientChemoTreatmentDTO pctDto = new PatientChemoTreatmentDTO();
		pctDto.setUserId(patientId);
		
		if (medicationId != null && !medicationId.isEmpty()) {
			pctDto.setMedicationId(Integer.valueOf(medicationId));
		}
		if (cycleNo != null && !cycleNo.isEmpty()) {
			pctDto.setCycleNo(Integer.valueOf(cycleNo));
		}
		if (csId != null && !csId.isEmpty()) {
			pctDto.setCsId(Integer.valueOf(csId));
		}
		if (doseReduction != null && !doseReduction.isEmpty()) {
			pctDto.setDoseReduction(Integer.valueOf(doseReduction));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null && !startDate.isEmpty()) {
			try {
				Date date = df.parse(startDate);
				pctDto.setStartDate(date);
			} catch (ParseException e) {
				
			}
		}
		if (endDate != null && !endDate.isEmpty()) {
			try {
				Date date = df.parse(endDate);
				pctDto.setEndDate(date);
			} catch (ParseException e) {
				
			}
		}
		if (notes != null && !notes.isEmpty()) {
			pctDto.setNotes(notes);
		}
		em.getTransaction().begin();
		em.persist(pctDto);
		em.getTransaction().commit();
		
		Integer pctId = pctDto.getId();
		
		if (pctId != null && sideEffect != null) {
			PatientCttSideEffectDTO pctSeDto;
			for (Integer i: sideEffect.keySet()) {
				Integer id = sideEffect.get(i);
				pctSeDto = new PatientCttSideEffectDTO();
				pctSeDto.setPctId(pctId);
				pctSeDto.setSeId(id);
				em.getTransaction().begin();
				em.persist(pctSeDto);
				em.getTransaction().commit();
			}
		}
	}
	public static void saveRadiationTreatment(Integer patientId, Map<String, String> rtInfo, Map<Integer, Integer> sideEffect) {
		EntityManager em = JPAUtil.getEntityManager();
		String radiationType = rtInfo.get("radiationType");
		String dose = rtInfo.get("dose");
		String schedule = rtInfo.get("schedule");
		String startDate = rtInfo.get("startDate");
		String endDate = rtInfo.get("endDate");
		String region = rtInfo.get("region");
		String notes = rtInfo.get("notes");
		
		Integer rtId = null;
		Integer rsId = null;
		Integer trId = null;
		Integer prtId = null;
		
		RadiationTypeDTO rtDto = null;
		RadiationScheduleDTO rsDto = null;
		TreatmentRegionDTO trDto = null;
		PatientRadiationTreatmentDTO prtDto = null;
		
		TypedQuery<RadiationTypeDTO> query1 = em.createQuery("SELECT c FROM RadiationTypeDTO c WHERE c.name = :name", RadiationTypeDTO.class); 
		query1.setParameter("name", radiationType);
		try {
			rtDto = query1.getSingleResult();
			rtId = rtDto.getId();
		} catch (NoResultException e) {
			
		}
		
		TypedQuery<RadiationScheduleDTO> query2 = em.createQuery("SELECT c FROM RadiationScheduleDTO c WHERE c.timePeriod = :timePeriod", RadiationScheduleDTO.class); 
		query2.setParameter("timePeriod", schedule);
		try {
			rsDto = query2.getSingleResult();
			rsId = rsDto.getId();
		} catch (NoResultException e) {
			
		}
		
		TypedQuery<TreatmentRegionDTO> query3 = em.createQuery("SELECT c FROM TreatmentRegionDTO c WHERE c.region = :region", TreatmentRegionDTO.class); 
		query3.setParameter("region", region);
		try {
			trDto = query3.getSingleResult();
			trId = trDto.getId();
		} catch (NoResultException e) {
			
		}
		
		prtDto = new PatientRadiationTreatmentDTO();
		prtDto.setUserId(patientId);
		prtDto.setRtId(rtId);
		prtDto.setRsId(rsId);
		prtDto.setTrId(trId);
		prtDto.setDose(dose);
		prtDto.setNotes(notes);
		if (!startDate.isEmpty()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = df.parse(startDate);
				prtDto.setStartDate(date);
			} catch (ParseException e) {
				//e.printStackTrace();
			}
		}
		if (!endDate.isEmpty()) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = df.parse(endDate);
				prtDto.setEndDate(date);
			} catch (ParseException e) {
				//e.printStackTrace();
			}
		}
		
		em.getTransaction().begin();
		em.persist(prtDto);
		em.getTransaction().commit();
		
		prtId = prtDto.getId();
		
		if (prtId != null && sideEffect != null) {
			PatientRtSideEffectDTO prtSeDto;
			for (Integer i: sideEffect.keySet()) {
				Integer id = sideEffect.get(i);
				prtSeDto = new PatientRtSideEffectDTO();
				prtSeDto.setPrtId(prtId);
				prtSeDto.setSeId(id);
				em.getTransaction().begin();
				em.persist(prtSeDto);
				em.getTransaction().commit();
			}
		}
		
	}

	public static List<PatientRadiationTreatmentDTO> getPatientRadiationTreatments(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientRadiationTreatmentDTO> radiationTreatments = null;
		try {
			TypedQuery<PatientRadiationTreatmentDTO> query = em.createQuery("FROM PatientRadiationTreatmentDTO c WHERE c.userId = :userId", PatientRadiationTreatmentDTO.class);
			query.setParameter("userId", patientId);
			radiationTreatments = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		for (PatientRadiationTreatmentDTO prtDto : radiationTreatments) {
			em.refresh(prtDto);
			RadiationTypeDTO rtDto = prtDto.getRtDto();
			em.refresh(rtDto);
			RadiationScheduleDTO rsDto = prtDto.getRsDto();
			em.refresh(rsDto);
			TreatmentRegionDTO trDto = prtDto.getTrDto();
			em.refresh(trDto);
			List<PatientRtSideEffectDTO> prtSeDtos = prtDto.getPrtSeDtos();
			for (PatientRtSideEffectDTO prtSeDto : prtSeDtos) {
				em.refresh(prtSeDto);
				SideEffectDTO seDto = prtSeDto.getSeDto();
				em.refresh(seDto);
			}
		}
		return radiationTreatments;
	}
	
	public static List<PatientChemoTreatmentDTO> getPatientChemoTreatments(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientChemoTreatmentDTO> chemoTreatments = null;
		try {
			TypedQuery<PatientChemoTreatmentDTO> query = em.createQuery("FROM PatientChemoTreatmentDTO c WHERE c.userId = :userId", PatientChemoTreatmentDTO.class);
			query.setParameter("userId", patientId);
			chemoTreatments = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		for (PatientChemoTreatmentDTO pctDto : chemoTreatments) {
			em.refresh(pctDto);
			MedicationDTO medDto = pctDto.getMedDto();
			em.refresh(medDto);
			MedicationGenNameDTO mgnDto = medDto.getMgnDto();
			em.refresh(mgnDto);
			MedicationBrandNameDTO mbnDto = medDto.getMbnDto();
			em.refresh(mbnDto);
			ChemoScheduleDTO csDto = pctDto.getCsDto();
			em.refresh(csDto);
			List<PatientCttSideEffectDTO> pctSeDtos =  pctDto.getPctSeDtos();
			for (PatientCttSideEffectDTO pctSeDto: pctSeDtos) {
				em.refresh(pctSeDto);
				SideEffectDTO seDto = pctSeDto.getSeDto();
				em.refresh(seDto);
			}
		}
		return chemoTreatments;
	}
	
	public static List<PatientSurgeryInfoDTO> getPatientSurgeryInfo(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientSurgeryInfoDTO> surgeryInfo = null;
		try {
			TypedQuery<PatientSurgeryInfoDTO> query = em.createQuery("FROM PatientSurgeryInfoDTO c WHERE c.userId = :userId", PatientSurgeryInfoDTO.class);
			query.setParameter("userId", patientId);
			surgeryInfo = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		for (PatientSurgeryInfoDTO psiDto : surgeryInfo) {
			em.refresh(psiDto);
			TreatmentRegionDTO trDto = psiDto.getTrDto();
			em.refresh(trDto);
			List<PatientStSideEffectDTO> pstSeDtos = psiDto.getPstSeDtos();
			for (PatientStSideEffectDTO pstSeDto: pstSeDtos) {
				em.refresh(pstSeDto);
				SideEffectDTO seDto = pstSeDto.getSeDto();
				em.refresh(seDto);
			}
		}
		return surgeryInfo;
	}
}

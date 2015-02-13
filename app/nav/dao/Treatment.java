package nav.dao;

import javax.persistence.*;

import nav.dto.TAHConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;
import util.*;

public class Treatment {
	
	public static List<RadiationTypeDTO> allRadiationTypes() {
		List<RadiationTypeDTO> radiationTypes = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<RadiationTypeDTO> query = em.createQuery("FROM RadiationTypeDTO order by name", RadiationTypeDTO.class); //where active = true 
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
	
	public static List<SurgeryTypeDTO> getSurgeryTypes(Integer diseseId) {
		List<SurgeryTypeDTO> surgeryTypes = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<SurgeryTypeDTO> query = em.createQuery("FROM SurgeryTypeDTO where (diseaseid = :f1 or diseaseid is null) and active= true order by name", SurgeryTypeDTO.class); 
			query.setParameter("f1", diseseId);
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
			TypedQuery<RadiationScheduleDTO> query = em.createQuery("FROM RadiationScheduleDTO where active = true", RadiationScheduleDTO.class); 
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
	
	public static List<TreatmentRegionDTO> getTreatementRegions(Integer diseseId) {
		List<TreatmentRegionDTO> treatmentRegions = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<TreatmentRegionDTO> query = em.createQuery("FROM TreatmentRegionDTO where (diseaseid = :f1 or diseaseid is null) and active= true order by region", TreatmentRegionDTO.class);
			query.setParameter("f1", diseseId);
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
	
	public static List<MedicineCatlogDTO> getAllMedications() {
		List<MedicineCatlogDTO> medications = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<MedicineCatlogDTO> query = em.createQuery("FROM MedicineCatlogDTO", MedicineCatlogDTO.class); 
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
	public static void saveSurgeryInfo(Integer patientId, Integer treatmentId, Map<String, String> siInfo, Map<Integer, String> sideEffects) {
		EntityManager em = JPAUtil.getEntityManager();
		String surgeryType = siInfo.get("surgeryType");
		String surgeryDate = siInfo.get("surgeryDate");
		String region = siInfo.get("region");
		String notes = siInfo.get("notes");
		String doctor = siInfo.get("doctor");
		Integer stId = null;
		Integer trId = null;
		PatientSurgeryInfoDTO psiDto = null;
		
		if (treatmentId != null) {
			Query deleteQuery = em.createQuery("DELETE FROM PatientStSideEffectDTO p WHERE p.psiId = :psiId");
			deleteQuery.setParameter("psiId", treatmentId);
			em.getTransaction().begin();
			deleteQuery.executeUpdate();
			em.getTransaction().commit();
		}
		UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", userDto.getId());
		
		TypedQuery<TreatmentRegionDTO> trQuery = em.createQuery("SELECT c FROM TreatmentRegionDTO c WHERE (diseaseid = :f1 or diseaseid is null) and c.region = :region", TreatmentRegionDTO.class);
		trQuery.setParameter("f1", patientOtherDetails.getDiseaseId()); 
		trQuery.setParameter("region", region);
		try {
			TreatmentRegionDTO trDto = trQuery.getSingleResult();
			trId = trDto.getId();
		} catch (NoResultException e) {
			TreatmentRegionDTO trDto = new TreatmentRegionDTO();
			trDto.setRegion(region);
			trDto.setActive(false);
			trDto.setDiseaseid(null);
			em.getTransaction().begin();
			em.persist(trDto);
			em.getTransaction().commit();
			trId = trDto.getId();
		}
		
		TypedQuery<SurgeryTypeDTO> stQuery = em.createQuery("SELECT s FROM SurgeryTypeDTO s WHERE (s.diseaseid = :f1 or s.diseaseid is null) and s.name = :name", SurgeryTypeDTO.class);
		stQuery.setParameter("f1", patientOtherDetails.getDiseaseId()); 
		stQuery.setParameter("name", surgeryType);
		try {
			SurgeryTypeDTO stDto = stQuery.getSingleResult();
			stId = stDto.getId();
		} catch (NoResultException e) {
			SurgeryTypeDTO stDto = new SurgeryTypeDTO();
			stDto.setName(surgeryType);
			stDto.setActive(false);
			stDto.setDiseaseid(null);
			em.getTransaction().begin();
			em.persist(stDto);
			em.getTransaction().commit();
			stId = stDto.getId();
		}
		
		if (treatmentId != null) {
			TypedQuery<PatientSurgeryInfoDTO> psiQuery = em.createQuery("SELECT p FROM PatientSurgeryInfoDTO p WHERE p.id = :id", PatientSurgeryInfoDTO.class); 
			psiQuery.setParameter("id", treatmentId);
			try {
				psiDto = psiQuery.getSingleResult();
			} catch (NoResultException e) {
				
			}
		}
		else {
			psiDto = new PatientSurgeryInfoDTO();
		}
		
		psiDto.setUserId(patientId);
		psiDto.setStId(stId);
		psiDto.setTrId(trId);
		
		if (surgeryDate != null && !surgeryDate.isEmpty()) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				Date date = df.parse(surgeryDate);
				psiDto.setSurgeryDate(date);
			} catch (ParseException e) {
				
			}	
		}
		
		if (notes != null && !notes.isEmpty()) {
			psiDto.setNotes(notes);
		}
		psiDto.setDoctor(doctor);
		em.getTransaction().begin();
		em.persist(psiDto);
		em.getTransaction().commit();
		Integer psiId = psiDto.getId();
		
		if (psiId != null && sideEffects != null) {
			TypedQuery<SideEffectDTO> query = em.createQuery("SELECT c FROM  SideEffectDTO c WHERE c.description = :description", SideEffectDTO.class); 
			PatientStSideEffectDTO pstSeDto = null;
			SideEffectDTO seDto = null;
			Integer seId = null;
			for (Integer i: sideEffects.keySet()) {
				String description = sideEffects.get(i);
				query.setParameter("description", description);
				try {
					seDto = query.getSingleResult();
					seId = seDto.getId();
				} catch (NoResultException e) {
					seDto = new SideEffectDTO();
					seDto.setDescription(description);
					em.getTransaction().begin();
					em.persist(seDto);
					em.getTransaction().commit();
					seId = seDto.getId();
				}
				pstSeDto = new PatientStSideEffectDTO();
				pstSeDto.setPsiId(psiId);
				pstSeDto.setSeId(seId);
				em.getTransaction().begin();
				em.persist(pstSeDto);
				em.getTransaction().commit();
			}
		}
	}
	public static void saveChemoTreatment(Integer patientId, Integer treatmentId, Map<String, String> ctInfo, Map<Integer, String> sideEffects) {
		EntityManager em = JPAUtil.getEntityManager();
		String genericName = ctInfo.get("genericName");
		String brandName = ctInfo.get("brandName");
		String cycleNo = ctInfo.get("cycleNo");
		String schedule = ctInfo.get("schedule");
		String doseReduction = ctInfo.get("doseReduction");
		String startDate = ctInfo.get("startDate");
		String endDate = ctInfo.get("endDate");
		String notes = ctInfo.get("notes");
		String doctor = ctInfo.get("doctor");
		
		Integer csId = null;
		PatientChemoTreatmentDTO pctDto = null;
		
		if (treatmentId != null) {
			Query deleteQuery = em.createQuery("DELETE FROM PatientCttSideEffectDTO p WHERE p.pctId = :pctId");
			deleteQuery.setParameter("pctId", treatmentId);
			em.getTransaction().begin();
			deleteQuery.executeUpdate();
			em.getTransaction().commit();
			
			TypedQuery<PatientChemoTreatmentDTO> pctQuery = em.createQuery("SELECT p FROM PatientChemoTreatmentDTO p WHERE p.id = :id", PatientChemoTreatmentDTO.class); 
			pctQuery.setParameter("id", treatmentId);
			try {
				pctDto = pctQuery.getSingleResult();
			} catch (NoResultException e) {
				
			}
		}
		else {
			pctDto = new PatientChemoTreatmentDTO();
		}
		
		/*
		TypedQuery<MedicineCatlogDTO> mQuery = em.createQuery("FROM MedicineCatlogDTO m WHERE m.label = :label AND m.brandname LIKE :brandname", MedicineCatlogDTO.class); 
		mQuery.setParameter("label", genericName);
		mQuery.setParameter("brandname", "%" + brandName + "%");
		try {
			List<MedicineCatlogDTO> mDtos = mQuery.getResultList();
		} catch (NoResultException e) {
			MedicineCatlogDTO mDto = new MedicineCatlogDTO();
			mDto.setLabel(genericName);
			mDto.setBrandname(brandName);
			em.getTransaction().begin();
			em.persist(mDto);
			em.getTransaction().commit();
		}
		*/
		pctDto.setUserId(patientId);
		pctDto.setGenericName(genericName);
		pctDto.setBrandName(brandName);
		if (cycleNo != null && !cycleNo.isEmpty()) {
			pctDto.setCycleNo(Integer.valueOf(cycleNo));
		}
		if (schedule != null && !schedule.isEmpty()) {
			TypedQuery<ChemoScheduleDTO> csQuery = em.createQuery("SELECT c FROM ChemoScheduleDTO c WHERE c.timePeriod = :timePeriod", ChemoScheduleDTO.class); 
			csQuery.setParameter("timePeriod", schedule);
			try {
				ChemoScheduleDTO csDto = csQuery.getSingleResult();
				csId = csDto.getId();
			} catch (NoResultException e) {
				ChemoScheduleDTO csDto = new ChemoScheduleDTO();
				csDto.setTimePeriod(schedule);
				em.getTransaction().begin();
				em.persist(csDto);
				em.getTransaction().commit();
				csId = csDto.getId();
			}
			pctDto.setCsId(csId);
		}
		if (doseReduction != null && !doseReduction.isEmpty()) {			
			pctDto.setDoseReduction(doseReduction);
		}
		if (startDate != null && !startDate.isEmpty()) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				Date date = df.parse(startDate);
				pctDto.setStartDate(date);
			} catch (ParseException e) {
				
			}	
		}
		if (endDate != null && !endDate.isEmpty()) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				Date date = df.parse(endDate);
				pctDto.setEndDate(date);
			} catch (ParseException e) {
				
			}	
		}
		if (notes != null && !notes.isEmpty()) {
			pctDto.setNotes(notes);
		}
		pctDto.setDoctor(doctor);
		em.getTransaction().begin();
		em.persist(pctDto);
		em.getTransaction().commit();
		Integer pctId = pctDto.getId();
		
		if (pctId != null && sideEffects != null) {
			TypedQuery<SideEffectDTO> query = em.createQuery("SELECT c FROM  SideEffectDTO c WHERE c.description = :description", SideEffectDTO.class); 
			PatientCttSideEffectDTO pctSeDto = null;
			SideEffectDTO seDto = null;
			Integer seId = null;
			for (Integer i: sideEffects.keySet()) {
				String description = sideEffects.get(i);
				query.setParameter("description", description);
				try {
					seDto = query.getSingleResult();
					seId = seDto.getId();
				} catch (NoResultException e) {
					seDto = new SideEffectDTO();
					seDto.setDescription(description);
					em.getTransaction().begin();
					em.persist(seDto);
					em.getTransaction().commit();
					seId = seDto.getId();
				}
				pctSeDto = new PatientCttSideEffectDTO();
				pctSeDto.setPctId(pctId);
				pctSeDto.setSeId(seId);
				em.getTransaction().begin();
				em.persist(pctSeDto);
				em.getTransaction().commit();
			}
		}
	}
	public static void removeRadiationData(Integer treatmentId) {
		EntityManager em = JPAUtil.getEntityManager();
		if (treatmentId != null) {
			Query deleteSideEffects = em.createQuery("DELETE FROM PatientRtSideEffectDTO p WHERE p.prtId = :prtId");
			deleteSideEffects.setParameter("prtId", treatmentId);
			Query deleteRadiation = em.createQuery("DELETE FROM PatientRadiationTreatmentDTO p WHERE p.id = :id");
			deleteRadiation.setParameter("id", treatmentId);
			em.getTransaction().begin();
			deleteSideEffects.executeUpdate();
			deleteRadiation.executeUpdate();
			em.getTransaction().commit();
		}
	}
	public static void removeSurgeryData(Integer treatmentId) {
		EntityManager em = JPAUtil.getEntityManager();
		if (treatmentId != null) {
			Query deleteSideEffects = em.createQuery("DELETE FROM PatientStSideEffectDTO p WHERE p.psiId = :psiId");
			deleteSideEffects.setParameter("psiId", treatmentId);
			Query deleteSurgery = em.createQuery("DELETE FROM PatientSurgeryInfoDTO p WHERE p.id = :id");
			deleteSurgery.setParameter("id", treatmentId);
			em.getTransaction().begin();
			deleteSideEffects.executeUpdate();
			deleteSurgery.executeUpdate();
			em.getTransaction().commit();
		}
	}
	public static void removeChemotherapyData(Integer treatmentId) {
		EntityManager em = JPAUtil.getEntityManager();
		if (treatmentId != null) {
			Query deleteSideEffects = em.createQuery("DELETE FROM PatientCttSideEffectDTO p WHERE p.pctId = :pctId");
			deleteSideEffects.setParameter("pctId", treatmentId);
			Query deleteSurgery = em.createQuery("DELETE FROM PatientChemoTreatmentDTO p WHERE p.id = :id");
			deleteSurgery.setParameter("id", treatmentId);
			em.getTransaction().begin();
			deleteSideEffects.executeUpdate();
			deleteSurgery.executeUpdate();
			em.getTransaction().commit();
		}
	}
	public static void saveRadiationTreatment(Integer patientId, Integer treatmentId, Map<String, String> rtInfo, Map<Integer, String> sideEffects) {
		EntityManager em = JPAUtil.getEntityManager();
		String radiationType = rtInfo.get("radiationType");
		String dose = rtInfo.get("dose");
		String schedule = rtInfo.get("schedule");
		String startDate = rtInfo.get("startDate");
		String endDate = rtInfo.get("endDate");
		String region = rtInfo.get("region");
		String notes = rtInfo.get("notes");
		String doctor = rtInfo.get("doctor");
		
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
			rtDto = new RadiationTypeDTO();
			rtDto.setName(radiationType);
			em.getTransaction().begin();
			em.persist(rtDto);
			em.getTransaction().commit();
			rtId = rtDto.getId();
		}
		
		if (schedule != null && !schedule.isEmpty()) {
			TypedQuery<RadiationScheduleDTO> query2 = em.createQuery("SELECT c FROM RadiationScheduleDTO c WHERE c.timePeriod = :timePeriod", RadiationScheduleDTO.class); 
			query2.setParameter("timePeriod", schedule);
			try {
				rsDto = query2.getSingleResult();
				rsId = rsDto.getId();
			} catch (NoResultException e) {
				rsDto = new RadiationScheduleDTO();
				rsDto.setTimePeriod(schedule);
				em.getTransaction().begin();
				em.persist(rsDto);
				em.getTransaction().commit();
				rsId = rsDto.getId();
			}
		}
		
		if (region != null && !region.isEmpty()) {
//			TypedQuery<TreatmentRegionDTO> query3 = em.createQuery("SELECT c FROM TreatmentRegionDTO c WHERE c.region = :region", TreatmentRegionDTO.class);
			
			UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
			PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", userDto.getId());
			
			TypedQuery<TreatmentRegionDTO> query3 = em.createQuery("SELECT c FROM TreatmentRegionDTO c WHERE (diseaseid = :f1 or diseaseid is null) and c.region = :region", TreatmentRegionDTO.class);
			query3.setParameter("f1", patientOtherDetails.getDiseaseId()); 
			query3.setParameter("region", region);
			try {
				trDto = query3.getSingleResult();
				trId = trDto.getId();
			} catch (NoResultException e) {
				trDto = new TreatmentRegionDTO();
				trDto.setRegion(region);
				em.getTransaction().begin();
				em.persist(trDto);
				em.getTransaction().commit();
				trId = trDto.getId();
			}
		}
		if (treatmentId != null) {
			TypedQuery<PatientRadiationTreatmentDTO> prtQuery = em.createQuery("SELECT c FROM PatientRadiationTreatmentDTO c WHERE c.id = :id", PatientRadiationTreatmentDTO.class); 
			prtQuery.setParameter("id", treatmentId);
			try {
				prtDto = prtQuery.getSingleResult();
			} catch (NoResultException e) {
			}
		}
		else {
			prtDto = new PatientRadiationTreatmentDTO();
		}
		prtDto.setUserId(patientId);
		prtDto.setRtId(rtId);
		prtDto.setRsId(rsId);
		prtDto.setTrId(trId);
		prtDto.setDose(dose);
		prtDto.setNotes(notes);
		prtDto.setDoctor(doctor);
		if (!startDate.isEmpty()) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date date;
			try {
				date = df.parse(startDate);
				prtDto.setStartDate(date);
			} catch (ParseException e) {
				//e.printStackTrace();
			}
		}
		if (!endDate.isEmpty()) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
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
		if (treatmentId != null) {
			Query deleteQuery = em.createQuery("DELETE FROM PatientRtSideEffectDTO p WHERE p.prtId = :prtId");
			deleteQuery.setParameter("prtId", treatmentId);
			em.getTransaction().begin();
			deleteQuery.executeUpdate();
			em.getTransaction().commit();
		}
		
		if (prtId != null && sideEffects != null) {
			TypedQuery<SideEffectDTO> query = em.createQuery("SELECT c FROM  SideEffectDTO c WHERE c.description = :description", SideEffectDTO.class); 
			PatientRtSideEffectDTO prtSeDto = null;
			SideEffectDTO seDto = null;
			Integer seId = null;
			for (Integer i: sideEffects.keySet()) {
				String description = sideEffects.get(i);
				query.setParameter("description", description);
				try {
					seDto = query.getSingleResult();
					seId = seDto.getId();
				} catch (NoResultException e) {
					seDto = new SideEffectDTO();
					seDto.setDescription(description);
					em.getTransaction().begin();
					em.persist(seDto);
					em.getTransaction().commit();
					seId = seDto.getId();
				}
				prtSeDto = new PatientRtSideEffectDTO();
				prtSeDto.setPrtId(prtId);
				prtSeDto.setSeId(seId);
				em.getTransaction().begin();
				em.persist(prtSeDto);
				em.getTransaction().commit();
			}
		}
		
		
	}
	public static void refreshRadiationTreatment(PatientRadiationTreatmentDTO prtDto) {
		EntityManager em = JPAUtil.getEntityManager();
		if (prtDto != null) {
			em.refresh(prtDto);
			RadiationTypeDTO rtDto = prtDto.getRtDto();
			if (rtDto != null) {
				em.refresh(rtDto);
			}
			RadiationScheduleDTO rsDto = prtDto.getRsDto();
			if (rsDto != null) {
				em.refresh(rsDto);
			}
			TreatmentRegionDTO trDto = prtDto.getTrDto();
			if (trDto != null) {
				em.refresh(trDto);
			}
			List<PatientRtSideEffectDTO> prtSeDtos = prtDto.getPrtSeDtos();
			if (prtSeDtos != null) {
				for (PatientRtSideEffectDTO prtSeDto : prtSeDtos) {
					em.refresh(prtSeDto);
					SideEffectDTO seDto = prtSeDto.getSeDto();
					em.refresh(seDto);
				}
			}
		}
	}
	public static void refreshChemotherapy(PatientChemoTreatmentDTO pctDto) {
		EntityManager em = JPAUtil.getEntityManager();
		if (pctDto != null) {
			em.refresh(pctDto);
			ChemoScheduleDTO csDto = pctDto.getCsDto();
			if (csDto != null) {
				em.refresh(csDto);
			}
			List<PatientCttSideEffectDTO> pctSeDtos =  pctDto.getPctSeDtos();
			if (pctSeDtos != null) {
				for (PatientCttSideEffectDTO pctSeDto: pctSeDtos) {
					em.refresh(pctSeDto);
					SideEffectDTO seDto = pctSeDto.getSeDto();
					em.refresh(seDto);
				}
			}
		}
	}
	public static void refreshSurgeryInfo(PatientSurgeryInfoDTO psiDto) {
		EntityManager em = JPAUtil.getEntityManager();
		if (psiDto != null) {
			em.refresh(psiDto);
			TreatmentRegionDTO trDto = psiDto.getTrDto();
			if (trDto != null) {
				em.refresh(trDto);
			}
			List<PatientStSideEffectDTO> pstSeDtos = psiDto.getPstSeDtos();
			if (pstSeDtos != null) {
				for (PatientStSideEffectDTO pstSeDto: pstSeDtos) {
					em.refresh(pstSeDto);
					SideEffectDTO seDto = pstSeDto.getSeDto();
					em.refresh(seDto);
				}
			}
		}
	}
	public static List<PatientRadiationTreatmentDTO> getPatientRadiationTreatments(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientRadiationTreatmentDTO> radiationTreatments = null;
		try {
			TypedQuery<PatientRadiationTreatmentDTO> query = em.createQuery("FROM PatientRadiationTreatmentDTO p WHERE p.userId = :userId ORDER BY p.startDate ASC", PatientRadiationTreatmentDTO.class);
			query.setParameter("userId", patientId);
			radiationTreatments = query.getResultList();
			for (PatientRadiationTreatmentDTO prtDto : radiationTreatments) {
				refreshRadiationTreatment(prtDto);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}		
		return radiationTreatments;
	}
	
	public static PatientRadiationTreatmentDTO getRadiationTreatment(Integer treatmentId) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientRadiationTreatmentDTO prtDto = null;
		try {
			TypedQuery<PatientRadiationTreatmentDTO> query = em.createQuery("FROM PatientRadiationTreatmentDTO c WHERE c.id = :id", PatientRadiationTreatmentDTO.class);
			query.setParameter("id", treatmentId);
			prtDto = query.getSingleResult();
			refreshRadiationTreatment(prtDto);
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return prtDto;
	}

	public static PatientSurgeryInfoDTO getSurgeryInfo(Integer treatmentId) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientSurgeryInfoDTO psiDto = null;
		try {
			TypedQuery<PatientSurgeryInfoDTO> query = em.createQuery("FROM PatientSurgeryInfoDTO c WHERE c.id = :id", PatientSurgeryInfoDTO.class);
			query.setParameter("id", treatmentId);
			psiDto = query.getSingleResult();
			refreshSurgeryInfo(psiDto);
		} 
		catch(Exception e) {
			//e.printStackTrace();
		}
		return psiDto;
	}
	public static PatientChemoTreatmentDTO getChemotherapy(Integer treatmentId) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientChemoTreatmentDTO pctDto = null;
		try {
			TypedQuery<PatientChemoTreatmentDTO> query = em.createQuery("FROM PatientChemoTreatmentDTO p WHERE p.id = :id", PatientChemoTreatmentDTO.class);
			query.setParameter("id", treatmentId);
			pctDto = query.getSingleResult();
			refreshChemotherapy(pctDto);
		} 
		catch(Exception e) {
					
		}
		return pctDto;
	}
	public static List<PatientChemoTreatmentDTO> getPatientChemoTreatments(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientChemoTreatmentDTO> chemoTreatments = null;
		try {
			TypedQuery<PatientChemoTreatmentDTO> query = em.createQuery("FROM PatientChemoTreatmentDTO p WHERE p.userId = :userId ORDER BY p.startDate ASC", PatientChemoTreatmentDTO.class);
			query.setParameter("userId", patientId);
			chemoTreatments = query.getResultList();
			for (PatientChemoTreatmentDTO pctDto : chemoTreatments) {
				refreshChemotherapy(pctDto);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}		
		return chemoTreatments;
	}
	
	public static List<PatientSurgeryInfoDTO> getPatientSurgeryInfo(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientSurgeryInfoDTO> surgeryInfo = null;
		try {
			TypedQuery<PatientSurgeryInfoDTO> query = em.createQuery("FROM PatientSurgeryInfoDTO p WHERE p.userId = :userId ORDER BY p.surgeryDate ASC", PatientSurgeryInfoDTO.class);
			query.setParameter("userId", patientId);
			surgeryInfo = query.getResultList();
			for (PatientSurgeryInfoDTO psiDto : surgeryInfo) {
				refreshSurgeryInfo(psiDto);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return surgeryInfo;
	}

	public static void addDefaultNotes(Integer patientId,Integer diseaseId)
	{
		UserDTO userDTO = UserDAO.getUserBasicByField("id", patientId);
		 int start = 0,end=3;
		 if(diseaseId!=1)
		 {
			 start=3;
			 end=6;
		 }
		for(int i=start;i<end;i++)
		{
			NoteDTO temp = new NoteDTO();
			temp.setNoteFor(userDTO);
			temp.setNoteTitle(TAHConstants.TITLES[i]);
			temp.setNoteDesc(TAHConstants.DESCRIPTIONS[i]);
			temp.setNoteSection("followupcare");
			if(!NotesDAO.isDefaultNoteExist(patientId, TAHConstants.TITLES[i]))		
			{
				System.out.println("NOW : Note does not exist enter note");
				NotesDAO.saveNote(temp);				
			}
		}
	}
	
	public static boolean populatePatientFolloupplan(Integer patientId, Integer templateId) {
		List<DefaultTemplateDetailDTO> defaults = DefaultTemplateDAO.getInputDefaultByPageField(templateId); 
		if(defaults != null && !defaults.isEmpty()) {
			Integer careItemId = null;
			for (DefaultTemplateDetailDTO inputDefaultDTO : defaults) {
				Map<String, String> fupCareItem = new HashMap<String, String>();
				fupCareItem.put("activity",inputDefaultDTO.getFieldtext());
				fupCareItem.put("frequency",inputDefaultDTO.getFrequency());
				fupCareItem.put("purpose",inputDefaultDTO.getOtherfield());
				fupCareItem.put("endDate","");
				if(inputDefaultDTO.getEnddate() != null && inputDefaultDTO.getEnddate().equalsIgnoreCase("ongoing")) {
					fupCareItem.put("ongoing",inputDefaultDTO.getEnddate());
					fupCareItem.put("endDate",null);
				} else {
					fupCareItem.put("ongoing",null);
					fupCareItem.put("endDate",inputDefaultDTO.getEnddate());	
				}
				fupCareItem.put("doctor","");
				FollowUp.saveCareItem(patientId, careItemId, fupCareItem);		
				addDefaultNotes(patientId, templateId);
			}			
		}
		return true;
	}
}

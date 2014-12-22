package nav.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.sound.midi.MidiDevice.Info;

import org.apache.commons.lang.StringUtils;

import models.AddressDTO;
import models.AppointmentDTO;
import models.BreastCancerInfoDTO;
import models.BreastCancerStageDTO;
import models.CancerChromosomeDTO;
import models.CancerFabClassificationDTO;
import models.CancerGradeDTO;
import models.CancerInvasiveDTO;
import models.CancerMutationDTO;
import models.CancerPhaseDTO;
import models.CancerTypeDTO;
import models.CancerWhoClassificationDTO;
import models.DiseaseMasterDTO;
import models.PatientChromosomeDTO;
import models.PatientDetailDTO;
import models.PatientMutationDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import util.CommonUtil;
import util.JPAUtil;

public class PatientDetailDAO {

	public static PatientDetailDTO getDetailsByField(String fieldName, Object value) {
		PatientDetailDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientDetailDTO> query = em.createQuery("SELECT c FROM PatientDetailDTO c WHERE c."+fieldName+" = :field", PatientDetailDTO.class); 
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}

	public static PatientDetailDTO update(PatientDetailDTO dto) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(dto);
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public static PatientDetailDTO save(PatientDetailDTO dto) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(dto);
		em.getTransaction().commit();
		return dto;
	}
	
	public static Map<String, Object> patientSummary(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		Map<String, Object> ps = new HashMap<String, Object>();
		String firstName = null;
		String lastName = null;
		String userId = null;
		boolean isVerified = false;
		String disease = null;
		String stage = null;
		String er = null;
		String pr = null;
		String her2 = null;
		String brca = null;
		Date dob = null;
		String phone = null;
		Date dateOfDiagnosis = null;
		String ec1Name = null;
		String ec1Number = null;
		Date nextAppointment = null;
		Date lastAppointment = null;
		String nextPurpose = null;
		String lastPurpose = null;
		
		TypedQuery<UserDTO> userQuery = em.createQuery("SELECT c FROM UserDTO c WHERE c.id = :id", UserDTO.class); 
		userQuery.setParameter("id", patientId.intValue());
		UserDTO userDto = userQuery.getSingleResult();
		em.refresh(userDto);
		TypedQuery<UserDetailsDTO> userDetailsQuery = em.createQuery("SELECT c FROM UserDetailsDTO c WHERE c.id = :id", UserDetailsDTO.class);
		userDetailsQuery.setParameter("id", patientId.intValue());
		UserDetailsDTO userDetailsDto = userDetailsQuery.getSingleResult();
		
		TypedQuery<PatientDetailDTO> patientDetailsQuery = em.createQuery("SELECT c FROM PatientDetailDTO c WHERE c.id = :id", PatientDetailDTO.class);
		patientDetailsQuery.setParameter("id", patientId.intValue());
		try {
			PatientDetailDTO patientDetailsDto = patientDetailsQuery.getSingleResult();
			em.refresh(patientDetailsDto);
			dateOfDiagnosis = patientDetailsDto.getDateofdiagnosis();
			ec1Name = patientDetailsDto.getEc1name();
			ec1Number = patientDetailsDto.getEc1number();
			DiseaseMasterDTO dmDto = patientDetailsDto.getDisease();
			if (dmDto != null) {
				em.refresh(dmDto);
				disease = dmDto.getName();
					TypedQuery<BreastCancerInfoDTO> bcQuery = em.createQuery("SELECT c FROM BreastCancerInfoDTO c WHERE c.id = :id", BreastCancerInfoDTO.class);
					bcQuery.setParameter("id", patientId);
					BreastCancerInfoDTO bcDto = bcQuery.getSingleResult();
					if (dmDto.getId() == Disease.BREAST_CANCER_ID) {
					if (bcDto.getEr() != null) {
						er = bcDto.getEr().toString();
					}
					if (bcDto.getPr() != null) {
						pr = bcDto.getPr().toString();
					}
					if (bcDto.getHer2() != null) {
						her2 = bcDto.getHer2().toString();
					}
					if (bcDto.getBrca() != null) {
						brca = bcDto.getBrca().toString();
					}
					}
					BreastCancerStageDTO stageDto = bcDto.getBcStage();
					if (stageDto != null) {
						em.refresh(stageDto);
						stage = stageDto.getName();
					}
				
			}
		} catch (NoResultException e) {
			
		}
		
		TypedQuery<AppointmentDTO> nextQuery = em.createQuery("SELECT c FROM AppointmentDTO c WHERE c.patientid.id = :id AND c.deleteflag = false AND c.appointmentdate >= :appointmentdate order by c.appointmentdate asc", AppointmentDTO.class);
		nextQuery.setParameter("id", patientId);
		nextQuery.setParameter("appointmentdate", new Date());
		
		try {
			List<AppointmentDTO> nextDtos = nextQuery.getResultList();
			if (nextDtos != null && nextDtos.size() > 0) {
				AppointmentDTO nextDto = nextDtos.get(0);
				nextAppointment = nextDto.getAppointmentdate();
				//nextPurpose = nextDto.getAppointmentid().getName();
				nextPurpose = nextDto.getPurposeText();
			}
		} catch (NoResultException e) {
			
		}
		
		TypedQuery<AppointmentDTO> lastQuery = em.createQuery("SELECT c FROM AppointmentDTO c WHERE c.patientid.id = :id AND c.deleteflag = false AND c.appointmentdate < :appointmentdate order by c.appointmentdate desc", AppointmentDTO.class);
		lastQuery.setParameter("id", patientId);
		lastQuery.setParameter("appointmentdate", new Date());
		try {
			List<AppointmentDTO> lastDtos = lastQuery.getResultList();
			if (lastDtos != null && lastDtos.size() > 0) {
				AppointmentDTO lastDto = lastDtos.get(0);
				lastAppointment = lastDto.getAppointmentdate();
				lastPurpose = lastDto.getPurposeText();
//				lastPurpose = lastDto.getAppointmentid().getName();
			}
		} catch (NoResultException e) {
			
		}
		
		userId = String.valueOf(userDto.getId());
		firstName = userDetailsDto.getFirstName() != null ? userDetailsDto.getFirstName() : "";
		lastName = userDetailsDto.getLastName() != null ? userDetailsDto.getLastName() : "";		
		dob = userDetailsDto.getDob();
		isVerified = userDto.isIsverified();
		phone = userDetailsDto.getHomePhone();
		
		
		
		ps.put("userId", userId);
		ps.put("firstName", firstName);
		ps.put("lastName", lastName);
		ps.put("isVerified", isVerified);
		ps.put("disease", disease);
		ps.put("stage", stage);
		ps.put("er", er);
		ps.put("pr", pr);
		ps.put("her2", her2);
		ps.put("brca", brca);
		ps.put("dob", dob);
		ps.put("phone", phone);
		ps.put("dateOfDiagnosis", dateOfDiagnosis);
		ps.put("ec1Name", ec1Name);
		ps.put("ec1Number", ec1Number);
		ps.put("nextAppointment", nextAppointment);
		ps.put("nextPurpose", nextPurpose);
		ps.put("lastAppointment", lastAppointment);
		ps.put("lastPurpose", lastPurpose);
		ps.put("email", userDto.getEmail());
		return ps;
	}
	
	public static void patientVerify(Integer patientId, boolean isVerified) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<UserDTO> userQuery = em.createQuery("SELECT c FROM UserDTO c WHERE c.id = :id", UserDTO.class); 
		userQuery.setParameter("id", patientId.intValue());
		UserDTO userDto = userQuery.getSingleResult();
		userDto.setIsverified(isVerified);
		em.getTransaction().begin();
		em.persist(userDto);
		em.getTransaction().commit();
	}
	
	public static Map<String, Object> getDiagnosis(int patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientDetailDTO patientDetails = null;
		UserDetailsDTO userDetails = null;
		BreastCancerInfoDTO breastCancerInfo = null;
		Map<String, Object> patientInfo = new HashMap<String, Object>();
		Integer intId = new Integer(patientId);
		TypedQuery<UserDetailsDTO> query1 = em.createQuery("SELECT c FROM UserDetailsDTO c WHERE c.id = :id", UserDetailsDTO.class); 
		query1.setParameter("id", intId);
		userDetails = query1.getSingleResult();
		
		TypedQuery<PatientDetailDTO> query2 = em.createQuery("SELECT c FROM PatientDetailDTO c WHERE c.id = :id", PatientDetailDTO.class); 
		query2.setParameter("id", patientId);
		try {
			patientDetails = query2.getSingleResult();
		} catch (NoResultException e) {}
		
		TypedQuery<BreastCancerInfoDTO> query3 = em.createQuery("SELECT c FROM BreastCancerInfoDTO c WHERE c.id = :id", BreastCancerInfoDTO.class); 
		query3.setParameter("id", patientId);
		
		try {
			breastCancerInfo = query3.getSingleResult();
			/****          Eliminate Cache Problem (not sure) **************/
			BreastCancerStageDTO bcStage;
			TypedQuery<BreastCancerStageDTO> query4 = em.createQuery("SELECT c FROM BreastCancerStageDTO c WHERE c.id = :id", BreastCancerStageDTO.class);
			query4.setParameter("id", breastCancerInfo.getStageId());
			bcStage = query4.getSingleResult();
			breastCancerInfo.setBcStage(bcStage);
			/****          Eliminate Cache Problem (not sure) **************/
		} catch(NoResultException e) {
			e.printStackTrace();
		}
			
		if(breastCancerInfo != null ) {
			breastCancerInfo.setType(null);
			breastCancerInfo.setSubtype(null);
			try {
				TypedQuery<CancerTypeDTO> query5 = em.createQuery("SELECT c FROM CancerTypeDTO c WHERE c.id = :id", CancerTypeDTO.class);
				query5.setParameter("id", breastCancerInfo.getTypeid());
				CancerTypeDTO type = query5.getSingleResult();
				breastCancerInfo.setType(type);
			} catch(NoResultException e) {
				e.printStackTrace();
			}
			try {
				TypedQuery<CancerTypeDTO> query5 = em.createQuery("SELECT c FROM CancerTypeDTO c WHERE c.id = :id", CancerTypeDTO.class);
				query5.setParameter("id", breastCancerInfo.getSubtypeid());
				CancerTypeDTO type = query5.getSingleResult();
				breastCancerInfo.setSubtype(type);
			} catch(NoResultException e) {
				e.printStackTrace();
			}
		}
		
		/****          Eliminate Cache Problem (not sure) **************/
		if (breastCancerInfo != null && breastCancerInfo.getStageId() == null) {
			breastCancerInfo.setBcStage(null);
		}
		/****          Eliminate Cache Problem (not sure) **************/
		
		patientInfo.put("userDetails", userDetails);
		patientInfo.put("patientDetails", patientDetails);
		patientInfo.put("breastCancerInfo", breastCancerInfo);
		return patientInfo;
	}
	
	public static List<PatientMutationDTO> getMutations(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientMutationDTO> mutations = null;
		try {
			TypedQuery<PatientMutationDTO> query5 = em.createQuery("SELECT c FROM PatientMutationDTO c WHERE c.patientid = :id", PatientMutationDTO.class);
			query5.setParameter("id", patientId);
			mutations = query5.getResultList();
			for (PatientMutationDTO patientMutationDTO : mutations) {
				TypedQuery<CancerMutationDTO> query6 = em.createQuery("SELECT c FROM CancerMutationDTO c WHERE c.id = :id", CancerMutationDTO.class);
				query6.setParameter("id", patientMutationDTO.getMutationid());
				CancerMutationDTO pmDtos = query6.getSingleResult();
				patientMutationDTO.setPmDto(pmDtos);
			}
		} catch(NoResultException e) {
			e.printStackTrace();
		}
		return mutations;
	}
	public static List<PatientChromosomeDTO> getChromosome(Integer patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<PatientChromosomeDTO> mutations = null;
		try {
			TypedQuery<PatientChromosomeDTO> query5 = em.createQuery("SELECT c FROM PatientChromosomeDTO c WHERE c.patientid = :id", PatientChromosomeDTO.class);
			query5.setParameter("id", patientId);
			mutations = query5.getResultList();
			for (PatientChromosomeDTO patientMutationDTO : mutations) {
				TypedQuery<CancerChromosomeDTO> query6 = em.createQuery("SELECT c FROM CancerChromosomeDTO c WHERE c.id = :id", CancerChromosomeDTO.class);
				query6.setParameter("id", patientMutationDTO.getChromosomeid());
				CancerChromosomeDTO pmDtos = query6.getSingleResult();
				patientMutationDTO.setPmDto(pmDtos);
			}
		} catch(NoResultException e) {
			e.printStackTrace();
		}
		return mutations;
	}
	public static Map<String, Object> getInfo(int patientId) {
		UserDTO user = UserDAO.getUserBasicByField("id", patientId);
		UserDetailsDTO userDetails = UserDAO.getDetailsById(patientId);
		PatientDetailDTO patientDetails = getDetailsByField("id", patientId);
		
		// validation and conversion
		String email = user.getEmail() != null ? user.getEmail() : "";
		String firstName = userDetails.getFirstName() != null ? userDetails.getFirstName() : "";
		String lastName = userDetails.getLastName() != null ? userDetails.getLastName() : "";
		String homePhone = userDetails.getHomePhone() != null ? userDetails.getHomePhone() : "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date dob = userDetails.getDob();
		String date = "";
		String ec1name = "", ec1number = "";
		if (dob != null) {
			date = dateFormat.format(dob);
		}
		
		if (patientDetails != null) {
			ec1name = patientDetails.getEc1name() != null ? patientDetails.getEc1name() : "";
			ec1number = patientDetails.getEc1number() != null ? patientDetails.getEc1number() : "";
		}
		
		// read
		Map<String, Object> json = new HashMap<String, Object>();
		
		json.put("firstname", firstName);		
		json.put("lastname", lastName);		
		json.put("email", email);
		json.put("dob", date);		
		json.put("homephone", homePhone);
		json.put("ec1name", ec1name);
		json.put("ec1number", ec1number);
		return json;
	}
	public static void saveInfo(int patientId, Map<String, String> info) {
		UserDTO user = null;
		UserDetailsDTO userDetails = null;
		PatientDetailDTO patientDetails = null;		
		
		// validation and conversion
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date dob = null;
		String firstName = info.get("firstName");
		String lastName = info.get("lastName");
		String email = info.get("email");
		String homephone = info.get("homephone");
		String ec1name = info.get("ec1name");
		String ec1number = info.get("ec1number");		
		try {
			dob = dateFormat.parse(info.get("dob"));
		} catch (ParseException e1) {			
		}
		
		// save
		EntityManager em = JPAUtil.getEntityManager();	
		
		TypedQuery<UserDTO> userQuery = em.createQuery("SELECT u FROM UserDTO u WHERE u.id = :id", UserDTO.class); 
		userQuery.setParameter("id", patientId);		
		try {
			user = userQuery.getSingleResult();
			user.setEmail(email);
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (NoResultException e) {
			
		}
		
		TypedQuery<UserDetailsDTO> userDetailsQuery = em.createQuery("SELECT u FROM UserDetailsDTO u WHERE u.id = :id", UserDetailsDTO.class); 
		userDetailsQuery.setParameter("id", patientId);		
		try {
			userDetails = userDetailsQuery.getSingleResult();
			userDetails.setFirstName(firstName);
			userDetails.setLastName(lastName);
			userDetails.setDob(dob);
			userDetails.setHomePhone(homephone);
			em.getTransaction().begin();
			em.persist(userDetails);
			em.getTransaction().commit();
		} catch (NoResultException e) {
			
		}
		
		TypedQuery<PatientDetailDTO> patientDetailsQuery = em.createQuery("SELECT p FROM PatientDetailDTO p WHERE p.id = :id", PatientDetailDTO.class); 
		patientDetailsQuery.setParameter("id", patientId);		
		try {
			patientDetails = patientDetailsQuery.getSingleResult();			
		} catch (NoResultException e) {
			patientDetails = new PatientDetailDTO();
			patientDetails.setId(patientId);
		}
		patientDetails.setEc1name(ec1name);
		patientDetails.setEc1number(ec1number);
		em.getTransaction().begin();
		em.persist(patientDetails);
		em.getTransaction().commit();						
	}
	
	public static Map<String, Object> getDiagnosisJSON(int patientId) {
		Map<String, Object> patientInfo = getDiagnosis(patientId);
		Map<String, Object> jsonData = new HashMap<String, Object>();
		UserDetailsDTO userDetails = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		List<DiseaseMasterDTO> diseases = Disease.allDiseases();
		List<BreastCancerStageDTO> bcStages = Disease.breastCancerStages();
		
		List<CancerMutationDTO> mutations =  Disease.cancerMutations();
		List<CancerTypeDTO> rootTypeList = Disease.getCancerTypes(true);
		List<CancerTypeDTO> subTypeList = Disease.getCancerTypes(false);
		List<CancerInvasiveDTO> invasionList = Disease.getCancerInvasive();
		List<CancerGradeDTO> gradeList = Disease.getCancerGrade();
		List<CancerChromosomeDTO> chromosomeList = Disease.getCancerChromosome();
		List<CancerPhaseDTO> phaseList = Disease.getCancerPhases();
		List<CancerFabClassificationDTO> fabClassList = Disease.getCancerFABClasses();
		List<CancerWhoClassificationDTO> whoClassList = Disease.getCancerWHOClasses();
				
		jsonData.put("diseases", diseases);
		jsonData.put("bcStages", bcStages);
		jsonData.put("mutations", mutations);
		jsonData.put("roottype", rootTypeList);
		jsonData.put("subtype", subTypeList);
		jsonData.put("invasion", invasionList);
		jsonData.put("grade", gradeList);
		jsonData.put("chromosome", chromosomeList);
		jsonData.put("phase", phaseList);
		jsonData.put("fab", fabClassList);
		jsonData.put("who", whoClassList);
		if (patientDetails != null) {
			Integer diseaseId = patientDetails.getDiseaseId();
			if (diseaseId != null) {
				jsonData.put("diseaseId", diseaseId.toString());
			}
			Date date = patientDetails.getDateofdiagnosis();
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				jsonData.put("firstDiagnosed", dateFormat.format(date));
			}
			jsonData.put("supportName", patientDetails.getEc1name());
			jsonData.put("supportNumber", patientDetails.getEc1number());
			jsonData.put("familyRisk", patientDetails.getFamilyRisk());
		}
		if (userDetails != null) {
			Date date = userDetails.getDob();
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				jsonData.put("dateOfBirth", dateFormat.format(date));
			}
			jsonData.put("Phone", userDetails.getHomePhone());
		}
		if (breastCancerInfo != null) {
			String er = CommonUtil.getHormoneText(breastCancerInfo.getEr());
			String pr = CommonUtil.getHormoneText(breastCancerInfo.getPr());
			String her2 = CommonUtil.getHormoneText(breastCancerInfo.getHer2());
			String brca = CommonUtil.getHormoneText(breastCancerInfo.getBrca());
			jsonData.put("er", er);
			jsonData.put("pr", pr);
			jsonData.put("her2", her2);
			jsonData.put("brca", brca);
			Integer stageId = breastCancerInfo.getStageId();
			if (stageId != null) {
				jsonData.put("stageId", stageId.toString());
			}
			CancerPhaseDTO phase = breastCancerInfo.getPhase();
			if (phase != null) {
				jsonData.put("phaseId", phase.getId());
			}
			if(breastCancerInfo.getRisklevel() != null) {
				jsonData.put("risklevel", breastCancerInfo.getRisklevel());
			}
			if(breastCancerInfo.getPsascore() != null) {
				jsonData.put("psascore", breastCancerInfo.getPsascore());
			}
			if(breastCancerInfo.getGleasonscore() != null) {
				jsonData.put("gleasonscore", breastCancerInfo.getGleasonscore());
			}
			
			if(breastCancerInfo.getTypeid() != null) {
				jsonData.put("csrtype", breastCancerInfo.getTypeid().intValue());
			}
			if(breastCancerInfo.getSubtypeid() != null) {
				jsonData.put("csrsubtype", breastCancerInfo.getSubtypeid().intValue());
			}
			
			if(breastCancerInfo.getGrade() != null) {
				jsonData.put("csrgrade", breastCancerInfo.getGrade().getId());
			}
			if(breastCancerInfo.getInvasion() != null) {
				jsonData.put("csrinvasion", breastCancerInfo.getInvasion().getId());
			}
			CancerFabClassificationDTO fab = breastCancerInfo.getFabclass();
			if (fab != null) {
				jsonData.put("fabId", fab.getId());
			}
			CancerWhoClassificationDTO who = breastCancerInfo.getWhoclass();
			if(who != null) {
				jsonData.put("whoId", who.getId());
			}
		}
		List<PatientChromosomeDTO> chromosomes =  PatientDetailDAO.getChromosome(new Integer(patientId));
		ArrayList<String> chrom = new ArrayList<String>();
		if(chromosomes != null && !chromosomes.isEmpty()) {
			for (PatientChromosomeDTO patientMutationDTO : chromosomes) {
				chrom.add(patientMutationDTO.getChromosomeid().intValue() + "");
			}
		}
		jsonData.put("chromosomesIds", chrom.toArray());
		List<PatientMutationDTO> genetics = PatientDetailDAO.getMutations(new Integer(patientId));
		ArrayList<String> gen = new ArrayList<String>();
		if(genetics != null && !genetics.isEmpty()) {
			for (PatientMutationDTO patientMutationDTO : genetics) {
				gen.add(patientMutationDTO.getMutationid().intValue() + "");	
			}
		}
		jsonData.put("genetics", gen.toArray());
		jsonData.put("breastCancerId", new Integer(Disease.BREAST_CANCER_ID).toString());
		
		return jsonData;
	}
	
	public static void updateDiagnosis(int patientId, Integer diseaseId, String dateOfDiagnosis, Map<String, String> diseaseInfo) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientDetailDTO patientDetails;
		UserDetailsDTO userDetails;
		BreastCancerInfoDTO breastCancerInfo;
		boolean bcEntryExist;
		// update 'patientdetails' table
		try {
			TypedQuery<PatientMutationDTO> query5 = em.createQuery("SELECT c FROM PatientMutationDTO c WHERE c.patientid = :id", PatientMutationDTO.class);
			query5.setParameter("id", new Integer(patientId));
			List<PatientMutationDTO> mutations = query5.getResultList();
			for (PatientMutationDTO patientMutationDTO : mutations) {
				BaseDAO.remove(patientMutationDTO);
			}
		} catch(NoResultException e) {
			e.printStackTrace();
		}
		
		try {
			TypedQuery<PatientChromosomeDTO> query5 = em.createQuery("SELECT c FROM PatientChromosomeDTO c WHERE c.patientid = :id", PatientChromosomeDTO.class);
			query5.setParameter("id", new Integer(patientId));
			List<PatientChromosomeDTO> mutations = query5.getResultList();
			for (PatientChromosomeDTO patientMutationDTO : mutations) {
				BaseDAO.remove(patientMutationDTO);
			}
		} catch(NoResultException e) {
			e.printStackTrace();
		}
		TypedQuery<PatientDetailDTO> query1 = em.createQuery("SELECT c FROM PatientDetailDTO c WHERE c.id = :id", PatientDetailDTO.class); 
		query1.setParameter("id", patientId);
		try {
			patientDetails = query1.getSingleResult();			
		} catch (NoResultException e) {
			patientDetails = new PatientDetailDTO();
			patientDetails.setId(patientId);			
		}		
		
		if (diseaseId != null && diseaseId.intValue() > 0) {
			patientDetails.setDiseaseId(diseaseId);
		}
		else {
			patientDetails.setDiseaseId(null);
		}
		if (dateOfDiagnosis != null) {
			if (!dateOfDiagnosis.isEmpty()) {					
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				Date date;
				try {
					date = df.parse(dateOfDiagnosis);
					patientDetails.setDateofdiagnosis(date);
				} catch (ParseException e1) {
					//e.printStackTrace();
				}
			}
			else {
				patientDetails.setDateofdiagnosis(null);
			}
		}
		String familyRisk = diseaseInfo.get("familyRisk");
		if (familyRisk != null) {
			patientDetails.setFamilyRisk(familyRisk);
		}		
		em.getTransaction().begin();
		em.persist(patientDetails);
		em.getTransaction().commit();
		
		
		
		
		// update 'pbcsvinfo' info
		
		TypedQuery<BreastCancerInfoDTO> query3 = em.createQuery("SELECT c FROM BreastCancerInfoDTO c WHERE c.id = :id", BreastCancerInfoDTO.class); 
		query3.setParameter("id", patientId);
		
		try {
			breastCancerInfo = query3.getSingleResult();
			bcEntryExist = true;
		} catch(NoResultException e) {
			breastCancerInfo = new BreastCancerInfoDTO();
			breastCancerInfo.setId(patientId);
			bcEntryExist = false;
		}
		
		String str = diseaseInfo.get("stage_id");
		Integer stageId;
		if (StringUtils.isBlank(str)) {
			stageId = null;
		} else {
			stageId = new Integer(str);
		}
		breastCancerInfo.setStageId(stageId);
		
		str = diseaseInfo.get("fab_id");
		if (StringUtils.isBlank(str)) {
			stageId = null;
		} else {
			stageId = new Integer(str);
		}
		breastCancerInfo.setFabclass(Disease.getCancerFABClasseById(stageId));
		
		str = diseaseInfo.get("who_id");
		if (StringUtils.isBlank(str)) {
			stageId = null;
		} else {
			stageId = new Integer(str);
		}
		breastCancerInfo.setWhoclass(Disease.getCancerWHOClasseById(stageId));
		str = diseaseInfo.get("grade");
		Integer grade;
		if (StringUtils.isBlank(str)) {
			grade = null;
		} else {
			grade = new Integer(str);
		}
		breastCancerInfo.setGrade( Disease.getCancerGradeById(grade));
		
		str = diseaseInfo.get("phase");
		Integer phase;
		if (StringUtils.isBlank(str)) {
			phase = null;
		} else {
			phase = new Integer(str);
		}
		breastCancerInfo.setPhase(Disease.getCancerPhaseById(phase));
		str = diseaseInfo.get("invasiveness");
		Integer invasiveness;
		if (StringUtils.isBlank(str)) {
			invasiveness = null;
		} else {
			invasiveness = new Integer(str);
		}
		breastCancerInfo.setInvasion(Disease.getCancerInvasiveById(invasiveness));
		
		breastCancerInfo.setTypeid(null);
		breastCancerInfo.setSubtypeid(null);

		//Cancer specifics start
		if (diseaseId != null && diseaseId == Disease.BREAST_CANCER_ID) {
			Character er = CommonUtil.getHormoneStatus(diseaseInfo.get("er"));
			Character pr = CommonUtil.getHormoneStatus(diseaseInfo.get("pr"));
			Character her2 = CommonUtil.getHormoneStatus(diseaseInfo.get("her2"));

			breastCancerInfo.setEr(er);
			breastCancerInfo.setPr(pr);
			breastCancerInfo.setHer2(her2);
		} else {
			breastCancerInfo.setEr(null);
			breastCancerInfo.setPr(null);
			breastCancerInfo.setHer2(null);
		}
		
		if (diseaseId != null && (diseaseId == Disease.OVARIAN_CANCER_ID || diseaseId == Disease.BREAST_CANCER_ID)) {
			Character brca = CommonUtil.getHormoneStatus(diseaseInfo.get("brca"));
			breastCancerInfo.setBrca(brca);
		} else {
			breastCancerInfo.setBrca(null);
		}

		if (diseaseId != null && diseaseId == Disease.PROSTATE_CANCER_ID) {
			breastCancerInfo.setRisklevel(diseaseInfo.get("risklevel"));
			breastCancerInfo.setPsascore(diseaseInfo.get("psascore"));
			breastCancerInfo.setGleasonscore(diseaseInfo.get("gleasonscore"));	
		} else {
			breastCancerInfo.setRisklevel(null);
			breastCancerInfo.setPsascore(null);
			breastCancerInfo.setGleasonscore(null);
		}
		//Cancer specifics end

		str = diseaseInfo.get("typeid");
		if (StringUtils.isBlank(str)) {
			breastCancerInfo.setTypeid(null);
		} else {
			try {
				breastCancerInfo.setTypeid( new Integer(str));
			} catch (Exception e) {
				Integer typeId = CancerDAO.createCancerType(diseaseId, str, true);
				breastCancerInfo.setTypeid(typeId);
			}
		}
		str = diseaseInfo.get("subtypeid");
		if (StringUtils.isBlank(str)) {
			breastCancerInfo.setSubtypeid(null);
		} else {
			breastCancerInfo.setSubtypeid( new Integer(str));
		}
		String mustr = diseaseInfo.get("mutation_id");
		if (StringUtils.isNotBlank(mustr)) {
			String muIds [] = mustr.split(",");
			Integer muId;
			for (String string : muIds) {
				string = string.trim();
				if (StringUtils.isNotBlank(string)) {
					muId = new Integer(string);
					PatientMutationDTO pmDto = new PatientMutationDTO();
					pmDto.setMutationid(muId);
					pmDto.setPatientid(new Integer(patientId));
					BaseDAO.save(pmDto);
				}
			}
		}
		mustr = diseaseInfo.get("chromosome_id");
		if (StringUtils.isNotBlank(mustr)) {
			String muIds [] = mustr.split(",");
			Integer muId;
			for (String string : muIds) {
				string = string.trim();
				if (StringUtils.isNotBlank(string)) {
					muId = new Integer(string);
					PatientChromosomeDTO pmDto = new PatientChromosomeDTO();
					pmDto.setChromosomeid(muId);
					pmDto.setPatientid(new Integer(patientId));
					BaseDAO.save(pmDto);
				}
			}
		}
		if(diseaseId != null) {
			em.getTransaction().begin();
			em.persist(breastCancerInfo);
			em.getTransaction().commit();
		} else {
			if (bcEntryExist) {
				em.getTransaction().begin();
				em.remove(breastCancerInfo);
				em.getTransaction().commit();
			}
		}
		
		// update 'userdetails' table
		TypedQuery<UserDetailsDTO> query2 = em.createQuery("SELECT c FROM UserDetailsDTO c WHERE c.id = :id", UserDetailsDTO.class); 
		query2.setParameter("id", patientId);
		userDetails = query2.getSingleResult();
		
		em.getTransaction().begin();
		em.persist(userDetails);
		em.getTransaction().commit();
	}
}
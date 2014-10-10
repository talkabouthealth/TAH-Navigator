package nav.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.sound.midi.MidiDevice.Info;

import models.AddressDTO;
import models.AppointmentDTO;
import models.BreastCancerInfoDTO;
import models.BreastCancerStageDTO;
import models.DiseaseMasterDTO;
import models.PatientDetailDTO;
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
				if (dmDto.getId() == Disease.BREAST_CANCER_ID) {
					TypedQuery<BreastCancerInfoDTO> bcQuery = em.createQuery("SELECT c FROM BreastCancerInfoDTO c WHERE c.id = :id", BreastCancerInfoDTO.class);
					bcQuery.setParameter("id", patientId);
					BreastCancerInfoDTO bcDto = bcQuery.getSingleResult();
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
					
					BreastCancerStageDTO stageDto = bcDto.getBcStage();
					if (stageDto != null) {
						em.refresh(stageDto);
						stage = stageDto.getName();
					}
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
		firstName = userDetailsDto.getFirstName();
		lastName = userDetailsDto.getLastName();
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
			
		} catch(NoResultException e) {}
		
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
	
	public static Map<String, Object> getDiagnosisJSON(int patientId) {
		Map<String, Object> patientInfo = getDiagnosis(patientId);
		Map<String, Object> jsonData = new HashMap<String, Object>();
		UserDetailsDTO userDetails = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		List<DiseaseMasterDTO> diseases = Disease.allDiseases();
		List<BreastCancerStageDTO> bcStages = Disease.breastCancerStages();
		
		jsonData.put("diseases", diseases);
		jsonData.put("bcStages", bcStages);
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
		}
		jsonData.put("breastCancerId", new Integer(Disease.BREAST_CANCER_ID).toString());
		
		return jsonData;
	}
	
	public static void updateDiagnosis(int patientId, Integer diseaseId, String dateOfDiagnosis, String dob, String phone, String supportName, String supportNumber, Map<String, String> diseaseInfo) {
		EntityManager em = JPAUtil.getEntityManager();
		PatientDetailDTO patientDetails;
		UserDetailsDTO userDetails;
		BreastCancerInfoDTO breastCancerInfo;
		boolean bcEntryExist;
		// update 'patientdetails' table
		
		TypedQuery<PatientDetailDTO> query1 = em.createQuery("SELECT c FROM PatientDetailDTO c WHERE c.id = :id", PatientDetailDTO.class); 
		query1.setParameter("id", patientId);
		try {
			patientDetails = query1.getSingleResult();
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
					} catch (ParseException e) {
						//e.printStackTrace();
					}
				}
				else {
					patientDetails.setDateofdiagnosis(null);
				}
			}			
			
			
			patientDetails.setEc1name(supportName);
			patientDetails.setEc1number(supportNumber);
			em.getTransaction().begin();
			em.persist(patientDetails);
			em.getTransaction().commit();
		} catch (NoResultException e) {
			patientDetails = new PatientDetailDTO();
			patientDetails.setId(patientId);
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
			patientDetails.setEc1name(supportName);
			patientDetails.setEc1number(supportNumber);
			em.getTransaction().begin();
			em.persist(patientDetails);
			em.getTransaction().commit();
		}
		
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
		
		if (diseaseId != null && diseaseId == Disease.BREAST_CANCER_ID) {
			String str = diseaseInfo.get("stage_id");
			Integer stageId;
			if (str.isEmpty()) {
				stageId = null;
			}
			else {
				stageId = new Integer(str);
				
			}
			Character er = CommonUtil.getHormoneStatus(diseaseInfo.get("er"));
			Character pr = CommonUtil.getHormoneStatus(diseaseInfo.get("pr"));
			Character her2 = CommonUtil.getHormoneStatus(diseaseInfo.get("her2"));
			Character brca = CommonUtil.getHormoneStatus(diseaseInfo.get("brca"));
			
			breastCancerInfo.setStageId(stageId);
			breastCancerInfo.setEr(er);
			breastCancerInfo.setPr(pr);
			breastCancerInfo.setHer2(her2);
			breastCancerInfo.setBrca(brca);
			em.getTransaction().begin();
			em.persist(breastCancerInfo);
			em.getTransaction().commit();
		}
		else {
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
		
		//userDetails.setDob(dob);
		
		if (dob != null) {
			if (!dob.isEmpty()) {					
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				Date date;
				try {
					date = df.parse(dob);
					userDetails.setDob(date);
				} catch (ParseException e1) {
					//e.printStackTrace();
				}
			}
			else {
				userDetails.setDob(null);
			}
		}
		
		userDetails.setHomePhone(phone);
		em.getTransaction().begin();
		em.persist(userDetails);
		em.getTransaction().commit();
	}
}
package nav.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.BreastCancerInfoDTO;
import models.BreastCancerStageDTO;
import models.CancerInvasiveDTO;
import models.CancerTypeDTO;
import models.DiseaseMasterDTO;
import models.PatientDetailDTO;
import util.JPAUtil;

public class CancerDAO {
	public static final int BREAST_CANCER_ID = 1;
	
	public static Map<String, String> cancerInfo(int patientId) {
		Map<String, String> info = new HashMap<String, String>();
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<PatientDetailDTO> patientDetailsQuery = em.createQuery("SELECT p FROM PatientDetailDTO p WHERE p.id = :id", PatientDetailDTO.class);
		patientDetailsQuery.setParameter("id", patientId);
		try {
			PatientDetailDTO patientDetails = patientDetailsQuery.getSingleResult();
			Integer diseaseId = patientDetails.getDiseaseId();
			if (diseaseId != null && diseaseId.intValue() == BREAST_CANCER_ID) {
				switch (diseaseId.intValue()) {
					case BREAST_CANCER_ID:
						info = breastCancerInfo(patientDetails);
						break;
				}			
			}
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return info;
	}
		
	
	public static Map<String, String> breastCancerInfo(PatientDetailDTO patientDetails) {
		EntityManager em = JPAUtil.getEntityManager();
		Map<String, String> info = new HashMap<String, String>();										
		DiseaseMasterDTO diseaseMaster = patientDetails.getDisease();		
		info.put("diseaseId", String.valueOf(BREAST_CANCER_ID));
		info.put("diseaseName", diseaseMaster.getName());
		TypedQuery<BreastCancerInfoDTO> query = em.createQuery("SELECT b FROM BreastCancerInfoDTO b WHERE b.id = :id", BreastCancerInfoDTO.class);
		query.setParameter("id", patientDetails.getId());
		try {
			BreastCancerInfoDTO breastCancerInfo = query.getSingleResult();
			info.put("stageId", String.valueOf(breastCancerInfo.getBcStage().getId()));
			info.put("stageName", breastCancerInfo.getBcStage().getName());
			Character er = breastCancerInfo.getEr();
			Character pr = breastCancerInfo.getPr();
			Character her2 = breastCancerInfo.getHer2();
			Character brca = breastCancerInfo.getBrca();
			if (er != null && (String.valueOf(er).equalsIgnoreCase("+") || String.valueOf(er).equalsIgnoreCase("-"))) {
				info.put("er", String.valueOf(er));
			}
			if (pr != null && (String.valueOf(pr).equalsIgnoreCase("+") || String.valueOf(pr).equalsIgnoreCase("-"))) {
				info.put("pr", String.valueOf(pr));
			}
			if (her2 != null && (String.valueOf(her2).equalsIgnoreCase("+") || String.valueOf(her2).equalsIgnoreCase("-"))) {
				info.put("her2", String.valueOf(her2));
			}
			if (brca != null && (String.valueOf(brca).equalsIgnoreCase("+") || String.valueOf(brca).equalsIgnoreCase("-"))) {
				info.put("brca", String.valueOf(brca));
			}
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return info;
	}
	
	public static Integer createCancerType(Integer diseaseId, String name, boolean rootType) {
		EntityManager em = JPAUtil.getEntityManager();
		CancerTypeDTO cancerType = new CancerTypeDTO();
		cancerType.setDiseaseid(diseaseId);
		cancerType.setRoottype(rootType);
		cancerType.setName(name);
		em.getTransaction().begin();
		em.persist(cancerType);
		em.getTransaction().commit();
		return cancerType.getId();
	}
	
	public static Integer createCancerStage(Integer diseaseId, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		BreastCancerStageDTO stage = new BreastCancerStageDTO();
		stage.setDiseaseid(diseaseId);
		stage.setName(name);
		em.getTransaction().begin();
		em.persist(stage);
		em.getTransaction().commit();
		return stage.getId();
	}
	public static CancerInvasiveDTO createCancerInvasion(Integer diseaseId, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		CancerInvasiveDTO invasion = new CancerInvasiveDTO();
		invasion.setDiseaseid(diseaseId);
		invasion.setInvname(name);
		em.getTransaction().begin();
		em.persist(invasion);
		em.getTransaction().commit();
		return invasion;
	}
}

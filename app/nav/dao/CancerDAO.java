package nav.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
	public static Integer getCancerTypeId(Integer diseaseId, String name) {
		Integer id = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<CancerTypeDTO> query = em.createQuery("SELECT c FROM CancerTypeDTO c WHERE c.diseaseid = :diseaseid AND c.name = :name", CancerTypeDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("name", name);
		try {
			CancerTypeDTO dto = query.getSingleResult();
			id = dto.getId();			
		} catch (NoResultException e) {
			
		}
		return id;
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
	
	public static Integer getCancerStageId(Integer diseaseId, String name) {
		Integer id = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<BreastCancerStageDTO> query = em.createQuery("SELECT b FROM BreastCancerStageDTO b WHERE b.diseaseid = :diseaseid AND b.name = :name", BreastCancerStageDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("name", name);
		try {
			BreastCancerStageDTO dto = query.getSingleResult();
			id = dto.getId();
		} catch (NoResultException e) {
			
		}
		return id;
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
	public static CancerInvasiveDTO getCancerInvasion(Integer diseaseId, String name) {
		CancerInvasiveDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<CancerInvasiveDTO> query = em.createQuery("SELECT c FROM CancerInvasiveDTO c WHERE c.diseaseid = :diseaseid AND c.invname = :invname", CancerInvasiveDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("invname", name);
		try {
			dto = query.getSingleResult();					
		} catch (NoResultException e) {
			
		}
		return dto;
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
	
	public static CancerGradeDTO getCancerGrade(Integer diseaseId, String name) {
		CancerGradeDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<CancerGradeDTO> query = em.createQuery("SELECT c FROM CancerGradeDTO c WHERE c.diseaseid = :diseaseid AND c.gradename = :gradename", CancerGradeDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("gradename", name);
		try {
			dto = query.getSingleResult();					
		} catch (NoResultException e) {
			
		}
		return dto;
	}
	
	public static CancerGradeDTO createCancerGrade(Integer diseaseId, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		CancerGradeDTO grade = new CancerGradeDTO();
		grade.setDiseaseid(diseaseId);
		grade.setGradename(name);
		em.getTransaction().begin();
		em.persist(grade);
		em.getTransaction().commit();
		return grade;
	}
	public static CancerPhaseDTO getCancerPhase(Integer diseaseId, String name) {
		CancerPhaseDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<CancerPhaseDTO> query = em.createQuery("SELECT c FROM CancerPhaseDTO c WHERE c.diseaseid = :diseaseid AND c.name = :name", CancerPhaseDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("name", name);
		try {
			dto = query.getSingleResult();					
		} catch (NoResultException e) {
			
		}
		return dto;
	}
	public static CancerPhaseDTO createCancerPhase(Integer diseaseId, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		CancerPhaseDTO phase = new CancerPhaseDTO();
		phase.setDiseaseid(diseaseId);
		phase.setName(name);
		em.getTransaction().begin();
		em.persist(phase);
		em.getTransaction().commit();
		return phase;
	}
	public static CancerFabClassificationDTO getCancerFabClass(Integer diseaseId, String name) {
		CancerFabClassificationDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<CancerFabClassificationDTO> query = em.createQuery("SELECT c FROM CancerFabClassificationDTO c WHERE c.diseaseid = :diseaseid AND c.fabname = :fabname", CancerFabClassificationDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("fabname", name);
		try {
			dto = query.getSingleResult();					
		} catch (NoResultException e) {
			
		}
		return dto;
	}
	public static CancerFabClassificationDTO createCancerFabClass(Integer diseaseId, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		CancerFabClassificationDTO fab = new CancerFabClassificationDTO();
		fab.setDiseaseid(diseaseId);
		fab.setFabname(name);
		em.getTransaction().begin();
		em.persist(fab);
		em.getTransaction().commit();
		return fab;
	}
	
	public static CancerWhoClassificationDTO getCancerWHOClass(Integer diseaseId, String name) {
		CancerWhoClassificationDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<CancerWhoClassificationDTO> query = em.createQuery("SELECT c FROM CancerWhoClassificationDTO c WHERE c.diseaseid = :diseaseid AND c.whoname = :whoname", CancerWhoClassificationDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("whoname", name);
		try {
			dto = query.getSingleResult();					
		} catch (NoResultException e) {
			
		}
		return dto;
	}
	
	public static CancerWhoClassificationDTO createCancerWHOClass(Integer diseaseId, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		CancerWhoClassificationDTO who = new CancerWhoClassificationDTO();
		who.setDiseaseid(diseaseId);
		who.setWhoname(name);
		em.getTransaction().begin();
		em.persist(who);
		em.getTransaction().commit();
		return who;
	}
	
	public static CancerChromosomeDTO getCancerChromosome(Integer diseaseId, String name) {
		CancerChromosomeDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<CancerChromosomeDTO> query = em.createQuery("SELECT c FROM CancerChromosomeDTO c WHERE c.diseaseid = :diseaseid AND c.chromosomename = :chromosomename", CancerChromosomeDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("chromosomename", name);
		try {
			dto = query.getSingleResult();					
		} catch (NoResultException e) {
			
		}
		return dto;
	}
	
	public static CancerChromosomeDTO createCancerChromosome(Integer diseaseId, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		CancerChromosomeDTO chromosome = new CancerChromosomeDTO();
		chromosome.setDiseaseid(diseaseId);
		chromosome.setChromosomename(name);
		em.getTransaction().begin();
		em.persist(chromosome);
		em.getTransaction().commit();
		return chromosome;
	}
	
	public static CancerMutationDTO getCancerMutation(Integer diseaseId, String name) {
		CancerMutationDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<CancerMutationDTO> query = em.createQuery("SELECT c FROM CancerMutationDTO c WHERE c.diseaseid = :diseaseid AND c.mutation = :mutation", CancerMutationDTO.class);
		query.setParameter("diseaseid", diseaseId);
		query.setParameter("mutation", name);
		try {
			dto = query.getSingleResult();					
		} catch (NoResultException e) {
			
		}
		return dto;
	}
	
	public static CancerMutationDTO createCancerMutation(Integer diseaseId, String name) {
		EntityManager em = JPAUtil.getEntityManager();
		CancerMutationDTO mutation = new CancerMutationDTO();
		mutation.setDiseaseid(diseaseId);
		mutation.setMutation(name);
		em.getTransaction().begin();
		em.persist(mutation);
		em.getTransaction().commit();
		return mutation;
	}
}

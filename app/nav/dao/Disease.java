package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
import util.JPAUtil;

public class Disease {

	public final static int BREAST_CANCER_ID = 1; 
	public final static int PROSTATE_CANCER_ID = 2;
	public final static int LUNG_CANCER_ID = 3;
	public final static int COLON_CANCER_ID = 4;
	public final static int RECTAL_CANCER_ID = 5;
	public final static int ESOPHAGEAL_CANCER_ID = 6;
	public final static int ENDOMETRIAL_CANCER_ID = 7;
	
	public final static int BLADDER_CANCER_ID = 8;
	public final static int NON_HODGKIN_LYMPHOMA_ID = 9;
	public final static int MELANOMA_ID = 10;
	public final static int AML_ID = 11;
	public final static int ALL_ID = 12;
	public final static int CLL_ID = 13;
	public final static int CML_ID = 14;
	public final static int CERVICAL_CANCER_ID = 15;
	public final static int STOMACH_CANCER_ID = 16;
	public final static int LIVER_CANCER_ID = 17;
	public final static int PANCREATIC_CANCER_ID = 18;
	public final static int LARYNGEAL_CANCER_ID = 19;
	public final static int PHARYNGEAL_CANCER_ID = 20;
	public final static int MULTIPLE_MYELOMA_ID = 21;
	public final static int OVARIAN_CANCER_ID = 22;
	public final static int KIDNEY_RENAL_CANCER_ID = 23;
	public final static int BRAIN_CANCER_ID = 24;
	public final static int THYROID_CANCER_ID = 25;
	public final static int HODGKIN_LYMPHOMA_CANCER_ID = 26;

	public static List<DiseaseMasterDTO> allDiseases() {
		List<DiseaseMasterDTO> diseases = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<DiseaseMasterDTO> query = em.createQuery("FROM DiseaseMasterDTO where diseaseactive = true order by name", DiseaseMasterDTO.class); 
			diseases = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return diseases;
	}

	public static List<BreastCancerStageDTO> breastCancerStages() {
		List<BreastCancerStageDTO> stages = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<BreastCancerStageDTO> query = em.createQuery("SELECT c FROM BreastCancerStageDTO c order by c.name", BreastCancerStageDTO.class); 
			stages = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return stages;
	}

	public static List<CancerMutationDTO> cancerMutations() {
		List<CancerMutationDTO> mutations = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerMutationDTO> query = em.createQuery("SELECT c FROM CancerMutationDTO c order by c.mutation", CancerMutationDTO.class); 
			mutations = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return mutations;
	}

	public static List<BreastCancerStageDTO> getCancerStages(Integer cancerId) {
		List<BreastCancerStageDTO> stages = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<BreastCancerStageDTO> query = em.createQuery("SELECT c FROM BreastCancerStageDTO c where c.diseaseid = :field  order by c.name", BreastCancerStageDTO.class);
			query.setParameter("field", cancerId);
			stages = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		return stages;
	}

	public static List<CancerTypeDTO> getCancerTypes(boolean isRoot) {
		List<CancerTypeDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerTypeDTO> query = em.createQuery("SELECT c FROM CancerTypeDTO c where c.roottype = :f2 and c.userDefined = false  order by c.name", CancerTypeDTO.class);
			query.setParameter("f2", isRoot);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public static List<CancerInvasiveDTO> getCancerInvasive() {
		List<CancerInvasiveDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerInvasiveDTO> query = em.createQuery("SELECT c FROM CancerInvasiveDTO c order by c.invname", CancerInvasiveDTO.class);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public static List<CancerGradeDTO> getCancerGrade() {
		List<CancerGradeDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerGradeDTO> query = em.createQuery("SELECT c FROM CancerGradeDTO c order by c.gradename", CancerGradeDTO.class);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}
	
	public static CancerGradeDTO getCancerGradeById(Integer greadId) {
		CancerGradeDTO types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerGradeDTO> query = em.createQuery("SELECT c FROM CancerGradeDTO c  where id = :f1", CancerGradeDTO.class);
			query.setParameter("f1", greadId);
			types = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public static CancerInvasiveDTO getCancerInvasiveById(Integer greadId) {
		CancerInvasiveDTO types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerInvasiveDTO> query = em.createQuery("SELECT c FROM CancerInvasiveDTO c where id = :f1", CancerInvasiveDTO.class);
			query.setParameter("f1", greadId);
			types = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public static List<CancerChromosomeDTO> getCancerChromosome() {
		List<CancerChromosomeDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerChromosomeDTO> query = em.createQuery("SELECT c FROM CancerChromosomeDTO c order by c.chromosomename", CancerChromosomeDTO.class);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public static CancerChromosomeDTO getCancerChromosomeById(Integer greadId) {
		CancerChromosomeDTO types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerChromosomeDTO> query = em.createQuery("SELECT c FROM CancerChromosomeDTO c  where id = :f1", CancerChromosomeDTO.class);
			query.setParameter("f1", greadId);
			types = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}
	
	public static List<CancerPhaseDTO> getCancerPhases() {
		List<CancerPhaseDTO> stages = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerPhaseDTO> query = em.createQuery("SELECT c FROM CancerPhaseDTO c order by c.name", CancerPhaseDTO.class); 
			stages = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return stages;
	}
	
	public static CancerPhaseDTO getCancerPhaseById(Integer greadId) {
		CancerPhaseDTO stages = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerPhaseDTO> query = em.createQuery("SELECT c FROM CancerPhaseDTO c where id = :f1", CancerPhaseDTO.class); 
			query.setParameter("f1", greadId);
			stages = query.getSingleResult();
		} catch(Exception e) {
			System.out.println("NO PHASE: " + greadId);
		} 
		return stages;
	}
	
	public static List<CancerFabClassificationDTO> getCancerFABClasses() {
		List<CancerFabClassificationDTO> stages = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerFabClassificationDTO> query = em.createQuery("SELECT c FROM CancerFabClassificationDTO c order by fabname", CancerFabClassificationDTO.class); 
			stages = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return stages;
	}
	
	public static CancerFabClassificationDTO getCancerFABClasseById(Integer greadId) {
		CancerFabClassificationDTO stages = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerFabClassificationDTO> query = em.createQuery("SELECT c FROM CancerFabClassificationDTO c where id = :f1", CancerFabClassificationDTO.class); 
			query.setParameter("f1", greadId);
			stages = query.getSingleResult();
		} catch(Exception e) {
			System.out.println("NO FAB CLASS: " + greadId);
		} 
		return stages;
	}

	public static List<CancerWhoClassificationDTO> getCancerWHOClasses() {
		List<CancerWhoClassificationDTO> stages = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerWhoClassificationDTO> query = em.createQuery("SELECT c FROM CancerWhoClassificationDTO c order by c.whoname", CancerWhoClassificationDTO.class); 
			stages = query.getResultList();
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			em.close();
		}
		return stages;
	}

	public static CancerWhoClassificationDTO getCancerWHOClasseById(Integer greadId) {
		CancerWhoClassificationDTO stages = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerWhoClassificationDTO> query = em.createQuery("SELECT c FROM CancerWhoClassificationDTO c where id = :f1", CancerWhoClassificationDTO.class); 
			query.setParameter("f1", greadId);
			stages = query.getSingleResult();
		} catch(Exception e) {
			System.out.println("NO FAB CLASS: " + greadId);
		} 
		return stages;
	}
}

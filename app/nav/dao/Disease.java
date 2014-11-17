package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.BreastCancerStageDTO;
import models.CancerMutationDTO;
import models.CancerTypeDTO;
import models.DiseaseMasterDTO;
import util.JPAUtil;

public class Disease {
	public final static int BREAST_CANCER_ID = 1; 
	public final static int COLON_CANCER_ID = 4;
	public final static int ESOPHAGEAL_CANCER_ID = 6;
	public final static int LUNG_CANCER_ID = 3;
	public final static int PROSTATE_CANCER_ID = 2; 	
	public final static int RECTAL_CANCER_ID = 5;
	public static List<DiseaseMasterDTO> allDiseases() {
		List<DiseaseMasterDTO> diseases = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<DiseaseMasterDTO> query = em.createQuery("FROM DiseaseMasterDTO order by name", DiseaseMasterDTO.class); 
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
		finally {
		}
		return stages;
	}
	public static List<CancerTypeDTO> getCancerTypes(boolean isRoot) {
		List<CancerTypeDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CancerTypeDTO> query = em.createQuery("SELECT c FROM CancerTypeDTO c where c.roottype = :f2  order by c.name", CancerTypeDTO.class);
			query.setParameter("f2", isRoot);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}
}

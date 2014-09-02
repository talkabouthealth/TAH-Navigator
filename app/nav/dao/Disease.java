package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.BreastCancerStageDTO;
import models.DiseaseMasterDTO;
import util.JPAUtil;

public class Disease {
	public final static int BREAST_CANCER_ID = 1; 
	public static List<DiseaseMasterDTO> allDiseases() {
		List<DiseaseMasterDTO> diseases = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<DiseaseMasterDTO> query = em.createQuery("FROM DiseaseMasterDTO", DiseaseMasterDTO.class); 
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
			TypedQuery<BreastCancerStageDTO> query = em.createQuery("FROM BreastCancerStageDTO", BreastCancerStageDTO.class); 
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
}

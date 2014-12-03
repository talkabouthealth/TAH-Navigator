package nav.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.DefaultTemplateDetailDTO;
import models.DefaultTemplateMasterDTO;
import models.DiseaseMasterDTO;
import models.InputDefaultDTO;
import util.JPAUtil;

public class DefaultTemplateDAO {

	public static List<DefaultTemplateMasterDTO> getPatientTemplate(Integer diseaseId) {
		List<DefaultTemplateMasterDTO> diseases = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<DefaultTemplateMasterDTO> query = em.createQuery("Select c FROM DefaultTemplateMasterDTO c where c.diseaseid = :f1 ORDER BY c.templatename", DefaultTemplateMasterDTO.class);
			query.setParameter("f1", diseaseId);
			
			diseases = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return diseases;
	}
	
	public static boolean isTemplate(Integer diseaseId) {
		List<String> diseases = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<String> query = em.createQuery("Select c.templatename FROM DefaultTemplateMasterDTO c where c.diseaseid = :f1 GROUP BY c.templatename", String.class);
			query.setParameter("f1", diseaseId);
			
			diseases = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} 
		if(diseases != null && diseases.size()>0) {
			return true;
		} else 
			return false;
	}
	
	public static  List<DefaultTemplateDetailDTO> getInputDefaultByPageField(Integer templateId) {
		List<DefaultTemplateDetailDTO> types = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<DefaultTemplateDetailDTO> query = em.createQuery("SELECT c FROM DefaultTemplateDetailDTO c where c.templateid = :f3", DefaultTemplateDetailDTO.class);
			query.setParameter("f3", templateId);
			types = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return types;
	}
}

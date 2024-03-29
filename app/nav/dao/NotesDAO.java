package nav.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPAUtil;

import models.NoteDTO;
import models.SecurityQuestionDTO;
import models.UserDTO;

public class NotesDAO {

	public static List<NoteDTO> getPatientNotesList(String patientId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<NoteDTO> arrayList = null;
		try{
			TypedQuery<NoteDTO> query = em.createQuery("SELECT c FROM NoteDTO c WHERE c.noteFor.id = :field  ORDER BY editdate DESC ", NoteDTO.class); 
			query.setParameter("field", new Integer(patientId));
			arrayList = query.getResultList();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			em.close();
		}
	//	List<NoteDTO> arrayList = em.createQuery( "from NoteDTO where noteFor.id =:field order by noteDate desc", NoteDTO.class ).getResultList();
		return arrayList;
	}
	
	public static NoteDTO getByField(String fieldName, Object value) {
		NoteDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<NoteDTO> query = em.createQuery("SELECT c FROM NoteDTO c WHERE c."+fieldName+" = :field", NoteDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static NoteDTO getLastNoteFor(UserDTO patient){
		NoteDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		Integer in = new Integer(patient.getId());
		try {
			TypedQuery<NoteDTO> query = em.createQuery("SELECT c FROM NoteDTO c WHERE c.noteFor.id = :field  ORDER BY editdate DESC ", NoteDTO.class); 
			query.setMaxResults(1);
			query.setParameter("field", in);
			dto = query.getSingleResult();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return dto;
	}
}

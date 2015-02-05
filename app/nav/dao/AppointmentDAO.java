package nav.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import util.JPAUtil;
import models.AppointmentDTO;
import models.AppointmentGroupDTO;
import models.NoteDTO;
import models.PatientMedicationDTO;
import models.UserDTO;
import models.UserDetailsDTO;


public class AppointmentDAO {

	public static List<AppointmentDTO> getAppointmentListByField(String fieldName, Object param) {
		List<AppointmentDTO> dtoList = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c WHERE c."+fieldName+" = :field and deleteflag = false order by appointmentdate asc", AppointmentDTO.class); 
			query.setParameter("field", param);
			dtoList = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dtoList;
	}
	
	public static List<AppointmentDTO> getAppointmentListByField(String fieldName, Object param ,Date date, String status,int pageId) {
		List<AppointmentDTO> dtoList = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c " +
					" WHERE c."+fieldName+" = :field and deleteflag = false and c.appointmentdate "
					+(status.equalsIgnoreCase("past")?"<":" >=")+" :date order by appointmentdate desc", 
					AppointmentDTO.class);
			query.setParameter("field", param);
			query.setParameter("date", date);
			query.setFirstResult(pageId);
			query.setMaxResults(10);
			dtoList = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dtoList;
	}
	
	public static long getTotalappointmentForDashboard(String fieldName, Object param ,Date date, String status) {
		EntityManager em = JPAUtil.getEntityManager();
		long total = 0l;
		try {
			TypedQuery<Long> query = em.createQuery("SELECT count(c.id) as total FROM AppointmentDTO c " +
					" WHERE c."+fieldName+" = :field and deleteflag = false and c.appointmentdate "
					+(status.equalsIgnoreCase("past")?"<":" >=")+" :date and deleteflag = false group by patientid.id", Long.class);
			
			query.setParameter("field", param);
			query.setParameter("date", date);
			total = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
		return total;
	}
	
	public static ArrayList<Integer> getTotalappointments(String fieldName, Object param ,Date date, String status) {
		ArrayList<Integer> totalArr = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<Long> query = em.createQuery("SELECT count(c.id) as total FROM AppointmentDTO c " +
					" WHERE c."+fieldName+" = :field and deleteflag = false and c.appointmentdate "
					+(status.equalsIgnoreCase("past")?"<":" >=")+" :date and deleteflag = false group by patientid.id", Long.class);
			
			query.setParameter("field", param);
			query.setParameter("date", date);
			long total = query.getSingleResult();
			System.out.println(status + " : " + total);
			if(total>10) {
				totalArr = new ArrayList<Integer>();
				System.out.println(status + " : " + total/10);
				if(total%10>0) {
					total = total/10+1;
				} else 
					total = total/10;
				for (int i = 1; i <= total; i++) {
					totalArr.add(i);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
		return totalArr;
	}
	
	public static List<AppointmentDTO> patientAllAppointments(Integer patientId) {		
		EntityManager em = JPAUtil.getEntityManager();
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT o FROM AppointmentDTO o WHERE o.patientid.id = :patientId AND o.deleteflag = false", AppointmentDTO.class);			
			query.setParameter("patientId", patientId);
			appointments = query.getResultList();			
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return appointments;
	}
	
	
	public static AppointmentDTO getAppointmentByField(String fieldName, Object param) {
		AppointmentDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c WHERE c."+fieldName+" = :field", AppointmentDTO.class);
			query.setMaxResults(1);
			query.setParameter("field", param);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dto;
	}
	/**
	 * get last appointment info of patient
	 * @param fieldName
	 * @param param
	 * @return
	 */
	public static AppointmentDTO getLastAppointment(UserDTO patient,Date date) {
		AppointmentDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		Integer in = new Integer(patient.getId());
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c WHERE c.patientid.id = :field and  c.appointmentdate < :date and deleteflag = false ORDER BY appointmentdate  desc, appointmenttime DESC ", AppointmentDTO.class);
			query.setMaxResults(1);
			query.setParameter("field",in);
			query.setParameter("date",date);
			dto = query.getSingleResult();
		} catch(Exception e) {
//			System.out.println(e.getMessage());
		} finally {
		}
		return dto;
	}
	
	/**
	 * get last appointment info of patient
	 * @param fieldName
	 * @param param
	 * @return
	 */
	public static AppointmentDTO getLatestAppointment(String fieldName, Object param ,Date date, String status) {
		
		AppointmentDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT c FROM AppointmentDTO c " +
					" WHERE c."+fieldName+" = :field and deleteflag = false and c.appointmentdate "+(status.equalsIgnoreCase("past")?"<":" >=")+" :date and deleteflag = false order by c.appointmentdate", AppointmentDTO.class); 
			query.setMaxResults(1);
			query.setParameter("field", param);
			query.setParameter("date", date);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dto;
	}
	
	public static AppointmentDTO nextAppointment(Integer patientId) {
		AppointmentDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<AppointmentDTO> query = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.patientid.id = :patientId and a.deleteflag = false and a.appointmentdate >= current_date ORDER BY a.appointmentdate ASC", AppointmentDTO.class);
		query.setMaxResults(1);
		query.setParameter("patientId", patientId);
		try {
			dto = query.getSingleResult();
		} catch (NoResultException e) {			
//			e.printStackTrace();
		}
		return dto;
	}
	
	public static boolean updateAppointmentsGroup(String appointmentGroupId) {
		
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="update AppointmentDTO set purpose = :fp1 where appointmentgroupid = :f0";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		
		query.setParameter("fp1", false);
		query.setParameter("f0", appointmentGroupId);
		query.executeUpdate();
		em.getTransaction().commit();
		
		return true;
	}
	
	
	
	public static AppointmentGroupDTO getAppointmentGroupByField(String fieldName, Object param) {
		AppointmentGroupDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentGroupDTO> query = em.createQuery("SELECT c FROM AppointmentGroupDTO c WHERE c."+fieldName+" = :field", AppointmentGroupDTO.class);
			query.setMaxResults(1);
			query.setParameter("field", param);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dto;
	}
	
	public static List<AppointmentDTO> futureAppointments(int patientId) {
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.patientid.id = :patientId and a.deleteflag = false and a.appointmentdate  >= :date order by appointmentdate asc", AppointmentDTO.class);
			query.setParameter("patientId", patientId);
			query.setParameter("date", new Date());			
			appointments = query.getResultList();
			for (AppointmentDTO dto : appointments) {
				if (dto.getCaremember() != null) {
					UserDetailsDTO userDetails = UserDAO.getDetailsById(dto.getCaremember().getId());
					dto.setExpertMobile(userDetails.getMobile());
				}				
			}
		} catch(Exception e) {
			//e.printStackTrace();
		} 
		return appointments;
	}
	public static List<AppointmentDTO> pastAppointments(int patientId) {
		List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<AppointmentDTO> query = em.createQuery("SELECT a FROM AppointmentDTO a WHERE a.patientid.id = :patientId and a.deleteflag = false and a.appointmentdate  < :date order by appointmentdate desc", AppointmentDTO.class);
			query.setParameter("patientId", patientId);
			query.setParameter("date", new Date());			
			appointments = query.getResultList();
			for (AppointmentDTO dto : appointments) {
				if (dto.getCaremember() != null) {
					UserDetailsDTO userDetails = UserDAO.getDetailsById(dto.getCaremember().getId());
					dto.setExpertMobile(userDetails.getMobile());
				}				
			}
		} catch(Exception e) {
			//e.printStackTrace();
		} 
		return appointments;
	}
}

package nav.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nav.dto.DistressBean;
import nav.dto.PatientBean;
import models.AppointmentDTO;
import models.BreastCancerInfoDTO;
import models.CareTeamMasterDTO;
import models.CareTeamMemberDTO;
import models.NoteDTO;
import models.PatienCareTeamDTO;
import models.PatientDetailDTO;
import models.UserCertificateDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import util.JPAUtil;
import util.TemplateExtensions;

public class CareTeamDAO {

	public static List<CareTeamMasterDTO> getAllCareTeam() {
		List<CareTeamMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMasterDTO> query = em.createQuery("FROM CareTeamMasterDTO", CareTeamMasterDTO.class); 
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static boolean addMember(CareTeamMasterDTO careTeam,UserDTO usr) {
		
		EntityManager em = JPAUtil.getEntityManager();
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		
 		CareTeamMemberDTO dto = new CareTeamMemberDTO();
		dto.setCareteamid(careTeam.getId());
		dto.setCareteam(careTeam);
		dto.setMemberid(usr.getId());
		dto.setMember(usr);
		dto.setPrimary(false);
		em.persist(dto);
		em.getTransaction().commit();
		return true;
	}
	public static boolean removeMember(int teamid,int memberid) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="delete from CareTeamMemberDTO where careteamid = :f0 and memberid= :f1";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		
		query.setParameter("f0", teamid);
		query.setParameter("f1", memberid);
		query.executeUpdate();
		em.getTransaction().commit();
		return true;
	}
	
	public static boolean makePrimary(int teamid,int memberid) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="update CareTeamMemberDTO set primary = :fp1 where careteamid = :f0";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		
		query.setParameter("fp1", false);
		query.setParameter("f0", teamid);
		int result = query.executeUpdate();
		em.getTransaction().commit();

		hql ="update CareTeamMemberDTO set primary= :fp2 where careteamid = :f1 and memberid= :f2";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		query = em.createQuery(hql);
		query.setParameter("fp2", true);
		query.setParameter("f1", teamid);
		query.setParameter("f2", memberid);
		result = query.executeUpdate();
		em.getTransaction().commit();
		
		System.out.println("result Edit care team : " + result);
		return true;
	}

	public static boolean createPatienCareTeamAll(UserDTO patien) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="INSERT INTO PatienCareTeamDTO(careteamid, patienid) SELECT id, "+patien.getId()+" FROM CareTeamMasterDTO where id=2 or id=3";
		em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		int result = query.executeUpdate();
		em.getTransaction().commit();
		System.out.println("result : " + result);
		return true;
	}

	public static List<PatienCareTeamDTO> getPatienCareTeamByField(String fieldName, Object value) {
		List<PatienCareTeamDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatienCareTeamDTO> query = em.createQuery("SELECT c FROM PatienCareTeamDTO c WHERE c."+fieldName+" = :field and c.patien.isActive = true order by id", PatienCareTeamDTO.class);
			// and c.patien.isverified = true
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static CareTeamMasterDTO getCareTeamByField(String fieldName, Object value) {
		CareTeamMasterDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMasterDTO> query = em.createQuery("SELECT c FROM CareTeamMasterDTO c WHERE c."+fieldName+" = :field", CareTeamMasterDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static List<CareTeamMemberDTO> getCareTeamMembersByField(String fieldName, Object value) {
		List<CareTeamMemberDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMemberDTO> query = em.createQuery("FROM CareTeamMemberDTO c WHERE c."+fieldName+" = :field order by primary desc", CareTeamMemberDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static CareTeamMemberDTO getCareTeamMembersByMemberId(Object value) {
		List<CareTeamMemberDTO> list = getCareTeamMembersByField("memberid",value);
		if(list!=null && list.size()>0)
			return list.get(0);
		else
			return null;
	}
	
	public static List<PatientBean> patientsOfCareTeam(int userId, Map<String, String> filterParams) {
		CareTeamMemberDTO careTeam =  getCareTeamMembersByMemberId(userId);		
		ArrayList<PatientBean> patients = new ArrayList<PatientBean>();
		if (careTeam != null) {
			List<PatienCareTeamDTO> patientDtos = getPatienCareTeamByField("careteamid",careTeam.getCareteamid());				
			for (PatienCareTeamDTO patientDto : patientDtos) {				
				UserDetailsDTO userDetails = UserDAO.getDetailsById(patientDto.getPatienid());					
				PatientDetailDTO patientDetail  = ProfileDAO.getPatientByField("id", patientDto.getPatienid());								
				/* need to refactor - getDiagnosis method performing unnecessary queries for this call. This method is good for the MyDiagnosis page though - Murray */
				Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(patientDto.getPatienid());
				BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
				DistressBean distress = DistressDAO.getLastDistress(patientDto.getPatien());
				AppointmentDTO appointment = AppointmentDAO.getLastAppointment(patientDto.getPatien(), new Date());				
				AppointmentDTO nextAppointment = AppointmentDAO.nextAppointment(patientDto.getPatienid());
				NoteDTO noteFor = NotesDAO.getLastNoteFor(patientDto.getPatien());
				
				
				if (filterParams.containsKey("disease")) {					
					Integer diseaseId = new Integer(filterParams.get("disease"));
					// filter based on disease only if disease id is not zero
					if (diseaseId.intValue() != 0) {
						if (patientDetail != null && patientDetail.getDiseaseId() != null) {
							if (patientDetail.getDiseaseId().compareTo(diseaseId) != 0) {
								continue;
							}		
						}
						else {
							continue;
						}
					}
				}
				
				//searchPatient
				if (filterParams.containsKey("searchPatient")) {					
					String searchStr = filterParams.get("searchPatient");					
					if (searchStr.length() > 0) {
						if (userDetails != null) {
							String patientName = String.valueOf(TemplateExtensions.usreNameNew(userDetails.getUser().getName(), userDetails.getId())).trim();
							patientName = patientName.toLowerCase();
							if (!patientName.contains(searchStr.toLowerCase())) {
								continue;
							}
						}
						else {
							continue;
						}						
					}
				}
				PatientBean patient = new PatientBean();
				patient.setUserDetails(userDetails);
				patient.setPatientOtherDetails(patientDetail);				
				patient.setBreastCancerInfo(breastCancerInfo);								
				if (distress != null) {
					patient.setDistress(distress);
				}				
				if (nextAppointment != null) {
					patient.setNextAppointment(nextAppointment);
				}				
				if(appointment != null) {
					patient.setAppointmentInfo(appointment);
				}								
				if(noteFor!= null) {
					patient.setNote(noteFor);
				}
				patients.add(patient);
			}
		}
		return patients;		
	}
	
	/*  following are implemented, please update list, when someone change this 
	 *  lastDistressCheckDate, desc
	 *  patientName, asc 
	 *  lastVisit, desc 
	 *  lastDistressLevel, desc   
	 */
	public static List<PatientBean> sortPatients(List<PatientBean> patients, Map<String, String> sortBy) {
	//  sort by distress checkin date
		if (sortBy.containsKey("lastDistressCheckDate")) {
			final String sortOrder = sortBy.get("lastDistressCheckDate");
			Collections.sort(patients, new Comparator<PatientBean>() {
				@Override
				public int compare(PatientBean o1, PatientBean o2) {
					Date o1Date = null;
					Date o2Date = null;
					if (o1.getDistress() != null) {
						o1Date = o1.getDistress().getDistressDate();
					}
					if (o2.getDistress() != null) {
						o2Date = o2.getDistress().getDistressDate();
					}
					
					if (sortOrder.equalsIgnoreCase("desc")) {					
						if (o1Date != null && o2Date != null) {
							return o2Date.compareTo(o1Date);
						}						
						else if (o2Date != null) {
							return 1;
						}
						else if (o1Date != null) {
							return -1;
						}									
						else {
							return 0;
						}				
					}					
					else if (sortOrder.equalsIgnoreCase("asc")) {
						if (o1Date != null && o2Date != null) {
							return o1Date.compareTo(o2Date);
						}						
						else if (o1Date != null) {
							return -1;
						}
						else if (o2Date != null) {
							return 1;
						}
						else {
							return 0;
						}
					}
					return 0;
				}
			});
		}
		//  sort by distress level		
		if (sortBy.containsKey("lastDistressLevel")) {
			final String sortOrder = sortBy.get("lastDistressLevel");
			Collections.sort(patients, new Comparator<PatientBean>() {
				@Override
				public int compare(PatientBean o1, PatientBean o2) {
					int o1DistressLevel = -1;
					int o2DistressLevel = -1;
					if (o1.getDistress() != null) {
						o1DistressLevel = o1.getDistress().getCurDist();
					}
					if (o2.getDistress() != null) {
						o2DistressLevel = o2.getDistress().getCurDist();
					}
					
					if (sortOrder.equalsIgnoreCase("desc")) {					
						if (o1DistressLevel != -1 && o2DistressLevel != -1) {
							if (o2DistressLevel > o1DistressLevel) {
								return 1;
							}
							else if (o2DistressLevel < o1DistressLevel) {
								return -1;
							}
							else {
								return 0;
							}
						}
						else if (o2DistressLevel != -1) {
							return 1;
						}
						else if (o1DistressLevel != -1) {
							return -1;
						}				
						else {
							return 0;
						}				
					}					
					else if (sortOrder.equalsIgnoreCase("asc")) {
						if (o1DistressLevel != -1 && o2DistressLevel != -1) {
							if (o1DistressLevel > o2DistressLevel) {
								return 1;
							}
							else if (o1DistressLevel < o2DistressLevel) {
								return -1;
							}
							else {
								return 0;
							}
						}
						else if (o1DistressLevel != -1) {
							return -1;
						}
						else if (o2DistressLevel != -1) {
							return 1;
						}				
						else {
							return 0;
						}	
					}
					return 0;
				}
			});
		}
		
		//  sort by patient name
		if (sortBy.containsKey("patientName")) {
			final String sortOrder = sortBy.get("patientName");
			Collections.sort(patients, new Comparator<PatientBean>() {
				@Override
				public int compare(PatientBean o1, PatientBean o2) {
					String o1PatientName = null;
					String o2PatientName = null;					
					o1PatientName = String.valueOf(TemplateExtensions.usreNameNew(o1.getUserDetails().getUser().getName(), o1.getUserDetails().getId())).trim();
					o2PatientName = String.valueOf(TemplateExtensions.usreNameNew(o2.getUserDetails().getUser().getName(), o2.getUserDetails().getId())).trim();
					
					
					if (sortOrder.equalsIgnoreCase("desc")) {					
						if (o1PatientName != null && o2PatientName != null) {
							return o2PatientName.compareToIgnoreCase(o1PatientName);							
						}
						else if (o2PatientName != null) {
							return 1;
						}
						else if (o1PatientName != null) {
							return -1;
						}				
						else {
							return 0;
						}				
					}					
					else if (sortOrder.equalsIgnoreCase("asc")) {
						if (o1PatientName != null && o2PatientName != null) {
							return o1PatientName.compareToIgnoreCase(o2PatientName);							
						}
						else if (o1PatientName != null) {
							return -1;
						}
						else if (o2PatientName != null) {
							return 1;
						}				
						else {
							return 0;
						}
					}
					return 0;
				}
			});
		}
		
		//  sort by last Visit
		if (sortBy.containsKey("lastVisit")) {
			final String sortOrder = sortBy.get("lastVisit");
			Collections.sort(patients, new Comparator<PatientBean>() {
				@Override
				public int compare(PatientBean o1, PatientBean o2) {
					Date o1Date = null;
					Date o2Date = null;					
					if (o1.getAppointmentInfo() != null) {
						o1Date = o1.getAppointmentInfo().getAppointmentdate();
					}
					if (o2.getAppointmentInfo() != null) {
						o2Date = o2.getAppointmentInfo().getAppointmentdate();
					}
					
					
					if (sortOrder.equalsIgnoreCase("desc")) {					
						if (o1Date != null && o2Date != null) {
							return o2Date.compareTo(o1Date);
						}						
						else if (o2Date != null) {
							return 1;
						}
						else if (o1Date != null) {
							return -1;
						}									
						else {
							return 0;
						}				
					}					
					else if (sortOrder.equalsIgnoreCase("asc")) {
						if (o1Date != null && o2Date != null) {
							return o1Date.compareTo(o2Date);
						}						
						else if (o1Date != null) {
							return -1;
						}
						else if (o2Date != null) {
							return 1;
						}
						else {
							return 0;
						}
					}
					return 0;
				}
			});
		}
		return patients;
	}
}

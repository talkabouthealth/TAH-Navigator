package nav.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nav.dto.CareMember;
import nav.dto.CareTeam;
import nav.dto.DistressBean;
import nav.dto.ExpertBean;
import nav.dto.PatientBean;
import nav.dto.TeamDetails;
import models.AddressDTO;
import models.AppointmentDTO;
import models.BreastCancerInfoDTO;
import models.CareTeamMasterDTO;
import models.CareTeamMemberDTO;
import models.ExpertDetailDTO;
import models.NoteDTO;
import models.OtherCareMemberDTO;
import models.PatienCareTeamDTO;
import models.PatientCareTeamMemberDTO;
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
	
	public static List<CareTeamMasterDTO> getAllActiveCareTeam() {
		List<CareTeamMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMasterDTO> query = em.createQuery("FROM CareTeamMasterDTO where active = true and adminteam = true", CareTeamMasterDTO.class); 
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}

	public static List<CareTeamMasterDTO> getPatientCareTeamNotAdded(int patientId) {
		List<CareTeamMasterDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMasterDTO> query = em.createQuery("FROM CareTeamMasterDTO where active = true and adminteam = true and id not in (select careteamid from PatienCareTeamDTO where deleted = false and patienid = :field)", CareTeamMasterDTO.class);
			query.setParameter("field", patientId);
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
		
		List<PatienCareTeamDTO> patients = getPatienCareTeamByField("careteamid",careTeam.getId());
		for (PatienCareTeamDTO patienCareTeamDTO : patients) {
			addCareMember(patienCareTeamDTO.getCareteamid(),usr.getId(),patienCareTeamDTO.getPatienid(),false);
		}
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

		hql ="update PatientCareTeamMemberDTO set deleted= :fp2 where careteamid = :f1 and memberid= :f2";
		query = em.createQuery(hql);
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		query = em.createQuery(hql);
		query.setParameter("fp2", true);
		query.setParameter("f1", teamid);
		query.setParameter("f2", memberid);
		int result = query.executeUpdate();
		em.getTransaction().commit();
		System.out.println("result Edit care team : " + result);

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

	public static boolean makePatientPrimary(int teamid,int memberid,int patientid,boolean other) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="update PatientCareTeamMemberDTO set primary = :fp1 where careteamid = :f0 and patientid = :f1";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		
		query.setParameter("fp1", false);
		query.setParameter("f0", teamid);
		query.setParameter("f1", patientid);
		int result = query.executeUpdate();
		em.getTransaction().commit();
		
		hql ="update OtherCareMemberDTO set primary = false where careteamid = :f0 and patientid = :f1 and primary = true";
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();	
		query = em.createQuery(hql);
		query.setParameter("f0", teamid);
		query.setParameter("f1", patientid);
		query.executeUpdate();
		em.getTransaction().commit();
		
		if(other) {
			hql ="update OtherCareMemberDTO set primary= true where id= :f2";
			if(!em.getTransaction().isActive())
				em.getTransaction().begin();
			query = em.createQuery(hql);
			query.setParameter("f2", memberid);
			result = query.executeUpdate();
			em.getTransaction().commit();
		} else {
			hql ="update PatientCareTeamMemberDTO set primary= :fp2 where careteamid = :f1 and memberid= :f2 and patientid = :f3";
			if(!em.getTransaction().isActive())
				em.getTransaction().begin();
			query = em.createQuery(hql);
			query.setParameter("fp2", true);
			query.setParameter("f1", teamid);
			query.setParameter("f2", memberid);
			query.setParameter("f3", patientid);
			result = query.executeUpdate();
			em.getTransaction().commit();
		}
		System.out.println("result Edit care team : " + result);
		return true;
	}
	
	
	
	public static boolean deleteCareMember(int teamid,int memberid,int patientid,boolean isOther) {
		EntityManager em = JPAUtil.getEntityManager();
		if(isOther) {
			String hql ="delete from OtherCareMemberDTO p where p.id= :f2 ";
			Query query = em.createQuery(hql);
			if(!em.getTransaction().isActive())
				em.getTransaction().begin();
			query.setParameter("f2", memberid);
			int result = query.executeUpdate();
			em.getTransaction().commit();
			System.out.println("result Edit care team : " + result);
		} else {
			String hql ="update PatientCareTeamMemberDTO set deleted= :fp2 where careteamid = :f1 and memberid= :f2 and patientid = :f3";
			Query query = em.createQuery(hql);
			if(!em.getTransaction().isActive())
				em.getTransaction().begin();
			query = em.createQuery(hql);
			query.setParameter("fp2", true);
			query.setParameter("f1", teamid);
			query.setParameter("f2", memberid);
			query.setParameter("f3", patientid);
			int result = query.executeUpdate();
			em.getTransaction().commit();
			System.out.println("result Edit care team : " + result);
		}
		
		return true;
	}
	
	public static boolean addCareMember(int teamid,int memberid,int patientid,boolean primary) {
		EntityManager em = JPAUtil.getEntityManager();
		if(primary) {
			String hql ="update PatientCareTeamMemberDTO set primary = false where careteamid = :f0 and patientid = :f1 and primary = true";
			if(!em.getTransaction().isActive())
				em.getTransaction().begin();	
			Query query = em.createQuery(hql);
			query.setParameter("f0", teamid);
			query.setParameter("f1", patientid);
			query.executeUpdate();

			hql ="update OtherCareMemberDTO set primary = false where careteamid = :f0 and patientid = :f1 and primary = true";
			query = em.createQuery(hql);
			query.setParameter("f0", teamid);
			query.setParameter("f1", patientid);
			query.executeUpdate();
			em.getTransaction().commit();
		} 
		String hql ="update PatientCareTeamMemberDTO set deleted= :fp1,primary = :fp2  where careteamid = :f1 and memberid= :f2 and patientid = :f3";
		Query query = em.createQuery(hql);
		if(!em.getTransaction().isActive())
			em.getTransaction().begin();
		query = em.createQuery(hql);
		query.setParameter("fp2", primary);
		query.setParameter("fp1", false);
		query.setParameter("f1", teamid);
		query.setParameter("f2", memberid);
		query.setParameter("f3", patientid);
		int result = query.executeUpdate();
		em.getTransaction().commit();
		if(result<=0) {
			PatientCareTeamMemberDTO dto = new PatientCareTeamMemberDTO();
			dto.setCareteamid(teamid);
			dto.setMemberid(memberid);
			dto.setPatientid(patientid);
			dto.setPrimary(primary);
			dto.setDeleted(false);
			BaseDAO.save(dto);
		}
		return true;
	}
	
	public static boolean addOtherCareMember(int teamid,int patientid, String memberName, String memberTitle, String memberTelephone, boolean primary) {

		if(primary) {
			EntityManager em = JPAUtil.getEntityManager();
			String hql ="update PatientCareTeamMemberDTO set primary = false where careteamid = :f0 and patientid = :f1 and primary = true";
			if(!em.getTransaction().isActive())
				em.getTransaction().begin();	
			Query query = em.createQuery(hql);
			query.setParameter("f0", teamid);
			query.setParameter("f1", patientid);
			query.executeUpdate();
			
			hql ="update OtherCareMemberDTO set primary = false where careteamid = :f0 and patientid = :f1 and primary = true";
			query = em.createQuery(hql);
			query.setParameter("f0", teamid);
			query.setParameter("f1", patientid);
			query.executeUpdate();
			em.getTransaction().commit();
		}

		OtherCareMemberDTO otherMember = new OtherCareMemberDTO();
		otherMember.setCareteamid(teamid);
		otherMember.setName(memberName);
		otherMember.setPatientid(patientid);
		otherMember.setPrimary(primary);
		otherMember.setTelephone(memberTelephone);
		otherMember.setTitle(memberTitle);
		BaseDAO.save(otherMember);

		return true;
	}

	public static PatienCareTeamDTO addCareTeam(UserDTO patien,CareTeamMasterDTO teamId) {
//		EntityManager em = JPAUtil.getEntityManager();
		List<PatienCareTeamDTO> team = getPatienCareTeamByPatientANDteam(patien.getId(),teamId);
		PatienCareTeamDTO patienCareTeam = null;
		if(team != null && team.size()>0) {
			System.out.println("There is team");
			for (PatienCareTeamDTO patienCareTeamDTO : team) {
				patienCareTeamDTO.setDeleted(false);
				BaseDAO.update(patienCareTeamDTO);
				patienCareTeam = patienCareTeamDTO;
			}
		} else {
			System.out.println("There is no team");
			patienCareTeam = createPatienCareTeamAllNew(patien,teamId);
		}
		return patienCareTeam;
	}

	public static PatienCareTeamDTO createPatienCareTeamAllNew(UserDTO patien,CareTeamMasterDTO teamId) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="INSERT INTO PatienCareTeamDTO(careteamid, patienid) SELECT id, "+patien.getId()+" FROM CareTeamMasterDTO where id= :teamId";
		em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		query.setParameter("teamId", teamId.getId());
		int result = query.executeUpdate();
		em.getTransaction().commit();

		
		PatienCareTeamDTO careTeamDTO = getCareTeamByPatientAndTeamid(patien.getId(), teamId.getId()); 
//		PatienCareTeamDTO careTeamDTO = new PatienCareTeamDTO();
//		careTeamDTO.setCareteamid(teamId.getId());
//		careTeamDTO.setPatienid(patien.getId());
//		careTeamDTO.setDeleted(false);
//		careTeamDTO.setPatien(patien);
//		careTeamDTO.setCareteam(teamId);
//		em.getTransaction().begin();
//		em.persist(careTeamDTO);
//		em.getTransaction().commit();

		hql ="INSERT INTO PatientCareTeamMemberDTO(careteamid, memberid, patientid, primary) SELECT careteamid,memberid,"
		+patien.getId()+", primary FROM CareTeamMemberDTO where careteamid= :teamId";
		em.getTransaction().begin();	
		Query query1 = em.createQuery(hql);
		query1.setParameter("teamId", teamId.getId());
		result = query1.executeUpdate();
		em.getTransaction().commit();

		System.out.println("result : " + result);
		return careTeamDTO;
	}
	
	public static boolean createPatienCareTeamAllWithPrimary(UserDTO patien,Integer teamId,Integer expertId) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="INSERT INTO PatienCareTeamDTO(careteamid, patienid) SELECT id, "+patien.getId()+" FROM CareTeamMasterDTO where id= :teamId";
		em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		query.setParameter("teamId", teamId);
		int result = query.executeUpdate();
		em.getTransaction().commit();

		List<CareTeamMemberDTO> list = getMasterCareTeamMembersByField("careteamid",teamId);
		boolean isExpertinList = false;
		if(list != null && list.size()>0) {
			for (CareTeamMemberDTO careTeamMemberDTO : list) {
				if(careTeamMemberDTO.getMemberid() == expertId.intValue()) {
					isExpertinList = true;
				}
			}
			if(isExpertinList) {
				for (CareTeamMemberDTO careTeamMemberDTO : list) {
					PatientCareTeamMemberDTO dto = new PatientCareTeamMemberDTO();
					dto.setCareteamid(teamId);
					dto.setDeleted(false);
					dto.setMemberid(careTeamMemberDTO.getMemberid());
					dto.setPatientid(patien.getId());
					if(careTeamMemberDTO.getMemberid() == expertId.intValue()) {
						dto.setPrimary(true);
					} else {
						dto.setPrimary(false);
					}
					BaseDAO.save(dto); 
				}
			} else {
				for (CareTeamMemberDTO careTeamMemberDTO : list) {
					PatientCareTeamMemberDTO dto = new PatientCareTeamMemberDTO();
					dto.setCareteamid(teamId);
					dto.setDeleted(false);
					dto.setMemberid(careTeamMemberDTO.getMemberid());
					dto.setPatientid(patien.getId());
					dto.setPrimary(careTeamMemberDTO.isPrimary());
					BaseDAO.save(dto); 
				}	
			}
		}
		System.out.println("result : " + result);
		return true;
	}
	
	public static CareTeamMasterDTO createMasterCareTeam (String teamtype,String center,String address1,String city,String state,String zip,String phone) {
		AddressDTO address = new AddressDTO();
		address.setPhone(phone);
		address.setLine1(center);
		address.setCity(city);
		address.setLine2(address1);
		address.setState(state);
		address.setZip(zip);
		BaseDAO.save(address);

		CareTeamMasterDTO teamMasterDTO = new CareTeamMasterDTO();
		teamMasterDTO.setAddress(address);
		teamMasterDTO.setName(teamtype);
		teamMasterDTO.setActive(true);
		teamMasterDTO.setAdminteam(false);
		BaseDAO.save(teamMasterDTO);
		return teamMasterDTO;
	}

	/*
	public static boolean createPatienCareTeamAll(UserDTO patien) {
		EntityManager em = JPAUtil.getEntityManager();
		String hql ="INSERT INTO PatienCareTeamDTO(careteamid, patienid) SELECT id, "+patien.getId()+" FROM CareTeamMasterDTO where id=2 or id=3";
		em.getTransaction().begin();	
		Query query = em.createQuery(hql);
		int result = query.executeUpdate();
		em.getTransaction().commit();

		hql ="INSERT INTO PatientCareTeamMemberDTO(careteamid, memberid, patientid, primary) SELECT careteamid,memberid,"
		+patien.getId()+", primary FROM CareTeamMemberDTO where careteamid=2 or careteamid=3";
		em.getTransaction().begin();	
		Query query1 = em.createQuery(hql);
		result = query1.executeUpdate();
		em.getTransaction().commit();

		System.out.println("result : " + result);
		return true;
	}
	*/
	public static boolean migrateCareTeam(int patientId, int careteamId) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			String hql ="INSERT INTO PatientCareTeamMemberDTO(careteamid, memberid, patientid, primary) SELECT careteamid,memberid,"+patientId+
					", primary FROM CareTeamMemberDTO where careteamid="+careteamId;
			em.getTransaction().begin();	
			Query query1 = em.createQuery(hql);
			query1.executeUpdate();
			em.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return true;
	}
	
	public static List<PatienCareTeamDTO> getPatienCareTeamByPatientANDteam(Integer patientid, CareTeamMasterDTO teamid) {
		List<PatienCareTeamDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatienCareTeamDTO> query = em.createQuery("SELECT c FROM PatienCareTeamDTO c WHERE c.patienid = :field1 and c.careteamid = :field2 and c.patien.isActive = true order by id", PatienCareTeamDTO.class);
			query.setParameter("field1", patientid);
			query.setParameter("field2", teamid.getId());
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dto;
	}

	public static List<PatienCareTeamDTO> getPatienCareTeamByField(String fieldName, Object value) {
		List<PatienCareTeamDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatienCareTeamDTO> query = em.createQuery("SELECT c FROM PatienCareTeamDTO c WHERE c."+fieldName+" = :field and deleted = false and c.patien.isActive = true order by id", PatienCareTeamDTO.class);
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
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
//			em.close();
		}
		return dto;
	}

	public static CareTeamMasterDTO getCareTeamByTypeAndCenter(String type, String center) {
		CareTeamMasterDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMasterDTO> query = em.createQuery("SELECT c FROM CareTeamMasterDTO c WHERE c.name = :field1 and c.address.line1 = :field2 and c.adminteam = true", CareTeamMasterDTO.class); 
			query.setParameter("field1", type);
			query.setParameter("field2", center);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
//			e.printStackTrace();
		}
		return dto;
	}

	public static PatienCareTeamDTO getCareTeamByPatientAndTeamid(Integer patientId,Integer teamId) {
		PatienCareTeamDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatienCareTeamDTO> query = em.createQuery("SELECT c FROM PatienCareTeamDTO c WHERE c.patienid = :field1 and c.careteamid = :field2", PatienCareTeamDTO.class); 
			query.setParameter("field1", patientId);
			query.setParameter("field2", teamId);
			dto = query.getResultList().get(0);
		} catch(Exception e) {
//			e.printStackTrace();
		}
		return dto;
	}
	
	public static List<PatientCareTeamMemberDTO> getCareTeamMembersByField(String fieldName, Object value) {
		List<PatientCareTeamMemberDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientCareTeamMemberDTO> query = em.createQuery("FROM PatientCareTeamMemberDTO c WHERE c."+fieldName+" = :field order by primary desc", PatientCareTeamMemberDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
//			e.printStackTrace();
		} finally {
//			em.close();
		}
		return dto;
	}
	
	public static List<PatientCareTeamMemberDTO> getCareTeamMembersByPatient(Integer patientid, Integer careTeamId) {
		List<PatientCareTeamMemberDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<PatientCareTeamMemberDTO> query = em.createQuery("FROM PatientCareTeamMemberDTO c WHERE c.deleted = false and c.careteamid = :field and c.patientid = :field1 "
					+" order by primary desc, memberid", PatientCareTeamMemberDTO.class); 
			query.setParameter("field", careTeamId);
			query.setParameter("field1", patientid);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
//			em.close();
		}
		return dto;
	}
	
	public static List<OtherCareMemberDTO> getOtherCareTeamMembersByPatient(Integer patientid, Integer careTeamId) {
		List<OtherCareMemberDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			TypedQuery<OtherCareMemberDTO> query = em.createQuery("FROM OtherCareMemberDTO c WHERE c.careteamid = :field and c.patientid = :field1 order by primary desc", OtherCareMemberDTO.class); 
			query.setParameter("field", careTeamId);
			query.setParameter("field1", patientid);
			dto = query.getResultList();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
//			em.close();
		}
		return dto;
	}

	public static List<CareTeamMemberDTO> getMasterCareTeamMembersByField(String fieldName, Object value) {
		List<CareTeamMemberDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<CareTeamMemberDTO> query = em.createQuery("FROM CareTeamMemberDTO c WHERE c."+fieldName+" = :field order by primary desc", CareTeamMemberDTO.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
//			em.close();
		}
		return dto;
	}
	
	public static List<ExpertBean> teamExperts(int careTeamId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<ExpertBean> experts = new ArrayList<ExpertBean>();
		List<CareTeamMemberDTO> members = null;
		TypedQuery<CareTeamMemberDTO> query = em.createQuery("FROM CareTeamMemberDTO c WHERE c.careteamid = :careteamid order by primary desc", CareTeamMemberDTO.class);
		query.setParameter("careteamid", careTeamId);
		try {
			members = query.getResultList();
			for (CareTeamMemberDTO member : members) {
				ExpertBean expert = new ExpertBean();				
				UserDetailsDTO userDetails = UserDAO.getDetailsById(member.getMemberid());
				ExpertDetailDTO expertDetails = ProfileDAO.getExpertByField("id", member.getMemberid());
				expert.setUserDetails(userDetails);
				expert.setExpertDetail(expertDetails);
				experts.add(expert);				
			}
		} catch (NoResultException e) {			
			e.printStackTrace();
		}
		return experts;
	}
	
	public static List<Integer> getCareTeamOfExpert(Integer expertId) {
		List<Integer> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<Integer> query = em.createQuery("select id from CareTeamMasterDTO where active = true and id in (select careteamid from CareTeamMemberDTO where memberid = :field)", Integer.class);
			query.setParameter("field", expertId);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
//			em.close();
		}
		return dto;
	}
	
	public static List<CareMember> getCareTeamMemberOfExpert(Integer expertId) {
		List<CareTeamMemberDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		List<CareMember> members = new ArrayList<CareMember>();
		try {
			TypedQuery<CareTeamMemberDTO> query = em.createQuery("from CareTeamMemberDTO where careteamid in (select careteamid from CareTeamMemberDTO where memberid = :field)", CareTeamMemberDTO.class);
			query.setParameter("field", expertId);
			dto = query.getResultList();
			
			for (CareTeamMemberDTO u : dto) {
		    	CareMember cm = new CareMember();		    	
		    	ExpertDetailDTO expert = null;
		    	UserDetailsDTO details = null;
		    	TypedQuery<UserDetailsDTO> userQuery = em.createQuery("SELECT c FROM UserDetailsDTO c WHERE c.id = :id", UserDetailsDTO.class); 
		    	userQuery.setParameter("id", u.getMemberid());
				try {
					details = userQuery.getSingleResult();
				} catch (NoResultException e1) {
					e1.printStackTrace();
				}		    	
		    	TypedQuery<ExpertDetailDTO> expertQuery = em.createQuery("SELECT e FROM ExpertDetailDTO e WHERE e.id = :id", ExpertDetailDTO.class);
		    	expertQuery.setParameter("id", u.getMemberid());
		    	try {
					expert = expertQuery.getSingleResult();
				} catch (NoResultException e2) {
					e2.printStackTrace();
				}				
		    	cm.setId(u.getMemberid());
		    	cm.setFirstName(details.getFirstName());
		    	cm.setLastName(details.getLastName());
		    	if (details.getMobile() != null) {
		    		cm.setPhone(details.getMobile());
		    	}
		    	else {
		    		cm.setPhone(details.getHomePhone());
		    	}
		    	if (expert != null) {
		    		cm.setDesignation(expert.getDesignation().getDesignation());
		    	}		    	
		    	members.add(cm);
		    }					
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
//			em.close();
		}
		return members;
	}

	
	public static List<CareTeam> patientTeams(Integer patientId) {
		List<PatienCareTeamDTO> careTeamDtos = getPatienCareTeamByField("patienid", patientId);
		List<CareTeam> careTeams = new ArrayList<CareTeam>();		
		for (PatienCareTeamDTO teamDto: careTeamDtos) {
			List<ExpertBean> experts = teamExperts(teamDto.getCareteamid());
			CareTeam team = new CareTeam();
			team.setCareTeamDto(teamDto);			
			if (experts.size() > 0) {
				team.setLeadExpert(experts.get(0));
				experts.remove(0);
			}
			team.setExperts(experts);
			careTeams.add(team);
		}
		return careTeams;
	}
	
	public static List<Integer> getCareTeamMembersByMemberId(Object value) {
		List<Integer> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<Integer> query = em.createQuery("select careteamid FROM PatientCareTeamMemberDTO c WHERE c.memberid = :field and deleted = false group by careteamid", Integer.class); 
			query.setParameter("field", value);
			dto = query.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
//			em.close();
		}
		return dto;
	}
	
	public static List<PatientBean> patientsOfCareTeam(int userId, Map<String, String> filterParams) {
		List<Integer> careTeam =  getCareTeamMembersByMemberId(userId);		
		ArrayList<PatientBean> patients = new ArrayList<PatientBean>();
		if (careTeam != null) {
			for (Integer teamId : careTeam) {
				List<PatienCareTeamDTO> patientDtos = getPatienCareTeamByField("careteamid",teamId);				
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
							} else {
								continue;
							}
						}
					}
					if (filterParams.containsKey("md")) {
						Integer careMemberId = new Integer(filterParams.get("md"));
						// filter based on disease only if disease id is not zero
						if (careMemberId.intValue() != 0) {
							if (appointment != null && appointment.getCaremember() != null) {
								UserDTO user = appointment.getCaremember();
								if (user.getId() != careMemberId.intValue()) {
									continue;
								}
							} else {
								continue;
							}
						}
					}

					if (filterParams.containsKey("searchPatient")) {
						String searchStr = filterParams.get("searchPatient");
						if (searchStr.length() > 0) {
							if (userDetails != null) {
								String patientName = String.valueOf(TemplateExtensions.usreNameNew(userDetails.getUser().getName(), userDetails.getId())).trim();
								patientName = patientName.toLowerCase();
								if (!patientName.contains(searchStr.toLowerCase())) {
									continue;
								}
							} else {
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
		//  sort by next Visit
		if (sortBy.containsKey("nextVisit")) {
			final String sortOrder = sortBy.get("nextVisit");
			Collections.sort(patients, new Comparator<PatientBean>() {
				@Override
				public int compare(PatientBean o1, PatientBean o2) {
					Date o1Date = null;
					Date o2Date = null;					
					if (o1.getNextAppointment() != null) {
						o1Date = o1.getNextAppointment().getAppointmentdate();
					}
					if (o2.getNextAppointment() != null) {
						o2Date = o2.getNextAppointment().getAppointmentdate();
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
	
	public static List<TeamDetails> getTeamDetails(Integer patientId) {		
		List<TeamDetails> teamDetailsList = new ArrayList<TeamDetails>();		
		List<PatienCareTeamDTO> careTeams = getPatienCareTeamByField("patienid", patientId);
		for (PatienCareTeamDTO team : careTeams) {
			CareTeamMasterDTO careTeam = null;
			List<ExpertBean> experts = new ArrayList<ExpertBean>();
			ExpertBean primaryExpert = null;
			OtherCareMemberDTO otherPrimaryCareMember = null;
			List<OtherCareMemberDTO> otherCareMembers = new ArrayList<OtherCareMemberDTO>();
			
			boolean primary = false;
			int members = 0;
			careTeam = team.getCareteam();
			List<PatientCareTeamMemberDTO> careMembers = getCareTeamMembersByPatient(patientId, careTeam.getId());
			List<OtherCareMemberDTO> careMembers1 = getOtherCareTeamMembersByPatient(patientId, careTeam.getId());
			
			if (careMembers != null) {
				for (PatientCareTeamMemberDTO cm : careMembers) {					
					ExpertBean expert = new ExpertBean();
					UserDetailsDTO userDetails = UserDAO.getDetailsById(cm.getMemberid());
					ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", cm.getMemberid());
					expert.setUserDetails(userDetails);
					expert.setExpertDetail(expertDetail);
					if (cm.isPrimary()) {
						primary = true;
					}
					if (members == 0) {
						primaryExpert = expert;
						members++;
					} else {
						experts.add(expert);
					}
				}
			}
			if (careMembers1 != null) {
				for (OtherCareMemberDTO cm : careMembers1) {				
					if (!primary && cm.isPrimary()) {
						otherPrimaryCareMember = cm;
						experts.add(primaryExpert);
						primaryExpert = null;
					} else {
						otherCareMembers.add(cm);
					}
				}
			}
			TeamDetails teamDetails = new TeamDetails();
			teamDetails.setCareTeam(careTeam);
			teamDetails.setPrimaryExpert(primaryExpert);
			teamDetails.setExperts(experts);
			teamDetails.setOtherPrimaryCareMember(otherPrimaryCareMember);
			teamDetails.setOtherCareMembers(otherCareMembers);
			
			teamDetailsList.add(teamDetails);
		}		
		return teamDetailsList;
	}
}

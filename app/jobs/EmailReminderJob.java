package jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.InvitedDTO;
import nav.dao.InvitationDAO;
import notifiers.Mail;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.On;
import util.EmailUtil;

@On("0 0 7 * * ?")
//@Every("1min")
public class EmailReminderJob  extends Job {

	public void doJob() {
		System.out.println("Job : Email Reminder to invited users using sailthrou");
		runEmailJob();
	}

	private boolean runEmailJob() {
		//Mail.morningNotification();
		//Moffitt-Welcome-Reminder
		List<InvitedDTO> invitedUsers = InvitationDAO.getReminders();
		for (InvitedDTO invitedDTO : invitedUsers) {
			System.out.println("Email: "+invitedDTO.getEmail());
			String url = "http://tvrhnavigator.com/";
   		 	Map<String, Object> vars = new HashMap<String, Object>();
   		 	vars.put("username", invitedDTO.getFirstname() + " " + invitedDTO.getLastname() + "[" + invitedDTO.getEmail() + "]");
   		 	vars.put("signupurl", url + "/invited-registration/" + invitedDTO.getId());
//   		EmailUtil.sendEmail(EmailUtil.MOFFITT_WELCOMEREMINDER,vars,invitedDTO.getEmail());
   		 	EmailUtil.sendEmail(EmailUtil.MOFFITT_WELCOMEREMINDER,vars,"murray@talkabouthealth.com");
		}
		return true;
	}
}
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
			Map<String, Object> vars;
			if (invitedDTO.getAppointmentdate() != null) {
				vars = InvitationDAO.mailVariables(EmailUtil.TVRH_INVITE_REMINDER_APPOINTMENT_SCHEDULED, invitedDTO);
				EmailUtil.sendEmail(EmailUtil.TVRH_INVITE_REMINDER_APPOINTMENT_SCHEDULED, vars,invitedDTO.getEmail());
			}
			else {
				vars = InvitationDAO.mailVariables(EmailUtil.TVRH_INVITE_REMINDER_NO_APPOINTMENT_SCHEDULED, invitedDTO);
				EmailUtil.sendEmail(EmailUtil.TVRH_INVITE_REMINDER_NO_APPOINTMENT_SCHEDULED, vars,invitedDTO.getEmail());
			}			
		}
		return true;
	}
}
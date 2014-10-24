package jobs;

import notifiers.Mail;
import play.jobs.Job;
import play.jobs.On;

@On("0 0 7 * * ?")
public class EmailReminderJob  extends Job {

	public void doJob() {
		System.out.println("Job : Email list population on sailthrou");
		runEmailJob();
	}
	
	private boolean runEmailJob() {
		System.out.println("Added job to send email to patient every 24 hrs or ");
//		Mail.morningNotification();
		return true;
	}
}
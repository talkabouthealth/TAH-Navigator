package notifiers;

import models.MailTemplateDTO;
import models.UserDetailsDTO;
import nav.dao.MailTemplateDAO;
import nav.dto.UserBean;

import org.apache.commons.mail.EmailAttachment;

import play.mvc.Before;
import play.mvc.Mailer;
import util.CommonUtil;

public class Mail extends Mailer {

	public static void welcome(UserDetailsDTO user) {
		MailTemplateDTO mailTemplateDto =  MailTemplateDAO.getByTemplateName("welcome");
		setSubject(mailTemplateDto.getSubject());
		addRecipient(user.getEmail());
		setFrom(mailTemplateDto.getForm());
		send(user);
	}
	
	public static void activation(UserDetailsDTO user,String url) {
		MailTemplateDTO mailTemplateDto =  MailTemplateDAO.getByTemplateName("activation");
		setSubject(mailTemplateDto.getSubject());
		addRecipient(user.getEmail());
		setFrom(mailTemplateDto.getForm());
		send(user,url);
	}
	
	public static void invitation(String firstname,String lastname, String email,int id,String url) {
		MailTemplateDTO mailTemplateDto =  MailTemplateDAO.getByTemplateName("invitation");
		setSubject(mailTemplateDto.getSubject());
//		addRecipient(email);
		addRecipient("aawte.umesh@s5infotech.com");
		setFrom(mailTemplateDto.getForm());
		send(firstname,lastname,id,url);
	}
	public static void forgot(UserDetailsDTO user,String newpassword) {
		MailTemplateDTO mailTemplateDto =  MailTemplateDAO.getByTemplateName("forgot");
		setSubject(mailTemplateDto.getSubject());
		addRecipient(user.getEmail());
		setFrom(mailTemplateDto.getForm());
		send(user,newpassword);
	}
	
	public static void morningNotification() {
		MailTemplateDTO mailTemplateDto =  MailTemplateDAO.getByTemplateName("notification1");
		setSubject(mailTemplateDto.getSubject());
//		addRecipient(user.getEmail());
		addRecipient("admin@talkabouthealth.com");
		setFrom(mailTemplateDto.getForm());
		send();
	}
}
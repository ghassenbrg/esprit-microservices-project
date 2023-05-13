package tn.esprit.notification.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tn.esprit.notification.mail.Mail;
import tn.esprit.notification.mail.MailBuilder;
import tn.esprit.notification.payload.ApiResponse;
import tn.esprit.notification.payload.MailRequest;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment environment;

	@Override
	public ApiResponse sendMail(MailRequest mailRequest) {
		final Mail mail = new MailBuilder().From(environment.getProperty("spring.mail.username"))
				.To(mailRequest.getTo()).Template("mail-template.html").AddContext("subject", "Hello,")
				.AddContext("content", mailRequest.getContent()).Subject(mailRequest.getSubject()).createMail();
		try {
			MimeMessage emailMessage = mailSender.createMimeMessage();
			MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
			mailBuilder.setTo(mail.getMailTo());
			mailBuilder.setFrom(mail.getMailFrom());
			mailBuilder.setText(mail.getMailContent(), true);
			mailBuilder.setSubject(mail.getMailSubject());
			mailSender.send(emailMessage);
		} catch (MessagingException e) {
			return new ApiResponse(Boolean.TRUE, "Failed to send email to " + mail.getMailTo());
		}

		return new ApiResponse(Boolean.TRUE, "Email send successfully to " + mail.getMailTo());
	}
}

package tn.esprit.payment.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
public class MailRequest {
	
	private Long userId;

	@NotBlank
	private String to;

	@NotBlank
	private String subject;

	@NotBlank
	private String content;

	public MailRequest( Long userId,String to, String subject, String content) {
		this.userId = userId;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}
}
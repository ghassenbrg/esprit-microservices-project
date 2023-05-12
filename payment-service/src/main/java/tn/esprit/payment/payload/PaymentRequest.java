package tn.esprit.payment.payload;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
public class PaymentRequest {

	@NotNull
	private Long orderId;
	@NotNull
	private Long userId;
	@NotNull
	private Long buyerId;
	@NotBlank
	private BigDecimal amount;
	@NotBlank
	private String cardHolderName;
	@NotBlank
	private String cardNo;
	@NotBlank
	private String cvv;
	@NotBlank
	private String expDate;
}

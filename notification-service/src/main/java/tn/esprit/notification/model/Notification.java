package tn.esprit.notification.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

	private static final long serialVersionUID = -8525504474431935739L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content")
	@NotBlank
	private String content;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private NotificationType type;

	@Column(name = "user_id")
	private Long userId;

}

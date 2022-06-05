package it.uniroma3.siw.siweventsservice.auth.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "event_credentials_table")
@Getter
@Setter
public class Credentials {

	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String username;
	@NotBlank

	@Column(nullable = false)
	private String password;

	@NotBlank
	@Column(nullable = false)
	private String role;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	public Credentials(){
		this.user = new User();
	}
}

package it.uniroma3.siw.siweventsservice.auth.models;

import it.uniroma3.siw.siweventsservice.models.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "event_user_table")
@Getter
@Setter
@NoArgsConstructor
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String email;

	@ManyToMany
	private List<Event> eventList;


}

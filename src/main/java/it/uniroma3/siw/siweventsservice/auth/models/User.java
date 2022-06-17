package it.uniroma3.siw.siweventsservice.auth.models;

import it.uniroma3.siw.siweventsservice.models.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "event_user_table")
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String email;

	@ManyToMany(mappedBy = "participants")
	private Set<Event> eventList;


	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return firstName.equals(user.firstName) && lastName.equals(user.lastName);
	}

	@Override
	public int hashCode () {
		return Objects.hash(firstName, lastName);
	}
}

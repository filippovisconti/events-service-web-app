package it.uniroma3.siw.siweventsservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
public class Organizer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String country;

	@OneToMany(mappedBy = "organizer")
	private List<Event> events;

	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Organizer organizer = (Organizer) o;
		return Objects.equals(firstName, organizer.firstName) && Objects.equals(lastName, organizer.lastName) && Objects.equals(country, organizer.country);
	}

	@Override
	public int hashCode () {
		return Objects.hash(firstName, lastName, country);
	}
}
package it.uniroma3.siw.siweventsservice.models;

import it.uniroma3.siw.siweventsservice.auth.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Getter
@Setter
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotNull
	@ManyToOne
	private Organizer organizer;

	//@DateTimeFormat(pattern = "dd/MM/yyyy h:mm a") 		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date date;
	//https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-input-date
	//https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-datetime-picker

	@ManyToMany
	@NotEmpty
	private List<Activity> activityList;

	@ManyToMany
	private Set<User> participants;

	public Event () {
		this.participants = new HashSet<>();
	}

	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Event event = (Event) o;
		return name.equals(event.name) && date.equals(event.date);
	}

	@Override
	public int hashCode () {
		return Objects.hash(name, date);
	}


	public float getDuration () {
		float duration = 0;
		for (Activity a : this.getActivityList()) {
			duration += a.getDuration();
		}
		return duration;
	}

	public boolean addParticipant (User user) {
		return this.participants.add(user);
	}

	public boolean removeParticipant (User user) {
		return this.participants.remove(user);
	}
}

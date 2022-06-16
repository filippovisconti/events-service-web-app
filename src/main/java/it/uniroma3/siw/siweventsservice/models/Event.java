package it.uniroma3.siw.siweventsservice.models;

import it.uniroma3.siw.siweventsservice.auth.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

	@ManyToMany(mappedBy = "eventList")
	private List<User> participants;

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
}

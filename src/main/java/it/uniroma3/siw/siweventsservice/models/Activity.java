package it.uniroma3.siw.siweventsservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Duration;
import java.util.List;

@Entity
@Getter
@Setter
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	private Duration duration;

	@NotEmpty
	@ManyToMany
	private List<Tool> toolList;



}

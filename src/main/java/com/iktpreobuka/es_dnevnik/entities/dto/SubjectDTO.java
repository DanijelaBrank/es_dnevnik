package com.iktpreobuka.es_dnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;

public class SubjectDTO {
	
	@NotBlank(message = "Subject name must be not blank or null.")
	@Size(min = 2, max = 30, message = "Subject name lenght must be string between {min} and {max}.")
	@Column(nullable = false)
	private String name;

//	@NotBlank(message = "Class group must have value JUNIOR_CLASS or SENIOR_CLASS")
	@Column(nullable = false)
	private EClassGroup classGroup;

	public SubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(EClassGroup classGroup) {
		this.classGroup = classGroup;
	}
	
	

}

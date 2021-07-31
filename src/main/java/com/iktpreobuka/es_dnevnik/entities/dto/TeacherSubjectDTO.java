package com.iktpreobuka.es_dnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;

public class TeacherSubjectDTO {
	
	@NotNull(message = "Username must be provided.")
	@Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.")
	@Column(nullable = false)
	private String userName;
	
	@NotNull(message = "Subject name must be provided.")
	@Size(min = 2, max = 30, message = "Subject name lenght must be string between {min} and {max}.")
	@Column(nullable = false)
	private String subject;
	
	@NotNull(message = "ClassGroup must be provided.")
	@Column(nullable = true)
	private EClassGroup classGroup;

	public TeacherSubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public EClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(EClassGroup classGroup) {
		this.classGroup = classGroup;
	}

	
}

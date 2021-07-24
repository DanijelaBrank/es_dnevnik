package com.iktpreobuka.es_dnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;

public class TeacherSubjectClassDTO {
	@NotBlank(message = "Username must be not blank or null.")
	@Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.")
	@Column(nullable = false)
	private String userName;
	
	@NotBlank(message = "Subject name must be not blank or null.")
	@Size(min = 2, max = 30, message = "Subject name lenght must be string between {min} and {max}.")
	@Column(nullable = false)
	private String subject;
	
	@Column(name = "class_group")
	private EClassGroup classGroup;
	
	@NotNull(message = "Grade must be provided.")
	@Range(min = 1,max=8, message = "Grade must be between 1 - 8")
	@Column(nullable = false)
	private Integer grade;
	
	@NotNull(message = "Sign of class must be provided.")
	@Range(min = 1,max=10, message = "Sign of class must be between 1 - 10")
	@Column(nullable = false)
	private Integer sign;

	public TeacherSubjectClassDTO() {
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}
	
	

}

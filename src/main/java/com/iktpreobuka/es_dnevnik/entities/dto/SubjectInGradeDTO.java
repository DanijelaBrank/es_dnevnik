package com.iktpreobuka.es_dnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubjectInGradeDTO {
	
	@NotNull(message = "Grade must be provided.")
	@Min(value = 1, message = "Grade must be between 1 - 8")
	@Max(value = 8, message = "Grade must be between 1 - 8")
	@Column(nullable = false)
	private Integer grade;
	
	

	@NotNull(message = "Subject name must be provided.")
	@Size(min = 2, max = 30, message = "Subject name lenght must be string between {min} and {max}.")
	@Column(nullable = false)
	private String subject;
	
	@NotNull(message = "Grade must be provided.")
	@Min(value = 1, message = "Weekly hours fund for this subject must be between 1 - 10")
	@Max(value = 10, message = "Weekly hours fund for this subject must be between 1 - 10")
	@Column(nullable = false)
	private Integer hoursFund;
	
	public SubjectInGradeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getHoursFund() {
		return hoursFund;
	}

	public void setHoursFund(Integer hoursFund) {
		this.hoursFund = hoursFund;
	}
	
	
	

}

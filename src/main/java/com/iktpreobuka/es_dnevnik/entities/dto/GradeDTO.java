package com.iktpreobuka.es_dnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;

public class GradeDTO {
	
	@NotNull(message = "Grade must be provided.")
	@Min(value = 1, message = "Grade must be between 1 - 8")
	@Max(value = 8, message = "Grade must be between 1 - 8")
	@Column(nullable = false)
	private Integer grade;

//	@NotBlank(message = "Class group must have value JUNIOR_CLASS or SENIOR_CLASS")
	@Column(nullable = false)
	private EClassGroup classGroup;

	public GradeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public EClassGroup getClassGroup() {
		return classGroup;
	}

	public void setClassGroup(EClassGroup classGroup) {
		this.classGroup = classGroup;
	}
	
	

}

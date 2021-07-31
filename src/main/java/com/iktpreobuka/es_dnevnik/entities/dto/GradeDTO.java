package com.iktpreobuka.es_dnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;

public class GradeDTO {
	
	@NotNull(message = "Grade must be provided.")
	@Range(min = 1, max = 8, message = "Grade must be between 1 - 8")
	@Column(nullable = false, unique = true)
	private Integer grade;

	@NotNull(message = "ClassGroup must be provided.")
	@Column(nullable = true)
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

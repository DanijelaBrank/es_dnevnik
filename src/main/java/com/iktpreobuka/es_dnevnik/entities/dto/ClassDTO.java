package com.iktpreobuka.es_dnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class ClassDTO {
	
	@NotNull(message = "Grade must be provided.")
	@Range(min = 1,max=8, message = "Grade must be between 1 - 8")
	@Column(nullable = false)
	private Integer grade;
	
	@NotNull(message = "Sign of class must be provided.")
	@Range(min = 1,max=10, message = "Sign of class must be between 1 - 10")
	@Column(nullable = false)
	private Integer sign;

	public ClassDTO() {
		super();
		// TODO Auto-generated constructor stub
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

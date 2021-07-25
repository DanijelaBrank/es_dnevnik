package com.iktpreobuka.es_dnevnik.entities.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MarkDTO {

	@NotBlank(message = "Username must be not blank or null.")
	@Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.")
	@Column(nullable = false)
	private String studentUserName;

	@NotBlank(message = "Subject name must be not blank or null.")
	@Size(min = 2, max = 30, message = "Subject name lenght must be string between {min} and {max}.")
	@Column(nullable = false)
	private String subject;

	@NotNull(message = "Mark must be provided.")
	@Range(min = 1, max = 5, message = "Mark must be between 1 - 5")
	@Column(nullable = false)
	private Integer mark;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(nullable = true)
	private LocalDate date;

	@NotBlank(message = "Description must be not blank or null.")
	@Size(min = 2, max = 30, message = "Description lenght must be string between {min} and {max}.")
	@Column(nullable = false)
	private String description;

	public MarkDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStudentUserName() {
		return studentUserName;
	}

	public void setStudentUserName(String studentUserName) {
		this.studentUserName = studentUserName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

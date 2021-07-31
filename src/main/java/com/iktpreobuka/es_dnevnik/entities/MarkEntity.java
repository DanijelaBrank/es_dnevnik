 package com.iktpreobuka.es_dnevnik.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class MarkEntity {
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "mark_id")
	private Integer id;
	
	// **** vrednost za ocenu od 1-5 je ogranicena u MarkDTO ****
	private Integer mark;
	
	// **** vrednost za semestar od 1 ili 2 je ogranicena u metodi za unos semestra ****
	private Integer semester;
	
	private LocalDate date;
	
	private String description;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="student")
	private StudentEntity student;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="grader")
	private TeachingEntity grader;
	
	public MarkEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
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

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public TeachingEntity getGrader() {
		return grader;
	}

	public void setGrader(TeachingEntity grader) {
		this.grader = grader;
	}
	
	@Override
	public String toString() {
		return "MarkEntity [id=" + id + ", mark=" + mark + ", semester=" + semester + ", date=" + date
				+ ", description=" + description + ", grader=" + grader + "]";
	}
	
	
	public String toStudentString() {
		return "MarkEntity [id=" + id + ", mark=" + mark + ", semester=" + semester + ", date=" + date
				+ ", description=" + description +", in subject=" + grader.getTeacherSubject().getSubject().getName() + ",by teacher=" + grader.getTeacherSubject().getTeacher().getUserName() + "]";
	}

	
}

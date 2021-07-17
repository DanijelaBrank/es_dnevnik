package com.iktpreobuka.es_dnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class ClassEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="classInGrade")
	private GradeEntity classInGrade;

	@Column
	private Integer sign;
	
	@OneToMany(mappedBy = "classLevel", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<StudentEntity> students;
	
	@OneToMany(mappedBy = "teachToClass", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TeachingEntity> teaching;

	public ClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public LevelEntity getClassInLevel() {
//		return classInLevel;
//	}
//
//	public void setClassInLevel(LevelEntity classInLevel) {
//		this.classInLevel = classInLevel;
//	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

	public List<TeachingEntity> getTeaching() {
		return teaching;
	}

	public void setTeaching(List<TeachingEntity> teaching) {
		this.teaching = teaching;
	}

	public GradeEntity getClassInGrade() {
		return classInGrade;
	}

	public void setClassInGrade(GradeEntity classInGrade) {
		this.classInGrade = classInGrade;
	}

	
}

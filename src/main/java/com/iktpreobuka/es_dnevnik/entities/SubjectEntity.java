package com.iktpreobuka.es_dnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class SubjectEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "subject_id")
	private Integer id;
	
	@Column(name = "subject_name")
	private String name;
	
	@Column(name = "class_group")
	private EClassGroup classGroup;
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TeacherSubjectEntity> subjectTeacher;
	
	@OneToMany(mappedBy = "subjectGrade", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<SubjectInGradeEntity> subjectsInGrade;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<TeacherSubjectEntity> getSubjectTeacher() {
		return subjectTeacher;
	}

	public void setSubjectTeacher(List<TeacherSubjectEntity> subjectTeacher) {
		this.subjectTeacher = subjectTeacher;
	}

	

	public List<SubjectInGradeEntity> getSubjectsInGrade() {
		return subjectsInGrade;
	}

	public void setSubjectsInGrade(List<SubjectInGradeEntity> subjectsInGrade) {
		this.subjectsInGrade = subjectsInGrade;
	}

	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

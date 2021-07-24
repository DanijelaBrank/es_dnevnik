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
public class GradeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private Integer grade;
	
	private EClassGroup classGroup;
	
	private Integer semester; 
	
	@OneToMany(mappedBy = "classInGrade", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<ClassEntity> classes;
	
	@OneToMany(mappedBy = "gradeSab", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<SubjectInGradeEntity>subjectsInLevel;

	public GradeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	
	public List<ClassEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}

	public List<SubjectInGradeEntity> getSubjectsInLevel() {
		return subjectsInLevel;
	}

	public void setSubjectsInLevel(List<SubjectInGradeEntity> subjectsInLevel) {
		this.subjectsInLevel = subjectsInLevel;
	}
	
	
}

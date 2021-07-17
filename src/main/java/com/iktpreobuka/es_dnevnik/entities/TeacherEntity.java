package com.iktpreobuka.es_dnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class TeacherEntity extends UserEntity {

	@Column(name = "class_group")
	private EClassGroup classGroup;
	
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TeacherSubjectEntity> teacherSubject;

	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EClassGroup getClassGroup() {
		return classGroup;
	}

	public List<TeacherSubjectEntity> getTeacherSubject() {
		return teacherSubject;
	}

	public void setTeacherSubject(List<TeacherSubjectEntity> teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	public void setClassGroup(EClassGroup classGroup) {
		this.classGroup = classGroup;
	}
	
	
}

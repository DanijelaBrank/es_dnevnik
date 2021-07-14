package com.iktpreobuka.es_dnevnik.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SubjectInLevelEntity {

	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "ls_id")
	 private Integer id;
	 
	 public SubjectInLevelEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Integer hoursFund;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="gradeSab")
	private GradeEntity gradeSab;
	
	public GradeEntity getGradeSab() {
		return gradeSab;
	}

	public void setGradeSab(GradeEntity gradeSab) {
		this.gradeSab = gradeSab;
	}

	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="subjectLev")
	private SubjectEntity subjectLev;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHoursFund() {
		return hoursFund;
	}

	public void setHoursFund(Integer hoursFund) {
		this.hoursFund = hoursFund;
	}

	
//	public LevelEntity getLevelSab() {
//		return levelSab;
//	}
//
//	public void setLevelSab(LevelEntity levelSab) {
//		this.levelSab = levelSab;
//	}

	public SubjectEntity getSubjectLev() {
		return subjectLev;
	}

	public void setSubjectLev(SubjectEntity subjectLev) {
		this.subjectLev = subjectLev;
	}
}

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
public class SubjectInGradeEntity {

	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "gs_id")
	 private Integer id;
	 
	 public SubjectInGradeEntity() {
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
	@JoinColumn(name="subjectGrade")
	private SubjectEntity subjectGrade;

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

	public SubjectEntity getSubjectGrade() {
		return subjectGrade;
	}

	public void setSubjectGrade(SubjectEntity subjectGrade) {
		this.subjectGrade = subjectGrade;
	}

		
}

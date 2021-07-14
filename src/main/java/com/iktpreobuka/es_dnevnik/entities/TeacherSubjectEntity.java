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
import javax.persistence.OneToOne;

@Entity
public class TeacherSubjectEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ts_id")
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="teacher")
	private TeacherEntity teacher;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="subject")
	private SubjectEntity subject;
	
	@OneToOne(mappedBy="teacherSubject",cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private TeachingEntity teaching;
}

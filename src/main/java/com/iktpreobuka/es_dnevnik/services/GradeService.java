package com.iktpreobuka.es_dnevnik.services;

import java.util.List;

import com.iktpreobuka.es_dnevnik.entities.GradeEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectInGradeEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.GradeDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectInGradeDTO;

public interface GradeService {
	
	public GradeEntity addGrade(GradeDTO newGrade);
	public List<GradeEntity> changeSemesterToGrade(Integer semester);
	public SubjectInGradeEntity addSubjectInGrade(SubjectInGradeDTO newSubjectInGrade);
}

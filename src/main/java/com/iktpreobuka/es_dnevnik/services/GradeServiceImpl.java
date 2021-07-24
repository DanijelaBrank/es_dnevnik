package com.iktpreobuka.es_dnevnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.GradeEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectInGradeEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.GradeDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectInGradeDTO;
import com.iktpreobuka.es_dnevnik.repositories.GradeRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectInGradeRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;

@Service
public class GradeServiceImpl implements GradeService {
	
	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private SubjectInGradeRepository subjectInGradeRepository;

	@Override
	public GradeEntity addGrade(GradeDTO newGrade) {
		GradeEntity grade = new GradeEntity();
		grade.setGrade(newGrade.getGrade());
		grade.setClassGroup(newGrade.getClassGroup());
		grade.setSemester(1);
		return gradeRepository.save(grade);		
	}
	
	

	@Override
	public SubjectInGradeEntity addSubjectInGrade(SubjectInGradeDTO newSubjectInGrade) {
		
		GradeEntity gradeForAddSubject = gradeRepository.findByGrade(newSubjectInGrade.getGrade());
		if (!subjectRepository.existsByNameAndClassGroup(newSubjectInGrade.getSubject(),
				gradeForAddSubject.getClassGroup()))
			return null;
		SubjectEntity subjectForAdd = subjectRepository.findByNameAndClassGroup(newSubjectInGrade.getSubject(),
				gradeForAddSubject.getClassGroup());

		SubjectInGradeEntity subjectInGrade = new SubjectInGradeEntity();
		subjectInGrade.setGradeSab(gradeForAddSubject);
		subjectInGrade.setSubjectGrade(subjectForAdd);
		subjectInGrade.setHoursFund(newSubjectInGrade.getHoursFund());
		subjectInGradeRepository.save(subjectInGrade);
		return subjectInGrade;
	}



	@Override
	public List<GradeEntity> changeSemesterToGrade(Integer semester) {
		
		if ((semester!=1)&&(semester!=2))
			return null;
		List<GradeEntity>grades=(List<GradeEntity>) gradeRepository.findAll();
		for(GradeEntity gr:grades)
			gr.setSemester(semester);
		gradeRepository.saveAll(grades);		
		return grades;
	}


	
		

	
}

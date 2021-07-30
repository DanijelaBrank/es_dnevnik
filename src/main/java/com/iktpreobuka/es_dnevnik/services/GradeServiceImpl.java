package com.iktpreobuka.es_dnevnik.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.GradeEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectInGradeEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.GradeDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectInGradeDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ObjectAlreadyExistsException;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.GradeRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectInGradeRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;

@Service
public class GradeServiceImpl implements GradeService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
				gradeForAddSubject.getClassGroup())) {
			logger.info("Subject doesn't exists!");
			throw new ResourceNotFoundException("Subject not found");}
		if(subjectInGradeRepository.existsBySubjectGradeNameAndGradeSabGrade(newSubjectInGrade.getSubject(),
				newSubjectInGrade.getGrade())) {						
			logger.info("Admin tried to add subject "+newSubjectInGrade.getSubject()+" that already exists at grade"+newSubjectInGrade.getGrade());
			throw new ObjectAlreadyExistsException("Subject "+newSubjectInGrade.getSubject()+" already exists at grade "+newSubjectInGrade.getGrade());}
						
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
		
		if ((semester!=1)&&(semester!=2)) {
			logger.info("Semester must be 1 or 2");
		throw new ResourceNotFoundException("Semester must be 1 or 2");
		}
		List<GradeEntity>grades=(List<GradeEntity>) gradeRepository.findAll();
		for(GradeEntity gr:grades)
			gr.setSemester(semester);
		gradeRepository.saveAll(grades);
		logger.info("Semester successfully changed.");
		return grades;
	}


	
		

	
}

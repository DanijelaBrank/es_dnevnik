package com.iktpreobuka.es_dnevnik.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.es_dnevnik.entities.GradeEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectInGradeEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.GradeDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectInGradeDTO;
import com.iktpreobuka.es_dnevnik.repositories.GradeRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectInGradeRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;

@RestController
public class GradeController {

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private SubjectInGradeRepository subjectInGradeRepository;

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addGrade")
	public ResponseEntity<?> addGrade(@Valid @RequestBody GradeDTO newGrade, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);

		GradeEntity grade = new GradeEntity();
		grade.setGrade(newGrade.getGrade());
		grade.setClassGroup(newGrade.getClassGroup());
		gradeRepository.save(grade);
		return new ResponseEntity<>(grade, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addSubjectToGrade")
	public ResponseEntity<?> addSubjectToGrade(@Valid @RequestBody SubjectInGradeDTO newSubjectInGrade,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}

		if (!gradeRepository.existsByGrade(newSubjectInGrade.getGrade()))
			return new ResponseEntity<>("Grade doesn't exist. Grade must have value 1 - 8.", HttpStatus.BAD_REQUEST);
		GradeEntity gradeForAddSubject = gradeRepository.findByGrade(newSubjectInGrade.getGrade());
		if (!subjectRepository.existsByNameAndClassGroup(newSubjectInGrade.getSubject(),
				gradeForAddSubject.getClassGroup()))
			return new ResponseEntity<>("Subject doesn't exist or doesn't match to this grade.",
					HttpStatus.BAD_REQUEST);
		SubjectEntity subjectForAdd = subjectRepository.findByNameAndClassGroup(newSubjectInGrade.getSubject(),
				gradeForAddSubject.getClassGroup());

		SubjectInGradeEntity subjectInGrade = new SubjectInGradeEntity();
		subjectInGrade.setGradeSab(gradeForAddSubject);
		subjectInGrade.setSubjectGrade(subjectForAdd);
		subjectInGrade.setHoursFund(newSubjectInGrade.getHoursFund());
		subjectInGradeRepository.save(subjectInGrade);
		return new ResponseEntity<>(subjectInGrade, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));

	}

}

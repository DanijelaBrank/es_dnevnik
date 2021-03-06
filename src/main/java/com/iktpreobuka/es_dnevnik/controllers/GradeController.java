package com.iktpreobuka.es_dnevnik.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.es_dnevnik.entities.GradeEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectInGradeEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.GradeDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectInGradeDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.GradeRepository;
import com.iktpreobuka.es_dnevnik.services.GradeService;

@RestController
public class GradeController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GradeService gradeService;
	@Autowired
	private GradeRepository gradeRepository;
	
//	@Autowired
//	private GradeRepository gradeRepository;


//  ****** DODAVANJE RAZREDA  *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addGrade")
	public ResponseEntity<?> addGrade(@Valid @RequestBody GradeDTO newGrade, BindingResult result) throws SQLIntegrityConstraintViolationException {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		
		if(gradeRepository.existsByGrade(newGrade.getGrade())) {
		logger.info("Grade="+newGrade.getGrade()+" already exists!");
			throw new SQLIntegrityConstraintViolationException("Grade="+newGrade.getGrade()+" already exists!");}
		return new ResponseEntity<>(gradeService.addGrade(newGrade), HttpStatus.OK);
	}
	
//  ****** PROMENA POLUGODISTA *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, path = "/changeSemester/{semester}")
	public ResponseEntity<?> changeSemester(@PathVariable Integer semester) {
		
		List<GradeEntity>grades=gradeService.changeSemesterToGrade(semester);
		if(grades==null)	
			return new ResponseEntity<>("Semester mast be 1 or 2.", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(grades, HttpStatus.OK);
	}

//  ****** DODAVANJE PREDMETA RAZREDU   *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addSubjectToGrade")
	public ResponseEntity<?> addSubjectToGrade(@Valid @RequestBody SubjectInGradeDTO newSubjectInGrade,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		SubjectInGradeEntity subjectInGrade = gradeService.addSubjectInGrade(newSubjectInGrade);
		if (subjectInGrade == null)
			return new ResponseEntity<>("Subject doesn't exist or doesn't match to this grade.",
					HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(subjectInGrade, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

}

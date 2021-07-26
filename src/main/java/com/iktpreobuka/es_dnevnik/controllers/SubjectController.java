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

import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectDTO;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.es_dnevnik.services.SubjectService;
import com.iktpreobuka.es_dnevnik.services.TeacherService;

@RestController
public class SubjectController {

		
	@Autowired
	private SubjectService subjectService;

	@Autowired
	private TeacherService teacherService;

//  ****** DODAVANJE PREDMETA *********
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addSubject")
	public ResponseEntity<?> addSubject(@Valid @RequestBody SubjectDTO newSubject, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		SubjectEntity subject =subjectService.addNewSubject(newSubject);		
		return new ResponseEntity<>(subject, HttpStatus.OK);
	}

//  ****** DODAVANJE PREDMETA NASTAVNIKU  *********

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addSubjectToTeacher")
	public ResponseEntity<?> addSubjectToTeacher(@Valid @RequestBody TeacherSubjectDTO newSubjectToTeacher,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);

		TeacherSubjectEntity subjectToTeacher = teacherService.addSubjectToTeacher(newSubjectToTeacher);
		return new ResponseEntity<>(subjectToTeacher, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

}

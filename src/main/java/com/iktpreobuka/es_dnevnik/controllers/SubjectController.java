package com.iktpreobuka.es_dnevnik.controllers;

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

import com.iktpreobuka.es_dnevnik.entities.MarkEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectDTO;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.es_dnevnik.services.SubjectService;
import com.iktpreobuka.es_dnevnik.services.TeacherService;
import com.iktpreobuka.es_dnevnik.services.UserService;

@RestController
public class SubjectController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SubjectRepository subjectRepository;

//  ****** DODAVANJE PREDMETA *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addSubject")
	public ResponseEntity<?> addSubject(@Valid @RequestBody SubjectDTO newSubject, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		SubjectEntity subject =subjectService.addNewSubject(newSubject);
		logger.info("Admin "+userService.getLoggedUser() +" add subject "+newSubject.getName()+" at "+newSubject.getClassGroup());
		
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
	
//  ****** BRISANJE PREDMETA  *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "deleteSubject/{id}")
	public SubjectEntity deleteSubject(@PathVariable Integer id) {
		if (!subjectRepository.existsById(id)) 			
			return null;
		SubjectEntity subject = subjectRepository.findById(id).get();
		subjectRepository.delete(subject);
		logger.info("Mark with ID= " + subject.getId() + " deleted by admin."); 
		return subject;
	}

}

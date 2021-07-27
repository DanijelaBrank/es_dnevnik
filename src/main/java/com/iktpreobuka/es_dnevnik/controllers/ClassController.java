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

import com.iktpreobuka.es_dnevnik.entities.ClassEntity;
import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.ClassDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectClassDTO;
import com.iktpreobuka.es_dnevnik.repositories.ClassRepository;
import com.iktpreobuka.es_dnevnik.repositories.GradeRepository;
import com.iktpreobuka.es_dnevnik.services.ClassService;

@RestController
public class ClassController {
	
	@Autowired
	private ClassRepository classRepository;	
	@Autowired
	private GradeRepository gradeRepository;	
	@Autowired
	private ClassService classService;

//  ****** DODAVANJE ODELJENJA  *********	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addClass")
	public ResponseEntity<?> addClass(@Valid @RequestBody ClassDTO newClass, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		ClassEntity clazz=new ClassEntity();
		clazz.setClassInGrade(gradeRepository.findByGrade(newClass.getGrade()));
		clazz.setSign(newClass.getSign());
		classRepository.save(clazz);
		return new ResponseEntity<>(clazz, HttpStatus.OK);
	}
	
//  ******* DODAVANJE PREDMETA SA NASTAVNIKOM ODELJENJU  *******
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addSubjectTeacherToClass")
	public ResponseEntity<?> addSubjectWithTeacherToClass(@Valid @RequestBody TeacherSubjectClassDTO newSubjectTeacherToClass,BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		
		TeachingEntity teachingToClass = classService.addTeachingToClass(newSubjectTeacherToClass);
		return new ResponseEntity<>(teachingToClass, HttpStatus.OK);
	}
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
}

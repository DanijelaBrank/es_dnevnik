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
import com.iktpreobuka.es_dnevnik.entities.dto.GradeDTO;
import com.iktpreobuka.es_dnevnik.repositories.GradeRepository;

@RestController
public class GradeController {
	
	@Autowired
	private GradeRepository gradeRepository;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST,path = "/addGrade")
	public ResponseEntity<?> addGrade(@Valid @RequestBody GradeDTO newGrade, BindingResult result) {
	if (result.hasErrors()) {
	return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
	} 
//	else {
//	userValidator.validate(newUser, result);
//	
//	}
	GradeEntity grade = new GradeEntity();
	grade.setGrade(newGrade.getGrade());
	grade.setClassGroup(newGrade.getClassGroup());
	gradeRepository.save(grade);
	return new ResponseEntity<>(grade, HttpStatus.OK);
	}
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
		
		}

}

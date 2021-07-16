package com.iktpreobuka.es_dnevnik.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.UserEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.UserDTO;
import com.iktpreobuka.es_dnevnik.repositories.RoleRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.es_dnevnik.utils.Encryption;
import com.iktpreobuka.es_dnevnik.utils.UserCustomValidator;

@RestController
public class TeacherController {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	UserCustomValidator userValidator;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder)
	{
	binder.addValidators(userValidator);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST,path = "/addTeacher/{group}")
	public ResponseEntity<?> addTeacher(@PathVariable EClassGroup group, @Valid @RequestBody UserDTO newUser, BindingResult result) {
	if (result.hasErrors()) {
	return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
	} 
	else {
	userValidator.validate(newUser, result);
	
	}
	TeacherEntity teacher = new TeacherEntity();
	teacher.setName(newUser.getName());
	teacher.setLastName(newUser.getLastName());
	teacher.setUserName(newUser.getUserName());
	teacher.setEmail(newUser.getEmail());
	teacher.setPassword(Encryption.getPassEncoded(newUser.getPassword()));
	teacher.setRole(roleRepository.findById(2).get());
	teacher.setClassGroup(group);
	teacherRepository.save(teacher);
	return new ResponseEntity<>(teacher, HttpStatus.OK);
	}
	
	private String createErrorMessage(BindingResult result) {
	return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	}


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.es_dnevnik.entities.ParentEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.UserDTO;
import com.iktpreobuka.es_dnevnik.repositories.ParentRepository;
import com.iktpreobuka.es_dnevnik.repositories.RoleRepository;
import com.iktpreobuka.es_dnevnik.utils.Encryption;
import com.iktpreobuka.es_dnevnik.utils.UserCustomValidator;

@RestController
public class ParentController {
	@Autowired
	private ParentRepository parentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	UserCustomValidator userValidator;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder)
	{
	binder.addValidators(userValidator);
	}

	
//  ****** DODAVANJE RODITELJA  *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST,path = "/addParent")
	public ResponseEntity<?> addParent(@Valid @RequestBody UserDTO newUser, BindingResult result) {
	if (result.hasErrors()) {
	return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
	} 
	else {
	userValidator.validate(newUser, result);
	
	}
	ParentEntity parent = new ParentEntity();
	parent.setName(newUser.getName());
	parent.setLastName(newUser.getLastName());
	parent.setUserName(newUser.getUserName());
	parent.setEmail(newUser.getEmail());
	parent.setPassword(Encryption.getPassEncoded(newUser.getPassword()));
	parent.setRole(roleRepository.findById(3).get());
	parentRepository.save(parent);
	return new ResponseEntity<>(parent, HttpStatus.OK);
	}
	
	private String createErrorMessage(BindingResult result) {
	return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	
	}
}

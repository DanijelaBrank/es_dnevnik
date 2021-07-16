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

import com.iktpreobuka.es_dnevnik.entities.UserEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.UserDTO;
import com.iktpreobuka.es_dnevnik.repositories.RoleRepository;
import com.iktpreobuka.es_dnevnik.repositories.UserRepository;
import com.iktpreobuka.es_dnevnik.utils.Encryption;
import com.iktpreobuka.es_dnevnik.utils.UserCustomValidator;

@RestController
public class AdminController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	UserCustomValidator userValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addAdmin")
	public ResponseEntity<?> addAdmin(@Valid @RequestBody UserDTO newUser, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			userValidator.validate(newUser, result);

		}
		UserEntity user = new UserEntity();
		user.setName(newUser.getName());
		user.setLastName(newUser.getLastName());
		user.setUserName(newUser.getUserName());
		user.setEmail(newUser.getEmail());
		user.setPassword(Encryption.getPassEncoded(newUser.getPassword()));
		// RoleEntity role = roleRepository.findById(1).get();
		user.setRole(roleRepository.findById(1).get());
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/createGradebook")
	private String createGradeBook() {
		return "Gradebook successfully created! ";
	}

}

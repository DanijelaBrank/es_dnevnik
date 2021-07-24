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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.es_dnevnik.entities.ClassEntity;
import com.iktpreobuka.es_dnevnik.entities.ParentEntity;
import com.iktpreobuka.es_dnevnik.entities.StudentEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.UserDTO;
import com.iktpreobuka.es_dnevnik.repositories.ClassRepository;
import com.iktpreobuka.es_dnevnik.repositories.ParentRepository;
import com.iktpreobuka.es_dnevnik.repositories.RoleRepository;
import com.iktpreobuka.es_dnevnik.repositories.StudentRepository;
import com.iktpreobuka.es_dnevnik.repositories.UserRepository;
import com.iktpreobuka.es_dnevnik.utils.Encryption;
import com.iktpreobuka.es_dnevnik.utils.UserCustomValidator;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	UserCustomValidator userValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	//  ****** DODAVANJE UCENIKA  *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addStudent")
	public ResponseEntity<?> addStudent(@Valid @RequestBody UserDTO newUser, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			userValidator.validate(newUser, result);

		}
		StudentEntity student = new StudentEntity();
		// UserEntity user=userService.addUser(newUser);
		student.setName(newUser.getName());
		student.setLastName(newUser.getLastName());
		student.setUserName(newUser.getUserName());
		student.setEmail(newUser.getEmail());
		student.setPassword(Encryption.getPassEncoded(newUser.getPassword()));
		student.setRole(roleRepository.findById(4));
		studentRepository.save(student);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

//  ****** DODAVANJE RODITELJA UCENIKU  *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, path = "/addParentToStudent")
	public ResponseEntity<?> addParentToStudent(@RequestParam String studentUserName,
			@RequestParam String parentUserName) {
//	if (result.hasErrors()) {
//	return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
//	} 
//	else {
//	userValidator.validate(newUser, result);
//	
//	}
		if (!userRepository.existsByUserName(studentUserName))
			return new ResponseEntity<>("Student don't exists", HttpStatus.BAD_REQUEST);

		if (!userRepository.existsByUserName(parentUserName))
			return new ResponseEntity<>("Parent don't exists", HttpStatus.BAD_REQUEST);

		StudentEntity student = studentRepository.findByUserName(studentUserName);
		ParentEntity parent = parentRepository.findByUserName(parentUserName);

		student.setParent(parent);
		studentRepository.save(student);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

//  ****** DODAVANJE ODELJENJA UCENIKU  *********
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, path = "/addClassToStudent")
	public ResponseEntity<?> addClassToStudent(@RequestParam String studentUserName, @RequestParam Integer grade,
			@RequestParam Integer sign) {
//	if (result.hasErrors()) {
//	return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
//	} 
//	else {
//	userValidator.validate(newUser, result);
//	
//	}
		if (!userRepository.existsByUserName(studentUserName))
			return new ResponseEntity<>("Student doesn't exists", HttpStatus.BAD_REQUEST);

		ClassEntity clazz = classRepository.findByClassInGradeGradeAndSign(grade, sign);

		if (clazz == null)
			return new ResponseEntity<>("Class doesn't exists", HttpStatus.BAD_REQUEST);

		StudentEntity student = studentRepository.findByUserName(studentUserName);
		student.setClassLevel(clazz);
		studentRepository.save(student);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
//  ****** OCENJIVANJE UCENIKA  *********
	

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));

	}
}

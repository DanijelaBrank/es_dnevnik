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

import com.iktpreobuka.es_dnevnik.entities.MarkEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.MarkDTO;
import com.iktpreobuka.es_dnevnik.services.MarkService;

@RestController
public class MarkController {

	@Autowired
	private MarkService markService;
	
	
//  ******* OCENJIVANJE UCENIKA  *******

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.POST, path = "/addMark")
	public ResponseEntity<?> addMarkToStudent(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		
		MarkEntity mark=markService.addMark(newMark);
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}
	

//  ******* DAVANJE OCENA UCENIKU OD STRANE ADMINISTRATORA *******

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addMarkByAdmin")
	public ResponseEntity<?> addMarkByAdmin(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);

		MarkEntity mark=markService.addMarkByAdmin(newMark);
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}

	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));

	}

}

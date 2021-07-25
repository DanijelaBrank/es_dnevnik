package com.iktpreobuka.es_dnevnik.controllers;

import java.time.LocalDate;
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
import com.iktpreobuka.es_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.es_dnevnik.repositories.StudentRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeachingRepository;
import com.iktpreobuka.es_dnevnik.services.ClassService;
import com.iktpreobuka.es_dnevnik.services.UserService;

@RestController
public class MarkController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeachingRepository teachingRepository;
	
	@Autowired
	private MarkRepository markRepository;
	
	@Autowired
	private UserService userService;
	
//  ******* OCENJIVANJE UCENIKA  *******
	
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.POST, path = "/addMark")
	public ResponseEntity<?> addMarkToStudent(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		if (result.hasErrors()) 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 

		if (!studentRepository.existsByUserName(newMark.getStudentUserName()))
			return new ResponseEntity<>("Student doesn't exists", HttpStatus.BAD_REQUEST);
		
		if(!teachingRepository.existsByTeacherSubjectSubjectNameAndTeacherSubjectTeacherUserName(newMark.getSubject(), userService.getLoggedUser()))
			return new ResponseEntity<>("Student doesn't attend that subject", HttpStatus.BAD_REQUEST);
		
		MarkEntity mark=new MarkEntity();
				mark.setStudent(studentRepository.findByUserName(newMark.getStudentUserName()));
				mark.setGrader(teachingRepository.findByTeacherSubjectSubjectNameAndTeacherSubjectTeacherUserName(newMark.getSubject(), userService.getLoggedUser()));
				mark.setMark(newMark.getMark());
				mark.setDescription(newMark.getDescription());
				if(newMark.getDate()!=null)
					mark.setDate(newMark.getDate());
					else
						mark.setDate(LocalDate.now());
				mark.setSemester(teachingRepository.findByTeacherSubjectSubjectName(newMark.getSubject()).getTeachToClass().getClassInGrade().getSemester());
				markRepository.save(mark);		
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}
	
	
//  ******* DAVANJE OCENA UCENIKU OD STRANE ADMINISTRATORA *******
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addMarkByAdmin")
	public ResponseEntity<?> addMarkByAdmin(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		if (result.hasErrors()) 
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST); 

		if (!studentRepository.existsByUserName(newMark.getStudentUserName()))
			return new ResponseEntity<>("Student doesn't exists", HttpStatus.BAD_REQUEST);
		
		if(!teachingRepository.existsByTeacherSubjectSubjectName(newMark.getSubject()))
			return new ResponseEntity<>("Student doesn't attend that subject", HttpStatus.BAD_REQUEST);
		
		MarkEntity mark=new MarkEntity();
				mark.setStudent(studentRepository.findByUserName(newMark.getStudentUserName()));
				mark.setGrader(teachingRepository.findByTeacherSubjectSubjectName(newMark.getSubject()));
				mark.setMark(newMark.getMark());
				mark.setDescription("Correction By Admin");
				if(newMark.getDate()!=null)
					mark.setDate(newMark.getDate());
					else
						mark.setDate(LocalDate.now());
				mark.setSemester(teachingRepository.findByTeacherSubjectSubjectName(newMark.getSubject()).getTeachToClass().getClassInGrade().getSemester());
				markRepository.save(mark);		
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}
	
	
	

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));

	}

}

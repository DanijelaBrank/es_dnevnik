package com.iktpreobuka.es_dnevnik.controllers;

import java.util.ArrayList;
import java.util.List;
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
import com.iktpreobuka.es_dnevnik.entities.ParentEntity;
import com.iktpreobuka.es_dnevnik.entities.StudentEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;
import com.iktpreobuka.es_dnevnik.entities.UserEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.MarkDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.es_dnevnik.repositories.ParentRepository;
import com.iktpreobuka.es_dnevnik.repositories.StudentRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.es_dnevnik.services.MarkService;
import com.iktpreobuka.es_dnevnik.services.UserService;

@RestController
public class MarkController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MarkService markService;
	@Autowired
	private UserService userService;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ParentRepository parentRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private MarkRepository markRepository;


//  ******* OCENJIVANJE UCENIKA OD STRANE PROFESORA *******

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.POST, path = "/addMark")
	public ResponseEntity<?> addMarkToStudent(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);

		MarkEntity mark = markService.addMark(newMark);
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}

//  ******* DAVANJE OCENA UCENIKU OD STRANE ADMINISTRATORA *******

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addMarkByAdmin")
	public ResponseEntity<?> addMarkByAdmin(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);

		MarkEntity mark = markService.addMarkByAdmin(newMark);
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));

	}

//  ******* PRETRAGA OCENA OD STRANE UCENIKA *******

	@Secured("ROLE_STUDENT")
	@RequestMapping(method = RequestMethod.GET, path = "/findMarkByStudent")
	public List<String> findMarkByStudent() {
		StudentEntity logStudent = studentRepository.findByUserName(userService.getLoggedUser());
		List<String> marksSt = new ArrayList<String>();
		marksSt = markService.findStudentMark(logStudent);
		logger.info("Student " + logStudent.getUserName() + " looked at his grades!");
		return marksSt;
	}

	@Secured("ROLE_STUDENT")
	@RequestMapping(method = RequestMethod.GET, path = "/findStudentMarkBySubject/{subjectName}")
	public List<String> findStudentMarkBySubject(@PathVariable String subjectName) {
		StudentEntity logStudent = studentRepository.findByUserName(userService.getLoggedUser());
		List<String> marksSt = new ArrayList<String>();
		marksSt = markService.findStudentMark(logStudent, subjectName);
		logger.info("Student " + logStudent.getUserName() + " looked at his grades from subject " + subjectName + ".");
		return marksSt;
	}

//  ******* PRETRAGA OCENA OD STRANE RODITELJA *******

	@Secured("ROLE_PARENT")
	@RequestMapping(method = RequestMethod.GET, path = "/getMarkByParent/{studentUserName}")
	public List<String> getMarkByParent(@PathVariable String studentUserName) {

		ParentEntity logParent = parentRepository.findByUserName(userService.getLoggedUser());
		if (!studentRepository.existsByUserNameAndParentId(studentUserName, logParent.getId())) {

			logger.info("Parent " + logParent.getUserName() + " tried to look at the grades for child "
					+ studentUserName + " for which he has no access. ");
			throw new ResourceNotFoundException("You don't have a child with a userName " + studentUserName);
		}
		StudentEntity student = studentRepository.findByUserNameAndParentId(studentUserName, logParent.getId());
		List<String> marksSt = new ArrayList<String>();
		marksSt = markService.findStudentMark(student);
		logger.info("Parent " + logParent.getUserName() + " looked at the grades for child " + studentUserName + "!");
		return marksSt;
	}

	@Secured("ROLE_PARENT")
	@RequestMapping(method = RequestMethod.GET, path = "/getSubjectMarkByParent/{studentUserName}/{subjectName}")
	public List<String> getSubjectMarkByParent(@PathVariable String studentUserName, @PathVariable String subjectName) {

		ParentEntity logParent = parentRepository.findByUserName(userService.getLoggedUser());
		if (!studentRepository.existsByUserNameAndParentId(studentUserName, logParent.getId())) {
			logger.info("Parent " + logParent.getUserName() + " tried to look at the grades for child "
					+ studentUserName + " for which he has no access. ");
			throw new ResourceNotFoundException("You don't have a child with a userName " + studentUserName);
		}
		StudentEntity student = studentRepository.findByUserNameAndParentId(studentUserName, logParent.getId());
		List<String> marksSt = new ArrayList<String>();
		marksSt = markService.findStudentMark(student, subjectName);
		logger.info("Parent " + logParent.getUserName() + " looked at the grades for child " + studentUserName
				+ " from subject " + subjectName + ".");
		return marksSt;
	}
	
//  ******* PRETRAGA OCENA OD STRANE PROFESORA *******

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, path = "/getSubjectMarkByTeacher/{studentUserName}/{subjectName}")
	public List<String> getSubjectMarkByTeacher(@PathVariable String studentUserName, @PathVariable String subjectName) {

		TeachingEntity grader=markService.logTeacherTeachingSubjectToStudent(studentUserName, subjectName);
		List<MarkEntity> marks=markRepository.findByStudentUserNameAndGrader(studentUserName,grader);
		List<String> marksToStudent=new  ArrayList<String>();
		for(MarkEntity mark:marks) {
			String markToStudent=mark.toStudentString();
			marksToStudent.add(markToStudent);
		}	
		logger.info("Profesor " + userService.getLoggedUser() + " looked at the grades for student " + studentUserName
				+ " from subject " + subjectName + ".");
		return marksToStudent;
				
	}	
	
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, path = "/getSubjectInClassMarkByTeacher/{subjectName}/{grade}/{sign}")
	public List<String> getSubjectMarkInClassByTeacher( @PathVariable String subjectName,@PathVariable Integer grade,@PathVariable Integer sign) {

		TeachingEntity grader=markService.logTeacherTeachingSubjectToClass(subjectName, grade, sign);
		List<MarkEntity> marks=markRepository.findByGrader(grader);
		List<String> marksToClass=new  ArrayList<String>();
		for(MarkEntity mark:marks) {
			String markToStudent=mark.toStudentString();
			marksToClass.add(markToStudent);
		}	
		logger.info("Profesor " + userService.getLoggedUser() + " looked at the grades for class " + grade+"/"+sign
				+ " from subject " + subjectName + ".");
		return marksToClass;
				
	}	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "deleteMark/{id}")
	public MarkEntity deleteMark(@PathVariable Integer id) {
		if (!markRepository.existsById(id)) 			
			return null;
		MarkEntity mark = markRepository.findById(id).get();
		markRepository.delete(mark);
		logger.info("Mark with ID= " + mark.getId() + " deleted by admin."); 
		return mark;
	}
	
//  ******* ZAKLJUCNA OCENA UCENIKU OD STRANE PROFESORA *******

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.POST, path = "/addFinalMark/{studentName}/{subject}")
	public ResponseEntity<?> addFinalMarkToStudent(@PathVariable String studentName,@PathVariable String subject) {
		
		MarkEntity mark = markService.addFinalMark(studentName,subject);
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}
				
		

		
		
		
		
}

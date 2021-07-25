package com.iktpreobuka.es_dnevnik.controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.es_dnevnik.entities.MarkEntity;
import com.iktpreobuka.es_dnevnik.entities.StudentEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.MarkDTO;
import com.iktpreobuka.es_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.es_dnevnik.repositories.StudentRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeachingRepository;
import com.iktpreobuka.es_dnevnik.services.UserService;

@RestController
public class MarkController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TeachingRepository teachingRepository;

	@Autowired
	private MarkRepository markRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private UserService userService;

//  ******* OCENJIVANJE UCENIKA  *******
// dodati proveru da li prof predaje tom odeljenju!!!!
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.POST, path = "/addMark")
	public ResponseEntity<?> addMarkToStudent(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);

		TeacherEntity logTeacher = teacherRepository.findByUserName(userService.getLoggedUser());
		
		if (!studentRepository.existsByUserName(newMark.getStudentUserName())) {
			logger.info("Student doesn't exists!");
			return new ResponseEntity<>("Student doesn't exists", HttpStatus.BAD_REQUEST);
		}
		StudentEntity student=studentRepository.findByUserName(newMark.getStudentUserName());
		
		// if(!teachingRepository.existsByTeacherSubjectSubjectNameAndTeacherSubjectTeacherUserName(newMark.getSubject(), userService.getLoggedUser()))
				// return new ResponseEntity<>("Student doesn't attend that subject",HttpStatus.BAD_REQUEST);
		
		if (!subjectRepository.existsByNameAndClassGroup(newMark.getSubject(), logTeacher.getClassGroup())) {
			logger.info("Subject doesn't exists!");
			return new ResponseEntity<>("Subject doesn't exists", HttpStatus.BAD_REQUEST);
		}
		SubjectEntity sub = subjectRepository.findByNameAndClassGroup(newMark.getSubject(), logTeacher.getClassGroup());
		
		if (!teachingRepository.existsByTeacherSubjectSubjectAndTeacherSubjectTeacher(sub, logTeacher)) {
			logger.info("Student doesn't attend that subject!");
			return new ResponseEntity<>("Student doesn't attend that subject", HttpStatus.BAD_REQUEST);
		}
		MarkEntity mark = new MarkEntity();
		mark.setStudent(student);
		mark.setGrader(teachingRepository.findByTeacherSubjectSubjectAndTeacherSubjectTeacher(sub, logTeacher));
		mark.setMark(newMark.getMark());
		mark.setDescription(newMark.getDescription());
		if (newMark.getDate() != null)
			mark.setDate(newMark.getDate());
		else
			mark.setDate(LocalDate.now());
		mark.setSemester(student.getClassLevel().getClassInGrade().getSemester());		
		markRepository.save(mark);
		logger.info("Mark added!");
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}

//  ******* DAVANJE OCENA UCENIKU OD STRANE ADMINISTRATORA *******
// ispraviti!!!!!!!!
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, path = "/addMarkByAdmin")
	public ResponseEntity<?> addMarkByAdmin(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);

		if (!studentRepository.existsByUserName(newMark.getStudentUserName()))
			return new ResponseEntity<>("Student doesn't exists", HttpStatus.BAD_REQUEST);

		if (!teachingRepository.existsByTeacherSubjectSubjectName(newMark.getSubject()))
			return new ResponseEntity<>("Student doesn't attend that subject", HttpStatus.BAD_REQUEST);

		MarkEntity mark = new MarkEntity();
		mark.setStudent(studentRepository.findByUserName(newMark.getStudentUserName()));
		mark.setGrader(teachingRepository.findByTeacherSubjectSubjectName(newMark.getSubject()));
		mark.setMark(newMark.getMark());
		mark.setDescription("Correction By Admin");
		if (newMark.getDate() != null)
			mark.setDate(newMark.getDate());
		else
			mark.setDate(LocalDate.now());
		mark.setSemester(teachingRepository.findByTeacherSubjectSubjectName(newMark.getSubject()).getTeachToClass()
				.getClassInGrade().getSemester());
		markRepository.save(mark);
		return new ResponseEntity<>(mark, HttpStatus.OK);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));

	}

}

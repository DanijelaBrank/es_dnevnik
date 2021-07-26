package com.iktpreobuka.es_dnevnik.services;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.MarkEntity;
import com.iktpreobuka.es_dnevnik.entities.StudentEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.MarkDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.es_dnevnik.repositories.StudentRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeachingRepository;

@Service
public class MarkServiceImpl implements MarkService {

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
	@Autowired
	private EmailService emailService;

	@Override
	public MarkEntity addMark(MarkDTO newMark) {
		
		TeacherEntity logTeacher = teacherRepository.findByUserName(userService.getLoggedUser());

		if (!studentRepository.existsByUserName(newMark.getStudentUserName())) {
			logger.info("Student " + newMark.getStudentUserName() + " doesn't exists!");
			throw new ResourceNotFoundException("Student not found");
		}
		StudentEntity student = studentRepository.findByUserName(newMark.getStudentUserName());

		if (!subjectRepository.existsByNameAndClassGroup(newMark.getSubject(), logTeacher.getClassGroup())) {
			logger.info("Subject doesn't exists!");
			throw new ResourceNotFoundException("Subject not found");
		}
		SubjectEntity sub = subjectRepository.findByNameAndClassGroup(newMark.getSubject(), logTeacher.getClassGroup());

		if (!teachingRepository.existsByTeacherSubjectSubjectAndTeacherSubjectTeacherAndTeachToClassStudents(sub,
				logTeacher, student)) {
			logger.info("Logged-in teacher doesn't teach that subject to that student!");
			throw new ResourceNotFoundException("You don't teach that subject to that student!");
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
		String mailTo = student.getParent().getEmail();
		String mailSubject = "New mark";
		String mailText = "Student " + student.getLastName() + " " + student.getName() + " from the subject "
				+ sub.getName() + " received a grade " + mark.getMark() + " on the " + mark.getDescription()
				+ " from the teacher " + logTeacher.getLastName();
		emailService.sendSimpleEmailMessage(mailTo, mailSubject, mailText);
		logger.info("Mail has been sent!");
		return mark;
	}

}

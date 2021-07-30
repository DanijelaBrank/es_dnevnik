package com.iktpreobuka.es_dnevnik.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.ClassEntity;
import com.iktpreobuka.es_dnevnik.entities.StudentEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectClassDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ObjectAlreadyExistsException;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.ClassRepository;
import com.iktpreobuka.es_dnevnik.repositories.StudentRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectInGradeRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeachingRepository;

///  ****************nije zavrseno ************

@Service
public class ClassServiceImpl implements ClassService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private TeachingRepository teachingRepository;

	@Autowired
	private SubjectInGradeRepository subjectInGradeRepository;

	@Autowired
	private StudentRepository studentRepository;
	

	@Override
	public TeachingEntity addTeachingToClass(TeacherSubjectClassDTO newSubjectTeacherToClass) {

		if (!teacherSubjectRepository.existsByTeacherUserNameAndSubjectNameAndSubjectClassGroup(
				newSubjectTeacherToClass.getUserName(), newSubjectTeacherToClass.getSubject(),
				newSubjectTeacherToClass.getClassGroup())) {
			String message = "There is no professor " + newSubjectTeacherToClass.getUserName() + " who teaches subject "
					+ newSubjectTeacherToClass.getSubject();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}

		if (!classRepository.existsByClassInGradeGradeAndSign(newSubjectTeacherToClass.getGrade(),
				newSubjectTeacherToClass.getSign())) {
			String message = "There is no grade with sign " + newSubjectTeacherToClass.getGrade() + "/"
					+ newSubjectTeacherToClass.getSign();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}

		if (!subjectInGradeRepository.existsBySubjectGradeNameAndGradeSabGrade(newSubjectTeacherToClass.getSubject(),
				newSubjectTeacherToClass.getGrade())) {
			String message = "There is no subject " + newSubjectTeacherToClass.getSubject() + " in "
					+ newSubjectTeacherToClass.getGrade() + " grade.";
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}

		TeacherSubjectEntity tsAdd = teacherSubjectRepository.findByTeacherUserNameAndSubjectNameAndSubjectClassGroup(
				newSubjectTeacherToClass.getUserName(), newSubjectTeacherToClass.getSubject(),
				newSubjectTeacherToClass.getClassGroup());

		ClassEntity classToAdd = classRepository.findByClassInGradeGradeAndSign(newSubjectTeacherToClass.getGrade(),
				newSubjectTeacherToClass.getSign());
		
		TeachingEntity teachingToClass = new TeachingEntity();
		teachingToClass.setTeacherSubject(tsAdd);
		teachingToClass.setTeachToClass(classToAdd);
		
		if(teachingRepository.existsByTeacherSubjectAndTeachToClass(tsAdd,classToAdd)) {
			logger.info("Admin try to add subject-teacher to class wich exists.");
			throw new ObjectAlreadyExistsException("That subject-teacher exists in this class.");
		}
		logger.info("That subject-teacher added to class.");
		teachingRepository.save(teachingToClass);
		return teachingToClass;

	}

	@Override
	public StudentEntity classToStudent(String studentUserName, Integer grade, Integer sign) {
		if (!studentRepository.existsByUserName(studentUserName)) {
			String message = "There is no student " + studentUserName + ".";
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}

		ClassEntity clazz = classRepository.findByClassInGradeGradeAndSign(grade, sign);
		if (clazz == null) {
			String message = "There is no grade with sign " + grade + "/" + sign;
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}
		String message;
		StudentEntity student = studentRepository.findByUserName(studentUserName);
//		if ((student.getClassLevel().getClassInGrade().getGrade() != null)
//				&& (student.getClassLevel().getSign() != null))
//			message = "Student " + studentUserName + " was on grade with sign "
//					+ student.getClassLevel().getClassInGrade().getGrade() + "/" + student.getClassLevel().getSign()
//					+ ", now is on grade with sign " + grade + "/" + sign;
//
//		else
			message = "Student " + studentUserName + " is added in grade with sign " + grade + "/" + sign;
		student.setClassLevel(clazz);
		logger.info(message);
		studentRepository.save(student);
		return student;

	}

}

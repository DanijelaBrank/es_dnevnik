package com.iktpreobuka.es_dnevnik.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.ClassEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectClassDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.ClassRepository;
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
	
	@Override
	public TeachingEntity addTeachingToClass(TeacherSubjectClassDTO newSubjectTeacherToClass) {
	
		if(!teacherSubjectRepository.existsByTeacherUserNameAndSubjectNameAndSubjectClassGroup(newSubjectTeacherToClass.getUserName(),
				newSubjectTeacherToClass.getSubject(), newSubjectTeacherToClass.getClassGroup())) {
			String message="There is no professor "+newSubjectTeacherToClass.getUserName()+" who teaches subject "+newSubjectTeacherToClass.getSubject();
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}
		
		if (!classRepository.existsByClassInGradeGradeAndSign(newSubjectTeacherToClass.getGrade(),
				newSubjectTeacherToClass.getSign())) {
			String message="There is no grade with sign "+newSubjectTeacherToClass.getGrade()+"/"+ newSubjectTeacherToClass.getSign(); 
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}
		
		if (!subjectInGradeRepository.existsBySubjectGradeNameAndGradeSabGrade(newSubjectTeacherToClass.getSubject(),
				newSubjectTeacherToClass.getGrade()))  {
			String message="There is no subject "+newSubjectTeacherToClass.getSubject()+" in "+newSubjectTeacherToClass.getGrade()+" grade."; 
			logger.info(message);
			throw new ResourceNotFoundException(message);
		}
		
		TeacherSubjectEntity tsAdd=teacherSubjectRepository.findByTeacherUserNameAndSubjectNameAndSubjectClassGroup(newSubjectTeacherToClass.getUserName(),
				newSubjectTeacherToClass.getSubject(),newSubjectTeacherToClass.getClassGroup());
					
		ClassEntity classToAdd=classRepository.findByClassInGradeGradeAndSign(newSubjectTeacherToClass.getGrade(), newSubjectTeacherToClass.getSign());

		TeachingEntity teachingToClass = new TeachingEntity();
		teachingToClass.setTeacherSubject(tsAdd);   
		teachingToClass.setTeachToClass(classToAdd);
		
		teachingRepository.save(teachingToClass);
		return teachingToClass;	
		
	}

}

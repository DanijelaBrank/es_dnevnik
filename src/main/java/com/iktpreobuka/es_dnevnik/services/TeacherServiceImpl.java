package com.iktpreobuka.es_dnevnik.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ObjectAlreadyExistsException;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherSubjectRepository;


@Service
public class TeacherServiceImpl implements TeacherService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;

	@Override
	public TeacherSubjectEntity addSubjectToTeacher(TeacherSubjectDTO newTeacherSubject) { 
				
		if (!subjectRepository.existsByNameAndClassGroup(newTeacherSubject.getSubject(),
				newTeacherSubject.getClassGroup())) {
			logger.info("Subject "+ newTeacherSubject.getSubject()+" not found in "+newTeacherSubject.getClassGroup());
			throw new ResourceNotFoundException("Subject "+ newTeacherSubject.getSubject()+" not found in "+newTeacherSubject.getClassGroup());
		}
		if (!teacherRepository.existsByUserNameAndClassGroup(newTeacherSubject.getUserName(),
				newTeacherSubject.getClassGroup())) {			
			logger.info("Teacher "+ newTeacherSubject.getUserName()+" not found in "+newTeacherSubject.getClassGroup());
			throw new ResourceNotFoundException("Teacher "+ newTeacherSubject.getUserName()+" not found in "+newTeacherSubject.getClassGroup());
		}
		
		if(teacherSubjectRepository.existsBySubjectNameAndTeacherUserName(newTeacherSubject.getSubject(),
				newTeacherSubject.getUserName())) {						
			logger.info("Admin tried to add subject "+newTeacherSubject.getSubject()+" to teacher "+
				newTeacherSubject.getUserName() +" who already teaches that subject.");
			throw new ObjectAlreadyExistsException("The teacher "+newTeacherSubject.getUserName() +" already teaches subject " +newTeacherSubject.getSubject());}
			
		SubjectEntity subjectForAdd = subjectRepository.findByNameAndClassGroup(newTeacherSubject.getSubject(),
				newTeacherSubject.getClassGroup());
		
		TeacherEntity teacherForAdd = teacherRepository.findByUserNameAndClassGroup(newTeacherSubject.getUserName(),
				newTeacherSubject.getClassGroup());

		TeacherSubjectEntity teacherWithSubject = new TeacherSubjectEntity();
		teacherWithSubject.setSubject(subjectForAdd);
		teacherWithSubject.setTeacher(teacherForAdd);
		teacherSubjectRepository.save(teacherWithSubject);
		return teacherWithSubject;		
			
	}

}

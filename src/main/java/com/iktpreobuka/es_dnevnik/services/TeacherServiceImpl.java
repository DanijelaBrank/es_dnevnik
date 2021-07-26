package com.iktpreobuka.es_dnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherSubjectRepository;


@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;

	@Override
	public TeacherSubjectEntity addSubjectToTeacher(TeacherSubjectDTO newTeacherSubject) { 
				
		if (!subjectRepository.existsByNameAndClassGroup(newTeacherSubject.getSubject(),
				newTeacherSubject.getClassGroup()))
			throw new ResourceNotFoundException("Subject not found");
	//		return null;
		if (!teacherRepository.existsByUserNameAndClassGroup(newTeacherSubject.getUserName(),
				newTeacherSubject.getClassGroup()))
			throw new ResourceNotFoundException("Teacher not found");
	//		return null;
		
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

package com.iktpreobuka.es_dnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.ClassEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectClassDTO;
import com.iktpreobuka.es_dnevnik.repositories.ClassRepository;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.es_dnevnik.repositories.TeachingRepository;

///  ****************nije zavrseno ************

@Service
public class ClassServiceImpl implements ClassService {
	
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private TeachingRepository teachingRepository;

	@Override
	public TeachingEntity addTeachingToClass(TeacherSubjectClassDTO newSubjectTeacherToClass) {

		if(!teacherSubjectRepository.existsByTeacherUserNameAndSubjectNameAndSubjectClassGroup(newSubjectTeacherToClass.getUserName(),
				newSubjectTeacherToClass.getSubject(), newSubjectTeacherToClass.getClassGroup()))
			return null;
		
		if (!classRepository.existsByClassInGradeGradeAndSign(newSubjectTeacherToClass.getGrade(),
				newSubjectTeacherToClass.getSign()))     
			return null;
		
		
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

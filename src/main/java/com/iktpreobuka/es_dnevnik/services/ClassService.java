package com.iktpreobuka.es_dnevnik.services;

import com.iktpreobuka.es_dnevnik.entities.StudentEntity;
import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectClassDTO;

public interface ClassService {
	TeachingEntity addTeachingToClass(TeacherSubjectClassDTO newSubjectTeacherToClass);
	StudentEntity classToStudent(String studentUserName, Integer grade, Integer sign); 
}

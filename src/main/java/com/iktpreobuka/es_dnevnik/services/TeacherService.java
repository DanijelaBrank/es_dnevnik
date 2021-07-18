package com.iktpreobuka.es_dnevnik.services;

import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectDTO;

public interface TeacherService {
	TeacherSubjectEntity addSubjectToTeacher(TeacherSubjectDTO teacherSubject);
}

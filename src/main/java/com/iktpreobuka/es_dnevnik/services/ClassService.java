package com.iktpreobuka.es_dnevnik.services;

import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.ClassDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectClassDTO;
import com.iktpreobuka.es_dnevnik.entities.dto.TeacherSubjectDTO;

public interface ClassService {
	TeachingEntity addTeachingToClass(TeacherSubjectClassDTO newSubjectTeacherToClass);
}

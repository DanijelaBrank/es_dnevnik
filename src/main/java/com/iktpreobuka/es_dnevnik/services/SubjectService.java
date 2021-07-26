package com.iktpreobuka.es_dnevnik.services;

import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectDTO;

public interface SubjectService {
	public SubjectEntity addNewSubject(SubjectDTO newSubject);
}

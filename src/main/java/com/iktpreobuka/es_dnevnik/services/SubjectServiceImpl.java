package com.iktpreobuka.es_dnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Override
	public SubjectEntity addNewSubject(SubjectDTO newSubject) {
		SubjectEntity subject = new SubjectEntity();
		subject.setName(newSubject.getName());
		subject.setClassGroup(newSubject.getClassGroup());
		subjectRepository.save(subject);
		return subject;
	}

}

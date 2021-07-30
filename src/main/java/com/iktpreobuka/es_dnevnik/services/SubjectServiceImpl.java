package com.iktpreobuka.es_dnevnik.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ObjectAlreadyExistsException;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Override
	public SubjectEntity addNewSubject(SubjectDTO newSubject) {
		if(subjectRepository.existsByNameAndClassGroup(newSubject.getName(),newSubject.getClassGroup())) {
			logger.info("Admin tried to add subject "+newSubject.getName()+" that already exists at "+newSubject.getClassGroup());
			throw new ObjectAlreadyExistsException("Subject "+newSubject.getName()+" already exists at "+newSubject.getClassGroup());}
				
		SubjectEntity subject = new SubjectEntity();
		subject.setName(newSubject.getName());
		subject.setClassGroup(newSubject.getClassGroup());
		subjectRepository.save(subject);
		return subject;
	}

}

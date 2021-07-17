package com.iktpreobuka.es_dnevnik.repositories;

import javax.validation.Valid;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {

	boolean existsByName(String s);

	boolean existsByNameAndClassGroup(String subject, EClassGroup classGroup);

	SubjectEntity findByName(String subject);

	SubjectEntity findByNameAndClassGroup(String subject, EClassGroup classGroup);

}

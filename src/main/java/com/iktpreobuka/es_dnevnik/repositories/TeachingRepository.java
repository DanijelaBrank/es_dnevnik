package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;

public interface TeachingRepository extends CrudRepository<TeachingEntity, Integer> {

	TeachingEntity findByTeacherSubjectSubjectName(String subject);

	boolean existsByTeacherSubjectSubjectName(String subject);

}

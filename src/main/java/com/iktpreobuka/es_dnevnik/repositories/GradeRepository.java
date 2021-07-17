package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.GradeEntity;
import com.iktpreobuka.es_dnevnik.entities.ParentEntity;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer> {
	
	public boolean existsByGrade(Integer grade);
	
	public GradeEntity findByGrade(Integer grade);
	
	
}

package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.ClassEntity;

public interface ClassRepository extends CrudRepository<ClassEntity, Integer> {

//	ClassEntity findByGradeInClassGradeAndSign(Integer grade, Integer Sign);

	ClassEntity findByClassInGradeGradeAndSign(Integer grade, Integer sign);

}

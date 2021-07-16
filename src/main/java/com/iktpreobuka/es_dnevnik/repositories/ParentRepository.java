package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {

	ParentEntity findByUserName(String studentUserName);

}

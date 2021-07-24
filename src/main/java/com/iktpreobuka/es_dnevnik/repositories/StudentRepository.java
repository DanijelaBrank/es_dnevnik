package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

	Boolean existsByUserName(String studentUserName);
	StudentEntity findByUserName(String studentUserName);

}

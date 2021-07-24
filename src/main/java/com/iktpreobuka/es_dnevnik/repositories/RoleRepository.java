package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.RoleEntity;

public interface RoleRepository extends CrudRepository <RoleEntity,Integer>{

	RoleEntity findById(int i);
	
	
}

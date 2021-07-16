package com.iktpreobuka.es_dnevnik.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.RoleEntity;
import com.iktpreobuka.es_dnevnik.entities.UserEntity;

public interface RoleRepository extends CrudRepository <RoleEntity,Integer>{
	
	public Optional<RoleEntity> findById(Integer id);

}

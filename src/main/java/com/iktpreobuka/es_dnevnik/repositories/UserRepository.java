package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.UserEntity;

public interface UserRepository extends CrudRepository <UserEntity,Integer>{
	public UserEntity findByEmail(String email);

}

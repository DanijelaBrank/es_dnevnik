package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository <TeacherEntity,Integer>{

	boolean existsByUserNameAndClassGroup(String userName, EClassGroup classGroup);

	TeacherEntity findByUserNameAndClassGroup(String userName, EClassGroup classGroup);

	TeacherEntity findByUserName(String userName);

	boolean existsByUserName(String userName);

		

}

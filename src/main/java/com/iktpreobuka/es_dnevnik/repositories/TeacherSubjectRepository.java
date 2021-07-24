package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.EClassGroup;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;

public interface TeacherSubjectRepository extends CrudRepository<TeacherSubjectEntity, Integer> {

	TeacherSubjectEntity findByTeacherUserNameAndSubjectNameAndSubjectClassGroup(String userName, String subject,
			EClassGroup classGroup);

	boolean existsByTeacherUserNameAndSubjectNameAndSubjectClassGroup(String userName, String subject,
			EClassGroup classGroup);

}

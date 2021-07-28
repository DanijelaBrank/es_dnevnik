package com.iktpreobuka.es_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.MarkEntity;
import com.iktpreobuka.es_dnevnik.entities.StudentEntity;

public interface MarkRepository extends CrudRepository<MarkEntity, Integer> {

	List<MarkEntity> findByStudent(StudentEntity student);

	List<MarkEntity> findByStudentAndGraderTeacherSubjectSubjectName(StudentEntity student, String subjectName);
	
	
	

}

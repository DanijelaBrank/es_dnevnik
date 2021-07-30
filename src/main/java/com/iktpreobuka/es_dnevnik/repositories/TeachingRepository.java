package com.iktpreobuka.es_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.es_dnevnik.entities.ClassEntity;
import com.iktpreobuka.es_dnevnik.entities.EClassGroup;
import com.iktpreobuka.es_dnevnik.entities.StudentEntity;
import com.iktpreobuka.es_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.es_dnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;

public interface TeachingRepository extends CrudRepository<TeachingEntity, Integer> {

	TeachingEntity findByTeacherSubjectSubjectName(String subject);

	boolean existsByTeacherSubjectSubjectName(String subject);

	boolean existsByTeacherSubjectSubjectNameAndTeacherSubjectTeacherUserName(String subject, String loggedUser);

	TeachingEntity findByTeacherSubjectSubjectNameAndTeacherSubjectTeacherUserName(String subject, String loggedUser);

	boolean existsByTeacherSubjectSubjectNameAndTeacherSubjectSubjectClassGroupAndTeacherSubjectTeacherUserName(
			String subject, EClassGroup string, String loggedUser);

	TeachingEntity findByTeacherSubjectSubjectNameAndTeacherSubjectSubjectClassGroupAndTeacherSubjectTeacherUserName(
			String subject, EClassGroup string, String loggedUser);

	boolean existsByTeacherSubjectSubject(SubjectEntity sub);

	TeachingEntity findByTeacherSubjectSubjectAndTeacherSubjectTeacher(SubjectEntity sub, TeacherEntity logTeacher);

	boolean existsByTeacherSubjectSubjectAndTeacherSubjectTeacher(SubjectEntity sub, TeacherEntity logTeacher);

	boolean existsByTeacherSubjectSubjectAndTeacherSubjectTeacherAndTeachToClassStudents(SubjectEntity sub,
			TeacherEntity logTeacher, StudentEntity student);

	boolean existsByTeacherSubjectSubjectNameAndTeachToClassStudents(String subject, StudentEntity student);

	TeachingEntity findByTeacherSubjectSubjectNameAndTeachToClassStudents(String subject, StudentEntity student);

	TeachingEntity findByTeacherSubjectSubjectAndTeacherSubjectTeacherAndTeachToClassStudents(SubjectEntity subject,
			TeacherEntity teacher, StudentEntity student);

	boolean existsByTeacherSubjectSubjectAndTeacherSubjectTeacherAndTeachToClass(SubjectEntity sub,
			TeacherEntity logTeacher, ClassEntity clazz);

	TeachingEntity findByTeacherSubjectSubjectAndTeacherSubjectTeacherAndTeachToClass(SubjectEntity sub,
			TeacherEntity logTeacher, ClassEntity clazz);

	boolean existsByTeacherSubjectAndTeachToClass(TeacherSubjectEntity tsAdd, ClassEntity classToAdd);

}

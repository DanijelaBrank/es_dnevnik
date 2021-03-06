package com.iktpreobuka.es_dnevnik.services;

import java.util.List;

import com.iktpreobuka.es_dnevnik.entities.MarkEntity;
import com.iktpreobuka.es_dnevnik.entities.StudentEntity;
import com.iktpreobuka.es_dnevnik.entities.TeachingEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.MarkDTO;

public interface MarkService {
	public MarkEntity addMark(MarkDTO newMark);
	public MarkEntity addMarkByAdmin(MarkDTO newMark);
	public List<String> findStudentMark(StudentEntity student);
	public List<String> findStudentMark(StudentEntity student, String subjectName); 
	public TeachingEntity logTeacherTeachingSubjectToStudent(String studentUserName, String subjectName);
	TeachingEntity logTeacherTeachingSubjectToClass(String subjectName, Integer grade, Integer sign);
	public MarkEntity addFinalMark(String studentName, String subject); 
}

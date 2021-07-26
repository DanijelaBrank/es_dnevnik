package com.iktpreobuka.es_dnevnik.services;

import com.iktpreobuka.es_dnevnik.entities.MarkEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.MarkDTO;

public interface MarkService {
	public MarkEntity addMark(MarkDTO newMark);
}

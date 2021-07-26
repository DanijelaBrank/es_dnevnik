package com.iktpreobuka.es_dnevnik.services;

import com.iktpreobuka.es_dnevnik.models.EmailObject;

public interface EmailService {
	public void sendSimpleEmailMessage(String to,String subject, String text);
}

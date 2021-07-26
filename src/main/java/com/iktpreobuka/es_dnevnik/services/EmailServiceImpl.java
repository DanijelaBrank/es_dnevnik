package com.iktpreobuka.es_dnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.models.EmailObject;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired 
	JavaMailSender emailSender;
	
	@Override 
	public void sendSimpleEmailMessage(String to,String subject, String text) {
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(text);
		emailSender.send(mail);
	}
	
	

}

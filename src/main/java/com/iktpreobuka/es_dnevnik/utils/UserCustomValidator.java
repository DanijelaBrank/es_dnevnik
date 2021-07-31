package com.iktpreobuka.es_dnevnik.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.es_dnevnik.entities.UserEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.UserDTO;
import com.iktpreobuka.es_dnevnik.exceptions.ResourceNotFoundException;
import com.iktpreobuka.es_dnevnik.repositories.UserRepository;

@Component
public class UserCustomValidator implements Validator {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserDTO user=(UserDTO)target;
		if(user.getPassword()==null)
			throw new NullPointerException("Password must be provided.");
		
		if(!user.getPassword().equals(user.getPasswordConfirm())) {
			errors.reject("400", "Password must match");
		}
		
		if(userRepository.existsByUserName(user.getUserName()))  { 
			errors.reject("400", "UserName must be unique");
		}
		
		// TODO Auto-generated method stub
		
	}
	
	

}

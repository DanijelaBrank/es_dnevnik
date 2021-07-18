package com.iktpreobuka.es_dnevnik.services;

import org.springframework.stereotype.Service;

import com.iktpreobuka.es_dnevnik.entities.UserEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.UserDTO;
import com.iktpreobuka.es_dnevnik.utils.Encryption;

@Service
public class UserServiceImpl implements UserService {


	@Override
	public UserEntity addUser(UserDTO newUser) {
		
		UserEntity user = new UserEntity();
		user.setName(newUser.getName());
		user.setLastName(newUser.getLastName());
		user.setUserName(newUser.getUserName());
		user.setEmail(newUser.getEmail());
		user.setPassword(Encryption.getPassEncoded(newUser.getPassword()));
		return user;
	}

}

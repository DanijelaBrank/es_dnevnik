package com.iktpreobuka.es_dnevnik.services;

import com.iktpreobuka.es_dnevnik.entities.UserEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.UserDTO;

public interface UserService {
	
	public UserEntity addUser(UserDTO newUser);

}

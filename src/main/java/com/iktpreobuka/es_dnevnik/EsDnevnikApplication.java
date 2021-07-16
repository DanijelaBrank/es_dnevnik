package com.iktpreobuka.es_dnevnik;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.iktpreobuka.es_dnevnik.entities.RoleEntity;
import com.iktpreobuka.es_dnevnik.entities.UserEntity;
import com.iktpreobuka.es_dnevnik.repositories.RoleRepository;
import com.iktpreobuka.es_dnevnik.repositories.UserRepository;
import com.iktpreobuka.es_dnevnik.utils.Encryption;

@SpringBootApplication
public class EsDnevnikApplication {
	
	
	@Autowired UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(EsDnevnikApplication.class, args);
		
	}
	
	@Bean
	InitializingBean sendDatabase() {
	    return () -> {
	
	if(!userRepository.existsByUserName("superAdmin")) 
        userRepository.save(new UserEntity("superAdmin@gmail.com","superAdmin",Encryption.getPassEncoded("aaa1"),"Danijela","Brankovic",roleRepository.findById(1).get()));
	     
	      };

	}
}

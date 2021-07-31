package com.iktpreobuka.es_dnevnik.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.es_dnevnik.entities.UserEntity;
//import com.iktpreobuka.es_dnevnik.entities.UserEntity;
import com.iktpreobuka.es_dnevnik.entities.dto.UserTokenDTO;
import com.iktpreobuka.es_dnevnik.repositories.UserRepository;
import com.iktpreobuka.es_dnevnik.utils.Encryption;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Value("${spring.security.secret-key}")
	private String secretKey;

	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	
//  ****** LOGOVANJE KORISNIKA  *********

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String pwd) {
		UserEntity userEntity = userRepository.findByUserName(userName);
		if (userEntity != null && Encryption.validatePassword(pwd, userEntity.getPassword())) {
			String token = getJWTToken(userEntity);
			UserTokenDTO user = new UserTokenDTO();
			user.setUser(userName);
			user.setToken(token);
			logger.info("Successful login by "+userName);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		logger.info("Wrong login credentials!");
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}

	private String getJWTToken(UserEntity userEntity) {

//		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//				.commaSeparatedStringToAuthorityList(userEntity.getRole().getName);
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getRole().getName());
		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getUserName())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenDuration))
				.signWith(SignatureAlgorithm.HS512, this.secretKey.getBytes()).compact();
		return "Bearer " + token;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "deleteUser/{id}")
	public UserEntity deleteUser(@PathVariable Integer id) {
		if (!userRepository.existsById(id))
			return null;
		UserEntity user = userRepository.findById(id).get();
		userRepository.delete(user);
		return user;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "changeUser/{userName}")
	public UserEntity changeUser(@RequestBody UserEntity newUser, @PathVariable String userName) {
		if (!userRepository.existsByUserName(userName))
			return null;
		UserEntity user = userRepository.findByUserName(userName);
		if (newUser.getName() != null)
			user.setName(newUser.getName());
		if (newUser.getLastName() != null)
			user.setLastName(newUser.getLastName());
		if (newUser.getUserName() != null)
			user.setUserName(newUser.getUserName());
		if (newUser.getEmail() != null)
			user.setEmail(newUser.getEmail());
		return userRepository.save(user);
	}
	
	

}

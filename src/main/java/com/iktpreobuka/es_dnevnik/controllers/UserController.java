package com.iktpreobuka.es_dnevnik.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

	@Autowired
	private UserRepository userRepository;

	@Value("${spring.security.secret-key}")
	private String secretKey;

	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String pwd) {
		UserEntity userEntity = userRepository.findByUserName(userName);
		if (userEntity != null && Encryption.validatePassword(pwd, userEntity.getPassword())) {
			String token = getJWTToken(userEntity);
			UserTokenDTO user = new UserTokenDTO();
			user.setUser(userName);
			user.setToken(token);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
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
	@RequestMapping("/test")
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}

}

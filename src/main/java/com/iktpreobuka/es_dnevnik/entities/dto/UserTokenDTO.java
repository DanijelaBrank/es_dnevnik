package com.iktpreobuka.es_dnevnik.entities.dto;

public class UserTokenDTO {

	private String user;
	private String token;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserTokenDTO(String user, String token) {
		super();
		this.user = user;
		this.token = token;
	}
	public UserTokenDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

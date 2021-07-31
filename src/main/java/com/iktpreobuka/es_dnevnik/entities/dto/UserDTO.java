package com.iktpreobuka.es_dnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {
	
	@NotBlank(message = "First name must be not blank or null.")
	@Size(min = 2, max = 30, message = "First name lenght must be string between {min} and {max}.")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Last name must be not blank or null.")
	@Size(min = 2, max = 30, message = "Last name lenght must be string between {min} and {max}.")
	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	@NotBlank(message = "Email must be not blank or null.")
	@Email(message = "Email is not valid.")
	private String email;

	@NotBlank(message = "Username must be not blank or null.")
	@Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.")
	@Column(nullable = false, unique = true)
	private String userName;

	@NotBlank(message = "Password must be not blank or null.")
	@Size(min = 5, max = 10, message = "Password must be between {min} and {max} characters long.")
	@Column(nullable = false)
	private String password;
	
	@NotBlank(message = "Password must be not blank or null.")
	@Size(min = 5, max = 10, message = "Password must be between {min} and {max} characters long.")
	@Column(nullable = false)
	private String passwordConfirm;
	
	@Null
	private Integer phoneNo;
	
	@Null
	private String city;
	
	@Null
	private String street;
	
	@Null
	private Integer number;

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Integer getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Integer phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	

}

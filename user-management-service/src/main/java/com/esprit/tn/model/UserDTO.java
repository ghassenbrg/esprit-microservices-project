package com.esprit.tn.model;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {

	private Long id;

	@Size(max = 255)
	private String firstName;

	@Size(max = 255)
	private String lastName;

	@Size(max = 255)
	private String email;

	@Size(max = 255)
	private String username;

	@Size
	private String role;
	
	@Size(max = 255)
	private String address;

	@Size(max = 255)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

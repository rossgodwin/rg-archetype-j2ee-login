package org.rg.archetype.data.shared;

import java.io.Serializable;

public class UserDTO implements Serializable, ILongId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7959096294571780152L;

	private Long id = -1L;
	
	private String username;
	
	private UserRole role;
	
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

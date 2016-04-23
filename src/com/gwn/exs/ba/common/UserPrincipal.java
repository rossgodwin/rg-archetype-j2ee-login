package com.gwn.exs.ba.common;

import java.security.Principal;

public class UserPrincipal implements Principal {

	private Long userId;
	
	private String username;
	
	private String role;

	public UserPrincipal() {}
	
	public UserPrincipal(Long userId, String username, String role) {
		this.userId = userId;
		this.username = username;
		this.role = role;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getName() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

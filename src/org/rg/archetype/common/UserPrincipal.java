package org.rg.archetype.common;

import java.security.Principal;

import org.rg.archetype.data.shared.UserRole;

public class UserPrincipal implements Principal {

	private Long userId;
	
	private String username;
	
	private UserRole role;

	public UserPrincipal() {}
	
	public UserPrincipal(Long userId, String username, UserRole role) {
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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}

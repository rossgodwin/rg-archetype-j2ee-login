package com.gwn.exs.ba.data.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwn.exs.ba.data.entity.UserTableMetadata;
import com.gwn.exs.ba.data.shared.ILongId;
import com.gwn.exs.ba.data.shared.UserConstants;
import com.gwn.exs.ba.data.shared.UserRole;

@Entity
@Table(name = UserTableMetadata.TABLE_NAME)
public class User implements ILongId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = -1L;
	
	@Column(name = UserTableMetadata.COL_USERNAME, nullable = false, length = UserConstants.MAX_SIZE_EMAIL)
	private String username;
	
	@Column(name = UserTableMetadata.COL_PASSWORD, nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = UserTableMetadata.COL_ROLE, nullable = false)
	private UserRole role;
	
	@Column(name = UserTableMetadata.COL_EMAIL, nullable = false, length = UserConstants.MAX_SIZE_EMAIL)
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

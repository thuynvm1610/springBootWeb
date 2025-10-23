package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "userName", nullable = false, unique = true)
	private String userName;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "fullname")
	private String fullname;
	
	@Column(name = "status", nullable = false)
	private Integer status;
	
	@Column(name = "email")
	private String email;

//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//	private List<UserRoleEntity> userRoleEntities = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "userid", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "roleid", nullable = false))
	private List<RoleEntity> roles = new ArrayList<>();
	
	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public List<UserRoleEntity> getUserRoleEntities() {
//		return userRoleEntities;
//	}
//
//	public void setUserRoleEntities(List<UserRoleEntity> userRoleEntities) {
//		this.userRoleEntities = userRoleEntities;
//	}
	
}

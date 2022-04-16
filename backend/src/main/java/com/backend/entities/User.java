package com.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User extends BasicEntity implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String password;
	private String picture;
	
	@OneToMany(mappedBy = "owner")
	private List<Project> ownerProjects = new ArrayList<Project>();
	
	@ManyToMany(mappedBy = "users")
	private List<Project> projects = new ArrayList<Project>();
	
	@OneToMany(mappedBy = "user")
	private List<Comment> comments = new ArrayList<Comment>();
	
	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@JsonIgnore
	public List<Project> getOwnerProjects() {
		return ownerProjects;
	}
	
	public void setOwnerProjects(List<Project> ownerProjects) {
		this.ownerProjects = ownerProjects;
	}

	@JsonIgnore
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	@JsonIgnore
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities =  new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + "USER"));

		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

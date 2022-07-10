package com.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length = 100, nullable = false)
	private String name;

	@Column(length = 150, nullable = false)
	private String description;

	@Column(nullable = false)
	private Boolean status;

	@ManyToOne(fetch = FetchType.LAZY)
	private User owner;

	@ManyToMany
	@JoinTable(name = "project_user", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users = new ArrayList<User>();

	public Project() {
		this.status = Boolean.FALSE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	public void addUser(User user) {
		user.getProjects().add(this);
		this.users.add(user);
	}
}

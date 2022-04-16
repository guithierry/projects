package com.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 150, nullable = false)
	private String description;

	@Column(name = "status", nullable = false)
	private Boolean status;

	@ManyToOne
	private User owner;
	
	@ManyToMany()
	@JoinTable()
	private List<User> users = new ArrayList<>();

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Todo> todos = new ArrayList<>();
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

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

		users.forEach(user -> {
			user.getProjects().add(this);
		});
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}

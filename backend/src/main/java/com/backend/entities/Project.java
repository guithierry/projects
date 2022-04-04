package com.backend.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "project")
public class Project extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "status", nullable = false)
	private Boolean status;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Todo> todos;

	public Project() {
		this.status = Boolean.FALSE;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
}

package com.backend.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "todo")
public class Todo extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 150, nullable = false)
	private String description;

	private Status status;
	
	@ManyToOne()
	@JoinColumn(name = "projectId", nullable = false)
	private Project project;

	public Todo() {
		this.status = Status.TODO;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@JsonIgnore
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}

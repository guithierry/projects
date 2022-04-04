package com.backend.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "todo")
public class Todo extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Status status;

	@ManyToOne()
	@JoinColumn(name = "projectId", nullable = false)
	private Project project;

	public Todo() {
		this.status = Status.TODO;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return this.getName() + " " + this.getProject().getName();
	}
}

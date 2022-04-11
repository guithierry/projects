package com.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comment")
public class Comment extends BasicEntity {

	@Column(name = "description", nullable = false)
	private String description;

	// TODO: Add user
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Project project;

	public Comment() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}

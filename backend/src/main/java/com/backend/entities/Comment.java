package com.backend.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comment")
public class Comment extends BasicEntity {

	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
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
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	@Override
	public String toString() {
		return super.getId() + " " + this.description;
	}
}

package com.backend.entities;

public enum Status {

	TODO("todo"),
	DOING("doing"),
	DONE("done");
	
	private String status;
	
	private Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static Status fromString(String string) {
		
		for (Status status: Status.values()) {
			if (status.getStatus().toLowerCase().equals(string.toLowerCase())) {
				return status;
			}
		}
		
		throw new IllegalArgumentException();
	}
}

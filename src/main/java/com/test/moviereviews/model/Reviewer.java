package com.test.moviereviews.model;

public class Reviewer {
	
	public enum Type {
		user, critic, expert, admin
	}
	
	private int id;
	private Type type;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

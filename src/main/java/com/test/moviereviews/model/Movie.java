package com.test.moviereviews.model;

import java.util.Date;
import java.util.List;

public class Movie {
	
	public enum Genre{
		comedy,drama,action,romance
	}
	
	private int id;
	private String name;
	private List<Genre> genres;
	private Date year;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	public Date getYear() {
		return year;
	}
	public void setYear(Date year) {
		this.year = year;
	}
	
	

}

package com.test.moviereviews.exception;

public class MovieNotExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The Movie is not valid";
	}
	

}

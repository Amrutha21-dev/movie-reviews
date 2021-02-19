package com.test.moviereviews.exception;

public class MovieNotReleasedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The movie is not released yet";
	}
}

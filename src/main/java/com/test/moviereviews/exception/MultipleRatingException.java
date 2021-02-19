package com.test.moviereviews.exception;

public class MultipleRatingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Multiple rating is not allowed";
	}

}

package com.test.moviereviews.exception;

public class RatingNotValidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Rating is not valid";
	}

}

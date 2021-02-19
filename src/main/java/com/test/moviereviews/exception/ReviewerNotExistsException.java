package com.test.moviereviews.exception;

public class ReviewerNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The reviewer is not valid";
	}
	
	

}

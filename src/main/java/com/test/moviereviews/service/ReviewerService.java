package com.test.moviereviews.service;

import java.util.ArrayList;
import java.util.List;

import com.test.moviereviews.exception.ReviewerNotExistsException;
import com.test.moviereviews.model.Reviewer;
import com.test.moviereviews.model.Reviewer.Type;

public class ReviewerService {
	
	static ReviewService reviewService = new ReviewService();
	
	//Since we need in-memory data to be stored
	private	static List<Reviewer> reviewers = new ArrayList<>();
	
	//Acts as DAO getter
	public static List<Reviewer> getReviewers() {
		return reviewers;
	}

	public void addReviewer(String name) {
		Reviewer reviewer = new Reviewer();
		reviewer.setId(reviewers.size()+1);
		reviewer.setName(name);
		reviewer.setType(Type.user);
		reviewers.add(reviewer);
		System.out.println("Successfully added " + reviewer.getName());
	}

	public int getId(String reviewerName) {
		try {
			for(Reviewer reviewer:reviewers) {
				if(reviewer.getName().equals(reviewerName)) {
					return reviewer.getId();
				}
			}
			throw new ReviewerNotExistsException();
		}
		catch(ReviewerNotExistsException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public Type getType(int reviewerId) {
		for(Reviewer reviewer:reviewers) {
			if(reviewer.getId() == reviewerId) {
				return reviewer.getType();
			}
		}
		return null;
	}
	
	public String getName(int reviewerId) {
		for(Reviewer reviewer:reviewers) {
			if(reviewer.getId() == reviewerId) {
				return reviewer.getName();
			}
		}
		return null;
	}

	public void checkForUpgrade(int reviewerId) {
		int num = reviewService.getTotalNumberOfMoviesReviewed(reviewerId);
		Type type = Type.user;
		if(num>=9) {
			type = Type.admin;
		}
		else if(num>=6) {
			type = Type.expert;
		}
		else if(num>=3) {
			type = Type.critic;
		}
		for(Reviewer reviewer:reviewers) {
			if(reviewer.getId() == reviewerId) {
				if(!reviewer.getType().equals(type)) {
					System.out.println("Upgraded "+getName(reviewerId)+" to "+type);
				}
				reviewer.setType(type);
				reviewers.set(reviewerId-1, reviewer);
			}
		}
		
	}

}

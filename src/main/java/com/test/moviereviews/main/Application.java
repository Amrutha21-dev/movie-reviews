package com.test.moviereviews.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.test.moviereviews.model.Movie;
import com.test.moviereviews.model.Movie.Genre;
import com.test.moviereviews.service.MovieService;
import com.test.moviereviews.service.ReviewService;
import com.test.moviereviews.service.ReviewerService;

public class Application {
	
	static ReviewService reviewService = new ReviewService();
	static ReviewerService reviewerService = new ReviewerService();
	static MovieService movieService = new MovieService();
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		reviewerService.addReviewer("SRK");
		reviewerService.addReviewer("Salman");
		reviewerService.addReviewer("Deepika");
		System.out.println("Reviewers created");
		List<Genre> temp = new ArrayList<>();
		temp.add(Genre.action);
		temp.add(Genre.comedy);
		Date date = new Date(2006,0,1);
		movieService.addMovie("Don",date,temp);
		temp = new ArrayList<>();
		temp.add(Genre.drama);
		date = new Date(2008,0,1);
		movieService.addMovie("Tiger",date,temp);
		temp = new ArrayList<>();
		temp.add(Genre.comedy);
		date = new Date(2006,0,1);
		movieService.addMovie("Padmaavat",date,temp);
		temp = new ArrayList<>();
		temp.add(Genre.drama);
		date = new Date(2021,2,1);
		movieService.addMovie("Lunchbox",date,temp);
		temp = new ArrayList<>();
		temp.add(Genre.drama);
		date = new Date(2006,0,1);
		movieService.addMovie("Guru" ,date,temp);
		temp = new ArrayList<>();
		temp.add(Genre.romance);
		date = new Date(2006,0,1);
		movieService.addMovie("Metro",date,temp);
		System.out.println("Movies created");
		reviewService.addReview("SRK", "Don", 2);
		reviewService.addReview("SRK","Padmaavat",8);
		reviewService.addReview("Salman", "Don", 5);
		reviewService.addReview("Deepika","Don",9);
		reviewService.addReview("Deepika", "Guru", 6);
		reviewService.addReview("SRK","Don", 10);
		reviewService.addReview("Deepika","Lunchbox",5);
		reviewService.addReview("SRK", "Tiger", 5);
		reviewService.addReview("SRK", "Metro", 7);
		System.out.println("Average rating for Don " + reviewService.
				getAverageReviewScoreForMovie("Don"));
		System.out.println("Average rating for movies in 2006 " + reviewService.
				getAverageReviewScoreInYear(new Date(2006,0,1)));
		System.out.print("Top 1 critic movie for drama genre is ");
		for(Movie movie : reviewService.getTopNCriticMoviesOfGenre(1, Genre.romance)) {
			System.out.println(movie.getName());
		}
	}

}

package com.test.moviereviews.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.test.moviereviews.exception.MovieNotReleasedException;
import com.test.moviereviews.exception.MultipleRatingException;
import com.test.moviereviews.exception.RatingNotValidException;
import com.test.moviereviews.model.Movie;
import com.test.moviereviews.model.Movie.Genre;
import com.test.moviereviews.model.Review;
import com.test.moviereviews.model.Reviewer.Type;

public class ReviewService {
	
	static ReviewerService reviewerService = new ReviewerService();
	static MovieService movieService = new MovieService();
	
	
	public static final int MAXIMUM_RATING = 10;
	public static final int MINIMUM_RATING = 1;
	
	//Since we need in-memory data to be stored
	private static List<Review> reviews = new ArrayList<>();
		
	//Acts as DAO getter
	public static List<Review> getReviews() {
		return reviews;
	}

	public void addReview(String reviewerName, String movieName, int rating) {
		try{
			if(rating >= MINIMUM_RATING && rating <= MAXIMUM_RATING) {
				int reviewerId = reviewerService.getId(reviewerName);
				int movieId = movieService.getId(movieName);
				if(movieId != -1 && reviewerId != -1 && movieService.isMovieRatable(movieId) 
						&& isFirstTimeReview(reviewerId,movieId)) {
					Review review = new Review();
					review.setId(reviews.size()+1);
					review.setReviewerId(reviewerId);
					review.setMovieId(movieId);
					review.setType(reviewerService.getType(reviewerId));
					switch(reviewerService.getType(reviewerId)) {
						case critic : rating *= 2;
										break;
						case expert : rating *= 3;
										break;
						case admin : rating *= 4;
										break;
						default : break;			
					}
					review.setRating(rating);
					reviews.add(review);
					System.out.println("Successfully added review for " + movieName + " by " + reviewerName);
					reviewerService.checkForUpgrade(reviewerId);
				}
			}
			else {
				throw new RatingNotValidException();
			}
		}
		catch(RatingNotValidException e) {
			System.out.println(e.getMessage() + "for " + movieName + " by " + reviewerName);
		}
		catch(MultipleRatingException e) {
			System.out.println(e.getMessage()+ "for " + movieName + " by " + reviewerName);
		}
		catch(MovieNotReleasedException e) {
			System.out.println(e.getMessage()+ "for " + movieName);
		}
	}

	private boolean isFirstTimeReview(int reviewerId, int movieId) {
		for(Review review:reviews) {
			if(review.getReviewerId() == reviewerId && review.getMovieId() == movieId) {
				throw new MultipleRatingException();
			}
		}
		return true;
	}

	public int getTotalNumberOfMoviesReviewed(int reviewerId) {
		int count = 0;
		for(Review review : reviews) {
			if(review.getReviewerId() == reviewerId) {
				count++;
			}
		}
		return count;
	}
	
	//List top n movies by total review score by ‘critics’ in a particular genre.
	public List<Movie> getTopNCriticMoviesOfGenre(int n,Genre genre) {
		List<Movie> movies = MovieService.getMovies();
		Map<Movie,Integer> movieReviews = new HashMap<>();
		for(Movie movie:movies) {
			int totalReview = 0;
			for(Genre temp:movie.getGenres()) {
				if(temp.equals(genre)) {
					for(Review review:getAllCriticReviews()) {
						if(review.getMovieId() == movie.getId()) {
							totalReview+=review.getRating();
							movieReviews.put(movie, totalReview);
						}
					}
				}
			}
		}
		return sortByValues(movieReviews,n);
	}
	
	private List<Movie> sortByValues(Map<Movie, Integer> movieReviews, int n) {
		List<Map.Entry<Movie, Integer> > list = 
	               new LinkedList<Map.Entry<Movie, Integer> >(movieReviews.entrySet()); 
	  
	        // Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<Movie, Integer> >() { 
	            public int compare(Map.Entry<Movie, Integer> o1,  
	                               Map.Entry<Movie, Integer> o2) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        }); 
	        List<Movie> movieList = new ArrayList<>();
	        if(n>list.size()) {
	        	n=list.size();
	        }
	        for(int i=0; i<n; i++) {
	        	movieList.add(list.get(i).getKey());
	        }
	        return movieList;
	}

	private List<Review> getAllCriticReviews() {
		List<Review> criticReviews = new ArrayList<>();
		for(Review review : reviews) {
			if(review.getType().equals(Type.critic)) {
				criticReviews.add(review);
			}
		}
		return criticReviews;
	}

	//Average review score in a particular year of release.
	public float getAverageReviewScoreInYear(Date year) {
		float average = 0;
		int sum = 0;
		int count = 0;
		List<Movie> movies = MovieService.getMovies();
		for(Movie movie : movies) {
			if(movie.getYear().equals(year)) {
				int id=movie.getId();
				for(Review review:getReviews()) {
					if(review.getMovieId() == id) {
						sum+=review.getRating();
						count++;
					}
				}
			}
		}
		average= (float)sum/(float)count;
		return average;
	}
	
	
	// Average review score for a particular movie.
	public float getAverageReviewScoreForMovie(String name) {
		float average = 0;
		int sum = 0;
		int count = 0;
		List<Movie> movies = MovieService.getMovies();
		for(Movie movie : movies) {
			if(movie.getName().equals(name)) {
				int id=movie.getId();
				for(Review review:getReviews()) {
					if(review.getMovieId() == id) {
						sum+=review.getRating();
						count++;
					}
				}
			}
		}
		average= (float)sum/(float)count;
		return average;
	}

}

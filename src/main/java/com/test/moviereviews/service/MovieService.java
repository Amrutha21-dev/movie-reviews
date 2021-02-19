package com.test.moviereviews.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.test.moviereviews.exception.MovieNotExistsException;
import com.test.moviereviews.exception.MovieNotReleasedException;
import com.test.moviereviews.model.Movie;
import com.test.moviereviews.model.Movie.Genre;

public class MovieService {
	
	//Since we need in-memory data to be stored
	private static List<Movie> movies = new ArrayList<>();
	
	//Acts as DAO getter
	public static List<Movie> getMovies() {
		return movies;
	}

	public void addMovie(String name, Date year, List<Genre> genres) {
		Movie movie = new Movie();
		movie.setId(movies.size()+1);
		movie.setName(name);
		movie.setYear(year);
		movie.setGenres(genres);
		movies.add(movie);
		System.out.println("Successfully added " + movie.getName());
	}
	
	public int getId(String movieName) {
		try {
			for(Movie movie:movies) {
				if(movie.getName().equals(movieName)) {
					return movie.getId();
				}
			}
			throw new MovieNotExistsException();
		}
		catch(MovieNotExistsException e) {
			System.out.println(e.getMessage() + " " + movieName);
			return -1;
		}
	}

	public boolean isMovieRatable(int movieId) {
		for(Movie movie:movies) {
			if(movie.getId() == movieId) {
				Date temp = movie.getYear();
				if(temp.after(new Date())) {
					return true;
				}
				else {
					throw new MovieNotReleasedException();
				}
			}
		}
		return false;
	}

	public String getName(int movieId) {
		for(Movie movie:movies) {
			if(movie.getId() == movieId) {
				return movie.getName();
			}
		}
		return null;
	}

}

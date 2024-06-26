package com.makaraya.movapp.data.repository

import android.icu.text.SimpleDateFormat
import com.makaraya.movapp.data.network.MoviesApiService
import com.makaraya.movapp.domain.model.Movie
import com.makaraya.movapp.domain.model.MovieList
import com.makaraya.movapp.domain.repository.MoviesRepository
import retrofit2.Response
import java.io.IOException
import java.util.Locale

class NetworkMoviesRepository(
    private val moviesApiService: MoviesApiService
) : MoviesRepository {
    override suspend fun getTrendingMovies(): List<Movie> =
        getMoviesListFromApiService(moviesApiService::getTrendingMovies)

    override suspend fun getPopularMovies(): List<Movie> =
        getMoviesListFromApiService(moviesApiService::getPopularMovies)

    override suspend fun getUpcomingMovies(): List<Movie> =
        getMoviesListFromApiService(moviesApiService::getUpcomingMovies)

    private suspend fun getMoviesListFromApiService(
        apiServiceFunction: suspend () -> Response<MovieList>
    ): List<Movie> {
        val response = apiServiceFunction()
        if (response.isSuccessful) {
            val body = response.body()
            val movies = body?.results
            movies?.let { return treatMovies(it).filter { movie -> movie.title != null && movie.posterPath != null } }
        }
        throw IOException()
    }

    override suspend fun getMovieDetails(movieId: Int): Movie {
        val response = moviesApiService.getMovieDetails(movieId = movieId)
        if (response.isSuccessful) {
            val movie = response.body()
            movie?.let {
                return treatMovie(movie)
            }
        }
        throw IOException()
    }

    override suspend fun searchMoviesByTerm(searchTerm: String): List<Movie> {
        val response = moviesApiService.searchMoviesByTerm(searchTerm = searchTerm)
        if (response.isSuccessful) {
            val body = response.body()
            val movies = body?.results
            movies?.let { return treatMovies(it).filter { movie -> movie.title != null && movie.posterPath != null } }
        }
        throw IOException()
    }

    private fun treatMovie(movie: Movie): Movie {
        val prefixUrl = "https://image.tmdb.org/t/p/original"

        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val releaseDate = movie.releaseDate

        return Movie(
            movie.id,
            movie.title,
            movie.overview,
            if (!releaseDate.isNullOrEmpty()) outputFormat.format(inputFormat.parse(releaseDate)) else null,
            "${prefixUrl}${movie.posterPath}",
            movie.voteAverage,
            movie.voteCount,
            movie.genres
        )
    }

    private fun treatMovies(movies: List<Movie>): List<Movie> {
        return movies.map { treatMovie(it) }
    }
}
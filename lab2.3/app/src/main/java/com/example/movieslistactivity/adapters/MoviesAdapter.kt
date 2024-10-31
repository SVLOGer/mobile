package com.example.movieslistactivity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieslistactivity.models.Movie
import com.example.movieslistactivity.databinding.ItemMovieBinding

class MoviesAdapter(
    private val movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], onMovieClick)
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onMovieClick: (Movie) -> Unit) {
            binding.movieTitle.text = movie.title
            binding.movieYear.text = movie.year
            binding.movieGenre.text = movie.genre
            Glide.with(binding.root.context).load(movie.imageUrl).into(binding.movieImage)

            binding.root.setOnClickListener { onMovieClick(movie) }
        }
    }
}

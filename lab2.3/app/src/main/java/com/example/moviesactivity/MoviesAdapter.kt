package com.example.moviesactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesactivity.databinding.ItemMovieBinding

class MoviesAdapter(
    private val movies: List<Movie>,
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val binding: ItemMovieBinding, val onClick: (Movie) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private var currentMovie: Movie? = null

        init {
            binding.root.setOnClickListener {
                currentMovie?.let {
                    onClick(it)
                }
            }
        }

        fun bind(movie: Movie) {
            currentMovie = movie
            binding.movieTitle.text = movie.title
            binding.movieRating.text = movie.rating.toString()
            binding.movieDescription.text = movie.description

            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(binding.movieImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size
}

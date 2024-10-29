package com.example.movieslistactivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val rating: Float,
    val description: String,
    val imageUrl: String
) : Parcelable

class MoviesAdapter(
    private val movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        private val ratingTextView: TextView = itemView.findViewById(R.id.movieRating)
        private val imageView: ImageView = itemView.findViewById(R.id.movieImage)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            ratingTextView.text = movie.rating.toString()
            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(imageView)

            itemView.setOnClickListener { onMovieClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}


class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movie = intent.getParcelableExtra<Movie>("movie")

        if (movie != null) {
            findViewById<TextView>(R.id.movieTitle).text = movie.title
            findViewById<TextView>(R.id.movieDescription).text = movie.description
            findViewById<RatingBar>(R.id.ratingBar).rating = movie.rating
            Glide.with(this)
                .load(movie.imageUrl)
                .into(findViewById(R.id.movieImage))
        }

        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            finish()
        }
    }
}


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        val movies = generateMovies() // Метод для генерации списка фильмов
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MoviesAdapter(movies) { movie ->
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }

    private fun generateMovies(): List<Movie> {
        return listOf(
            Movie("Интерстеллар", 4.7f, "Описание фильма...", "https://example.com/interstellar.jpg"),
            // Добавьте больше фильмов
        )
    }
}



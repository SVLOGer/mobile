package com.example.moviesactivity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moviesactivity.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie: Movie? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("movie", Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("movie") as? Movie
        }

        movie?.let {
            binding.movieTitle.text = it.title
            binding.movieDescription.text = it.description
            binding.movieRating.text = it.rating.toString()

            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.movieImage)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}

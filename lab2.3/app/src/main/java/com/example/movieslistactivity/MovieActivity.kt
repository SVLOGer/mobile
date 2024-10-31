package com.example.movieslistactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movieslistactivity.databinding.ActivityMovieBinding
import com.example.movieslistactivity.models.Movie

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra("movie") as? Movie
        movie?.let {
            binding.movieTitle.text = it.title
            binding.movieDescription.text = it.description
            Glide.with(this).load(it.imageUrl).into(binding.movieImage)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}

package com.example.movieslistactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieslistactivity.adapters.MoviesAdapter
import com.example.movieslistactivity.models.Movie
import io.bloco.faker.Faker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val movies = generateMoviesData()
        val adapter = MoviesAdapter(movies) { movie ->
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun generateMoviesData(): List<Movie> {
        val faker = Faker()
        val movies = mutableListOf<Movie>()

        for (i in 1..20) {
            val title = faker.book.title()
            val year = faker.number.between(1980, 2023).toString()
            val genre = faker.book.genre()
            val imageUrl = "https://picsum.photos/100/200"
            val description = faker.lorem.paragraph()

            movies.add(Movie(title, year, genre, imageUrl, description))
        }

        return movies
    }
}

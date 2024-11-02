package com.example.moviesactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import io.bloco.faker.Faker
import com.example.moviesactivity.databinding.ActivityMoviesListBinding

class MoviesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movies = generateFakeMovies(20)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.addItemDecoration(Decoration(resources))
        binding.recyclerView.adapter = MoviesAdapter(movies) { movie ->
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }

    private fun generateFakeMovies(count: Int): List<Movie> {
        val faker = Faker()
        val movies = mutableListOf<Movie>()

        for (i in 1..count) {
            val movie = Movie(
                title = faker.book.title(),
                description = "Sure! Here’s a short, imaginative story for you:\n" +
                        "\n" +
                        "## The Enchanted Forest\n" +
                        "\n" +
                        "Once upon a time, in a land far away, there was a magical forest known as Eldoria. This forest was unlike any other; its trees shimmered with silver leaves, and the air was filled with the sweet scent of blooming flowers that glowed softly in the moonlight.\n" +
                        "\n" +
                        "### The Mysterious Encounter\n" +
                        "\n" +
                        "One day, a curious young girl named Lila ventured into Eldoria. She had heard tales of its wonders but never believed them to be true. As she wandered deeper into the woods, she stumbled upon a hidden glade where the sunlight danced through the branches, creating a kaleidoscope of colors on the ground.\n" +
                        "\n" +
                        "Suddenly, she noticed a small, shimmering creature flitting between the flowers. It was a fairy, no taller than her hand, with wings that sparkled like diamonds. The fairy introduced herself as Seraphina and explained that she was the guardian of the forest.\n" +
                        "\n" +
                        "### A Quest for Harmony\n" +
                        "\n" +
                        "Seraphina revealed that Eldoria was in danger. A dark shadow had begun to creep into the forest, threatening to steal its magic. Lila felt a surge of bravery and offered to help. Together, they embarked on a quest to find the source of the darkness.\n" +
                        "\n" +
                        "Their journey took them through enchanted rivers and over mystical mountains. Along the way, they met talking animals and wise old trees that shared their wisdom and gifts. Each encounter brought Lila closer to understanding her own strength and the importance of harmony in nature.\n" +
                        "\n" +
                        "### The Final Confrontation\n" +
                        "\n" +
                        "Finally, they reached the heart of the darkness—a twisted tree that pulsed with malevolent energy. With Seraphina by her side, Lila summoned all her courage and spoke words of kindness and love. To her surprise, the dark energy began to dissolve, revealing a beautiful spirit trapped within.\n" +
                        "\n" +
                        "The spirit thanked Lila for freeing it and promised to restore balance to Eldoria. With a wave of its hand, the forest bloomed brighter than ever before, and peace returned.\n" +
                        "\n" +
                        "### A Lasting Friendship\n" +
                        "\n" +
                        "Lila returned home with a heart full of joy and memories of her adventure. She knew that she would always carry a piece of Eldoria within her. And every now and then, when she looked up at the stars, she could see Seraphina winking back at her from the enchanted forest.\n" +
                        "\n" +
                        "And so, their friendship blossomed across realms, reminding everyone that courage and kindness can light even the darkest paths.\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "I hope you enjoyed this little tale! If you have any specific themes or topics in mind for another text, feel free to let me know!",
                rating = (1..5).random().toDouble(),
                imageUrl = "https://loremflickr.com/" + (480..1920).random() + "/" + (300..1080).random()
            )
            movies.add(movie)
        }

        return movies
    }
}

package com.example.userprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userprofile.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Установка данных профиля
        binding.name.text = "Константин Западный"
        binding.specialty.text = "Музыкант"
        binding.age.text = "27 лет"
        binding.height.text = "185"
        binding.posts.text = "299"
        binding.followers.text = "299"
        binding.following.text = "1300"
        binding.bio.text = "Йё, урождённый Канье Омари Вест — рэпер, певец, актёр, автор, продюсер и дизайнер из Атланты/Чикаго. Основатель собственного лейбла G.O.O.D. Music."

        // Установка интересов
        binding.chipGroupInterests.addView(createChip("Музыка"))
        binding.chipGroupInterests.addView(createChip("Фитнес"))
        binding.chipGroupInterests.addView(createChip("Плавание"))
        binding.chipGroupInterests.addView(createChip("Программирование"))
        binding.chipGroupInterests.addView(createChip("Кино"))
        binding.chipGroupInterests.addView(createChip("Дизайн"))
    }

    private fun createChip(text: String): Chip {
        val chip = Chip(this)
        chip.text = text
        chip.isClickable = false
        return chip
    }
}

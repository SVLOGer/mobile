package com.example.movieslistactivity.models

import java.io.Serializable

data class Movie(
    val title: String,
    val year: String,
    val genre: String,
    val imageUrl: String,
    val description: String
) : Serializable

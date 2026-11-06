package com.example.artspaceapp

import androidx.annotation.DrawableRes

data class ArtworkUiState(
    val currentArtwork: Int = 1,
    val title: String = "Moonlit Night on Capri",
    val artist: String = "Ivan Aivazovsky",
    val year: String = "1841",
    @DrawableRes val imageRes: Int = R.drawable.image1
)
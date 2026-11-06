package com.example.artspaceapp

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ArtSpaceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArtworkUiState())
    val uiState: StateFlow<ArtworkUiState> = _uiState.asStateFlow()

    private val artworks = listOf(
        ArtworkInfo("Moonlit Night on Capri", "Ivan Aivazovsky",
                    "1841", R.drawable.image1),
        ArtworkInfo("Moonlit Night on the Dnieper", "Arkhip Kuindzhi",
                    "1882", R.drawable.image2),
        ArtworkInfo("Morning in a Pine Forest", "Ivan Shishkin",
                    "1889", R.drawable.image3)
    )

    private data class ArtworkInfo(
        val title: String,
        val artist: String,
        val year: String,
        @DrawableRes val imageRes: Int
    )

    init {
        loadArtwork(1)
    }

    fun onNext() {
        val next = (_uiState.value.currentArtwork % artworks.size) + 1
        loadArtwork(next)
    }

    fun onPrevious() {
        val prev = if (_uiState.value.currentArtwork > 1) {
            _uiState.value.currentArtwork - 1
        } else {
            artworks.size
        }
        loadArtwork(prev)
    }

    private fun loadArtwork(index: Int) {
        val art = artworks[index - 1]
        _uiState.update {
            it.copy(
                currentArtwork = index,
                title = art.title,
                artist = art.artist,
                year = art.year,
                imageRes = art.imageRes
            )
        }
    }
}
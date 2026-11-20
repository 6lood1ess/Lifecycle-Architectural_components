package com.example.dessertclicker

import androidx.annotation.DrawableRes

data class DessertUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    @DrawableRes val currentDessertImageId: Int = R.drawable.cupcake,
    val currentDessertPrice: Int = 5
)
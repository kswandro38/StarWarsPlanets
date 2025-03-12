package com.kalanasarange.starwarsplanets.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(title: String, navigationIcon:  @Composable (() -> Unit) = {}) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = navigationIcon
    )
}
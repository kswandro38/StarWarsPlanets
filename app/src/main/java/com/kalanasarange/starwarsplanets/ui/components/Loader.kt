package com.kalanasarange.starwarsplanets.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays a full-screen loader with a circular progress indicator.
 *
 * @param isVisible Boolean flag to control the visibility of the loader.
 *                  If `true`, the loader is displayed; otherwise, it's hidden.
 *
 * Example Usage:
 * ```
 *  // Show the loader
 *  Loader(isVisible = true)
 *
 *  // Hide the loader
 *  Loader(isVisible = false)
 * ```
 *
 * The loader consists of:
 *  - A white background that fills the entire screen.
 *  - A black circular progress indicator in the center.
 *  - The progress indicator has a fixed size of 30dp.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Loader(isVisible: Boolean){
    if(isVisible){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .testTag("progress_loader")
        ) {
            CircularProgressIndicator(
                color = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
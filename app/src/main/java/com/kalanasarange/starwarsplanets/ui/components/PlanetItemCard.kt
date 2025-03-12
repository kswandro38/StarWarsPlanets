package com.kalanasarange.starwarsplanets.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kalanasarange.starwarsplanets.R
import com.kalanasarange.starwarsplanets.data.model.Planet

@Composable
fun PlantItemCard(planet: Planet, modifier: Modifier = Modifier, navigationCallback: (planetId: Int) -> Unit = {}) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp) // Outer Padding
            .clickable { navigationCallback(planet.id) }
            .padding(4.dp), // Inner Padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = planet.thumbnailUrl,
            contentDescription = "Planet Thumbnail",
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = planet.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = stringResource(R.string.climate, planet.climate),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
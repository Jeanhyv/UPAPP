package com.example.upapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.upapp.R

/**
 * Modelo para hacer los banners dinámicos y escalables a futuro.
 */
data class BannerItem(
    val id: String,
    val imageResId: Int,
    val contentDescription: String,
    val onClick: () -> Unit
)

@Composable
fun CarouselBanner(
    banners: List<BannerItem>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        items(banners, key = { it.id }) { banner ->
            Image(
                painter = painterResource(id = banner.imageResId),
                contentDescription = banner.contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { banner.onClick() }
            )
        }
    }
}
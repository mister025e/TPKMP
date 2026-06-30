package com.example.tpkmp.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tpkmp.domain.model.Weather

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
) {
    LazyRow(modifier = modifier) {
        items(list) { weather ->
            PictureRowItem(
                data = weather,
                modifier = Modifier
                    .width(300.dp)
                    .padding(8.dp),
                onClick = { onPictureClick(weather) }
            )
        }
    }
}

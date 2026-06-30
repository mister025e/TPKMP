package com.example.tpkmp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tpkmp.domain.model.Weather

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(list) { weather ->
            PictureRowItem(
                data = weather,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { onPictureClick(weather) }
            )
        }
    }
}
package com.example.tpkmp.presentation.ui

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.example.tpkmp.domain.model.Weather
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    
    Box(modifier = modifier) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .onPointerEvent(PointerEventType.Scroll) {
                    val delta = it.changes.first().scrollDelta
                    scope.launch {
                        state.scrollBy(delta.y * 50f)
                    }
                }, 
            state = state
        ) {
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
        HorizontalScrollbar(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            adapter = rememberScrollbarAdapter(scrollState = state)
        )
    }
}

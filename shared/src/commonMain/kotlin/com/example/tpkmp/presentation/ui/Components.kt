package com.example.tpkmp.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.tpkmp.domain.model.Weather
import com.example.tpkmp.presentation.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import tpkmp.shared.generated.resources.Res
import tpkmp.shared.generated.resources.error

@Composable
fun MyError(modifier: Modifier = Modifier, errorMessage: String?) {
    AnimatedVisibility(visible = !errorMessage.isNullOrBlank()) {
        Text(
            text = errorMessage ?: "",
            color = MaterialTheme.colorScheme.onError,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error)
                .padding(8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PictureRowItem(modifier: Modifier = Modifier, data: Weather, onClick: () -> Unit = {}) {
    var isExpanded by remember { mutableStateOf(false) }
    val errorPainter = painterResource(Res.drawable.error)

    Row(modifier = modifier.clickable { onClick() }) {
        AsyncImage(
            model = data.icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            error = errorPainter,
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .weight(1f)
        ) {
            Text(text = data.name, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
            Text(
                text = if (isExpanded) data.getResume() else data.getResume().take(20),
                fontSize = 14.sp,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}

@Composable
expect fun WeatherGallery(modifier:Modifier = Modifier, list: List<Weather>, onPictureClick: (Weather)->Unit)

@Preview(showBackground = true)
@Composable
fun MyErrorPreview() {
    AppTheme {
        MyError(errorMessage = "Une erreur est survenue")
    }
}

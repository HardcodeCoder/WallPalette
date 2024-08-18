package com.hardcodecoder.wallpalette.ui.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hardcodecoder.wallpalette.domain.model.Photo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlin.random.Random

@OptIn(FlowPreview::class)
@Composable
fun PaginatedLazyPhotoGrid(
    modifier: Modifier = Modifier,
    data: State<List<Photo>>,
    onReachedEnd: (Int) -> Unit,
    onClick: (Photo) -> Unit
) {
    val listState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Adaptive(160.dp),
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = data.value,
            key = { it.id }
        ) {
            PhotoGridItem(
                photo = it,
                onClick = onClick
            )
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .debounce(500)
            .collectLatest {
                onReachedEnd(it.lastOrNull()?.index ?: 0)
            }
    }
}

@Composable
fun PhotoGridItem(
    modifier: Modifier = Modifier,
    photo: Photo,
    onClick: (Photo) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(photo.aspectRatio)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick(photo)
            }
    ) {
        val placeholder = ColorPainter(Color(photo.color))
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = photo.thumbnailUrl,
            contentDescription = photo.description,
            placeholder = placeholder,
            error = placeholder,
            contentScale = ContentScale.Crop
        )
    }
}


@Preview(
    device = "spec:parent=pixel_8_pro",
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun PaginatedLazyPhotoGridPreview() {
    val data = MutableList(10) {
        Photo(
            Random.nextInt().toString(),
            "",
            Random.nextFloat() * 1.5f,
            "",
            Color(
                red = Random.nextInt(until = 255),
                green = Random.nextInt(until = 255),
                blue = Random.nextInt(until = 255),
                alpha = 255
            ).toArgb(),
            "",
            ""
        )
    }.toList()
    val state = remember {
        mutableStateOf(data)
    }
    PaginatedLazyPhotoGrid(
        data = state,
        onReachedEnd = {}
    ) {
    }
}
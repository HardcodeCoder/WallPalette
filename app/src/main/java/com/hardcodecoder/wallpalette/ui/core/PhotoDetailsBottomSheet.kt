package com.hardcodecoder.wallpalette.ui.core

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hardcodecoder.wallpalette.R
import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.ui.randomPhoto
import com.hardcodecoder.wallpalette.ui.theme.Typography
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailsBottomSheet(
    modifier: Modifier = Modifier,
    photo: Photo,
    dismissRequest: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { dismissRequest() }
    ) {
        PhotoDetails(photo = photo)
    }
}

@Composable
fun PhotoDetails(
    modifier: Modifier = Modifier,
    photo: Photo
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(photo.aspectRatio)
                .clip(RoundedCornerShape(8.dp)),
            model = photo.imageUrl,
            contentDescription = photo.description,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.photo_description, photo.description),
            style = Typography.bodyLarge
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(id = R.string.photo_dimensions, photo.width, photo.height),
            style = Typography.bodyLarge
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(id = R.string.photo_likes, photo.likes),
            style = Typography.bodyLarge
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.photo_color, abs(photo.color).toString(16)),
                style = Typography.bodyLarge
            )
            Canvas(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp),
                onDraw = {
                    drawCircle(color = Color(photo.color))
                }
            )
        }
    }
}


@Preview(
    device = "spec:parent=pixel_8_pro",
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun PhotoDetailsBottomSheetPreview() {
    PhotoDetails(
        photo = randomPhoto()
    )
}
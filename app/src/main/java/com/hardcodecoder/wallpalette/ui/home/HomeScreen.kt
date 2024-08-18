package com.hardcodecoder.wallpalette.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.hardcodecoder.wallpalette.R
import com.hardcodecoder.wallpalette.ui.theme.Typography

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.fillMaxSize(),
        text = stringResource(id = R.string.home),
        style = Typography.headlineMedium
    )
}


@Preview(
    device = "id:pixel_8_pro",
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
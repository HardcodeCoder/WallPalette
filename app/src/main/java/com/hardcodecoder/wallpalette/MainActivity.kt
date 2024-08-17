package com.hardcodecoder.wallpalette

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.hardcodecoder.wallpalette.ui.theme.Typography
import com.hardcodecoder.wallpalette.ui.theme.WallpaletteTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            WallpaletteTheme {
                Greeting(
                    name = "Wallpalette"
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Hello $name!",
            style = Typography.headlineMedium
        )
    }
}

@Preview(
    device = "id:pixel_8_pro",
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun GreetingPreview() {
    WallpaletteTheme {
        Greeting("Wallpalette")
    }
}
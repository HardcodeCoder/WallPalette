package com.hardcodecoder.wallpalette.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.hardcodecoder.wallpalette.domain.model.Photo
import kotlin.random.Random

fun randomPhoto() = Photo(
    id = Random.nextInt(100000).toString(),
    description = "Some random photo for compose preview",
    width = Random.nextInt(1000),
    height = Random.nextInt(800),
    aspectRatio = Random.nextFloat() * 1.5f,
    blurHash = "true blur hash :)",
    Color(
        red = Random.nextInt(until = 255),
        green = Random.nextInt(until = 255),
        blue = Random.nextInt(until = 255),
        alpha = 255
    ).toArgb(),
    "",
    "",
    10
)
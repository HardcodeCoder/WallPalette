package com.hardcodecoder.wallpalette.ui.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.hardcodecoder.wallpalette.BuildConfig
import com.hardcodecoder.wallpalette.R
import com.hardcodecoder.wallpalette.ui.theme.Typography

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.build_date, BuildConfig.BUILD_DATE_TIME),
            style = Typography.bodyLarge
        )
        Text(
            text = "${BuildConfig.BUILD_TYPE}(${BuildConfig.VERSION_NAME})",
            style = Typography.bodyMedium
        )
        Spacer(modifier = Modifier.padding(top = 48.dp))
        Text(
            text = stringResource(id = R.string.settings_footer_text),
            style = Typography.bodySmall
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
fun SettingsScreenPreview() {
    SettingsScreen()
}
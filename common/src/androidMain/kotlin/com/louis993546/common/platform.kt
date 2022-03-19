package com.louis993546.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

actual fun getPlatformName(): String {
    return "Android"
}

@Composable
actual fun painter(): Painter {
    return painterResource(R.drawable.ic_baseline_send_24)
}
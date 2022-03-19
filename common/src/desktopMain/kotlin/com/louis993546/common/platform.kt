package com.louis993546.common

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

actual fun getPlatformName(): String {
    return "Desktop"
}

actual fun painter(): Painter {
    return painterResource("test")
}
package com.louis993546.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun App() {
//    val platformName = getPlatformName()
//
//    Button(onClick = {
//        text = "Hello, ${platformName}"
//    }) {
//        Text(text)
//    }
    PickOne()
}

@Composable
fun PickOne() {
    var text by remember { mutableStateOf("") }

    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true,
            contentPadding = PaddingValues(8.dp),
        ) {
            items(
                items = (0..100).toList(),
                key = { it }
            ) {
                Bubble(text = it.toString())
            }
        }
        Input(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            onTextChange = { text = it }
        ) {
            TODO("add message to list")
        }
    }
}

@Composable
fun Bubble(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier
            .widthIn(min = 48.dp)
            .background(color = Color.Cyan, shape = RoundedCornerShape(4.dp))
            .padding(4.dp),
        contentAlignment = Alignment.CenterEnd, // TODO switch between start and end
    ) {
        Text(text)
    }
}

@Composable
fun Input(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onSumbitClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .heightIn(min = A11y.MIN_CLICKABLE_SIZE)
            .background(color = Color.Green)
            .padding(horizontal = Dimen.SIDE_PADDING),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = modifier.weight(1f),
            value = text,
            onValueChange = onTextChange,
        )

        Image(
            modifier = Modifier.clickable(onClick = onSumbitClick),
            painter = painter(),
            contentDescription = "Send",
        )
    }
}

@Composable
fun PickOneTheme(
    content: @Composable () -> Unit,
) {
    // TODO do something about it
    content()
}

object A11y {
    val MIN_CLICKABLE_SIZE = 48.dp
}

// TODO move this inside theme
object Dimen {
    val SIDE_PADDING = 16.dp
}
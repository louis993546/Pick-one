@file:OptIn(ExperimentalComposeUiApi::class)

package com.louis993546.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val messages = remember { mutableStateListOf<Message>() }
    var machineOutput by remember { mutableStateOf<MachineOutput?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
            contentPadding = PaddingValues(8.dp),
            state = scrollState,
        ) {
            items(
                items = messages,
                key = { it.id }
            ) {
                Bubble(message = it)
            }
        }
        Input(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            onTextChange = { text = it }
        ) {
            messages.add(Message(text = text.trim(), direction = Direction.End, id = messages.size + 1))
            text = ""
            coroutineScope.launch {
                scrollState.scrollToItem(messages.size - 1)
            }

            messages.add(
                Message(
                    id = messages.size + 1,
                    direction = Direction.Start,
                    text = "Thanks for the input",
                )
            )

            machineOutput = theMachine(machineOutput, messages) {
                messages.addAll(it)
            }
        }
    }
}

fun theMachine(
    previousOutput: MachineOutput?,
    messages: List<Message>,
    newMessages: (List<Message>) -> Unit,
): MachineOutput {
    if (previousOutput == null) {
        newMessages(
            listOf(
                Message(id = messages.size + 1, text = "Start", direction = Direction.Start),
            )
        )
    } else {
        // TODO this is the point where TDD makes the most sense
    }


    return MachineOutput(
        contestants = emptyList(),
        phase = MachineOutput.Phase.CollectingContestants,
        lastKnownId = messages.last().id,
    )
}

@Composable
fun Bubble(
    modifier: Modifier = Modifier,
    message: Message,
) {
    Row(
        modifier = modifier,
    ) {
        if (message.direction == Direction.End) {
            Box(modifier = Modifier.weight(1f))
        }
        Box(
            modifier = Modifier.widthIn(min = 48.dp)
                .background(color = Color.Cyan, shape = RoundedCornerShape(4.dp))
                .padding(4.dp),
            contentAlignment = message.direction.toAlignment(),
        ) {
            Text(message.text)
        }
    }

}

@Composable
fun Input(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .heightIn(min = A11y.MIN_CLICKABLE_SIZE)
            .background(color = Color.Green)
            .padding(horizontal = Dimen.SIDE_PADDING),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = modifier.weight(1f).onKeyEvent {
              if (it.key == Key.Enter) {
                  onSubmitClick()
                  true
              } else {
                  false
              }
            },
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
        )

        Image(
            modifier = Modifier.clickable(onClick = onSubmitClick),
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

data class MachineOutput(
    private val contestants: List<String>,
    private val phase: Phase,
    private val lastKnownId: Int,
) {
    enum class Phase {
        CollectingContestants,
        CollectingComparisons
    }
}

data class Message(
    val id: Int,
    val text: String,
    val direction: Direction,
)

enum class Direction {
    Start, End
}

fun Direction.toAlignment(): Alignment = when (this) {
    Direction.Start -> Alignment.CenterStart
    Direction.End -> Alignment.CenterEnd
}
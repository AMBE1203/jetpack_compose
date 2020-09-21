package com.example.layoutbasic.todo

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.layoutbasic.util.generateRandomTodoItem
import kotlin.random.Random

@Composable
fun TodoScreen(
    items: List<TodoItem>,
    currentlyEditing: TodoItem?,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    onStartEdit: (TodoItem) -> Unit,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit
) {
    Column {
        val enableTopSelection = currentlyEditing == null

        TodoItemInputBackground(elevate = enableTopSelection, modifier = Modifier.fillMaxWidth()) {

            if (enableTopSelection) {
                TodoItemEntryInput(onAddItem)

            } else {
                Text(
                    text = "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.gravity(Alignment.CenterVertically).padding(16.dp)
                        .fillMaxWidth()
                )
            }


        }

        LazyColumnFor(
            items = items,
            modifier = Modifier.weight(1f),
            contentPadding = InnerPadding(top = 8.dp)
        ) { todo ->
            if (currentlyEditing?.id == todo.id) {
                TodoItemInlineEditor(
                    item = currentlyEditing,
                    onEditItemChange = onEditItemChange,
                    onEditDone = onEditDone,
                    onRemoveItem = { onRemoveItem(todo) }
                )
            } else {
                TodoRow(
                    todo = todo,
                    onItemClicked = { onStartEdit(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }

        }

        // For quick testing, a random item generator button
//        Button(
//            onClick = { onAddItem(generateRandomTodoItem()) },
//            modifier = Modifier.padding(16.dp).fillMaxWidth()
//        ) {
//            Text(text = "Add random item")
//        }
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha: Float = remember(todo.id) { randomTint() }
) {
    Row(
        modifier = modifier.clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = todo.task)
        Icon(todo.icon.vectorAsset, tint = contentColor().copy(alpha = iconAlpha))
    }
}


@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) = TodoItemInput(
    text = item.task,
    onTextChange = { onEditItemChange(item.copy(task = it)) },
    icon = item.icon,
    onIconChange = { onEditItemChange(item.copy(icon = it)) },
    submit = onEditDone,
    iconsVisible = true,
    buttonSlot = {
        Row {
            val shrinkButtons = Modifier.widthIn(20.dp)
            TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                Text(
                    text = "\uD83D\uDCBE", // floppy disk
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)

                )
            }
            TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                Text(text = "❌", textAlign = TextAlign.End, modifier = Modifier.width(30.dp))

            }
        }
    }
)


@Composable
private fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean,
    buttonSlot: @Composable () -> Unit
) {

    Column {
        Row(Modifier.padding(horizontal = 16.dp).padding(top = 16.dp)) {
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                onImeAction = submit
            )

            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.gravity(Alignment.CenterVertically), children = buttonSlot)

        }


        if (iconsVisible) {
            AnimatedIconRow(
                icon = icon,
                onIconChange = onIconChange,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.preferredHeight(16.dp))
        }
    }
}

@Composable
fun Scaffold() {

}

@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }

    val iconVisibility = text.isNotBlank()

    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }

    TodoItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconsVisible = iconVisibility
    ) {
        TodoEditButton(onClick = submit, text = "Add", enable = text.isNotBlank())
    }
}

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
//    TodoScreen(items, {}, {})
}

@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}
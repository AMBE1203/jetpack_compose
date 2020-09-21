package com.example.layoutbasic.todo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import com.example.layoutbasic.ui.LayoutBasicTheme
import androidx.compose.runtime.getValue


class TodoActivity : AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutBasicTheme {
                TodoActivityScreen(todoViewModel = todoViewModel)
            }
        }
    }

}

@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    TodoScreen(
        items = todoViewModel.todoItems,
        onAddItem = { todoViewModel.addItem(it) },
        onRemoveItem = todoViewModel::removeItem,
        currentlyEditing = todoViewModel.currentEditItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone
    )
}
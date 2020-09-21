package com.example.layoutbasic.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    private var currentEditPosition by mutableStateOf(-1)

    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)

    var todoItems: List<TodoItem> by mutableStateOf(listOf())
        private set

    // event: onEditItemSelected
    fun onEditItemSelected(item: TodoItem) {
        currentEditPosition = todoItems.indexOf(item)
    }

    // event: onEditDone
    fun onEditDone() {
        currentEditPosition = -1
    }

    // event: onEditItemChange
    fun onEditItemChange(item: TodoItem) {
        val currentItemm = requireNotNull(currentEditItem)
        require(currentItemm.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }
        todoItems = todoItems.toMutableList().also {
            it[currentEditPosition] = item
        }
    }

    // event: addItem
    fun addItem(item: TodoItem) {
        todoItems = todoItems + listOf(item)

    }

    // event: removeIte,
    fun removeItem(item: TodoItem) {
        todoItems = todoItems.toMutableList().also {
            it.remove(item)
            onEditDone()
        }

    }
}
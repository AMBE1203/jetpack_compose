package com.example.layoutbasic.util

import com.example.layoutbasic.todo.TodoIcon
import com.example.layoutbasic.todo.TodoItem

fun generateRandomTodoItem(): TodoItem {
    val message = listOf(
        "Learn compose",
        "Learn state",
        "Build dynamic UIs",
        "Learn Unidirectional Data Flow",
        "Integrate LiveData",
        "Integrate ViewModel",
        "Remember to savedState!",
        "Build stateless composables",
        "Use state from stateless composables"
    ).random()
    val icon = TodoIcon.values().random()
    return TodoItem(task = message, icon = icon)
}

fun main(){
    val  list = listOf(1, 2, 3)
    var currentCount = 0
    for (item in list){
        currentCount += item
        print(currentCount)
    }
}
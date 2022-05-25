package com.ubaya.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.ubaya.todoapp.model.Todo

interface TodoCheckedChangeListener{
    fun onCheckedChanged(
        cb : CompoundButton,
        isChecked : Boolean,
        obj : Todo
    )
}

interface TodoEditClickListener{
    fun onEditClick(view : View)
}

interface TodoPriorityClickListener{
    fun onRadioPriorityClick(
        view : View,
        priority : Int,
        obj : Todo
    )
}

interface TodoSaveChangesListener{
    fun onSaveChanges(
        view :View,
        obj:Todo
    )
}

interface TodoAddListener{
    fun onAddNewTodo(view: View)
}

interface TodoDateListener {
    fun onDateClick(view: View)
    fun onTimeClick(view: View)
}
package com.ubaya.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.todoapp.R
import com.ubaya.todoapp.model.Todo
import kotlinx.android.synthetic.main.layout_todo_item.view.*

class TodoListAdapter (val todoList:ArrayList<Todo>, val adapterOnClick : (Todo) -> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){
    class TodoViewHolder(var view:View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_todo_item, parent, false)

        return TodoViewHolder(view)

    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        with(holder.view){
            val priority = when(todo.priority){
                1 -> "Low"
                2 -> "Medium"
                else -> "HIGH"
            }
            checkTask.text = "[$priority] ${todoList[position].title.toString()}"

            checkTask.setOnCheckedChangeListener { compoundButton, b ->
                if (b) adapterOnClick(todo)
            }

            //kalau di klik edit, ke fragment edit
            btnEdit.setOnClickListener {
                val action = TodoListFragmentDirections.actionEditTodo(todo.uuid)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

}
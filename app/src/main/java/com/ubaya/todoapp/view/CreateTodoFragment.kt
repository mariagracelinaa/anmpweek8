package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.ubaya.todoapp.R
import com.ubaya.todoapp.databinding.FragmentCreateTodoBinding
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.util.NotificationHelper
import com.ubaya.todoapp.util.TodoWorker
import com.ubaya.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(), TodoAddListener, TodoPriorityClickListener, TodoDateListener {
    private lateinit var viewModel:DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        dataBinding.todo = Todo("","",3,0)
        dataBinding.addListener = this
        dataBinding.priorityListener = this
        dataBinding.dateListener = this

//        btnAdd.setOnClickListener {
//            val title = txtTitle.text.toString()
//            var radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
//            var todo = Todo(title, txtNotes.text.toString(), radio.tag.toString().toInt(), 0)
//            val list = listOf(todo)
//            viewModel.addTodo(list)
//            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
//            Navigation.findNavController(it).popBackStack()

        //Untuk mengeluarkan notification
//            NotificationHelper(view.context)
//                .createNotification(
//                    "Todo $title" + "created",
//                        "A new todo has been created! Stay focus!")
        //Membuat work request
//            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//                .setInitialDelay(5, TimeUnit.SECONDS)
//                .setInputData(
//                    workDataOf(
//                        "title" to "Todo $title created",
//                        "message" to "A new todo has been created! Stay focus!"
//                    )
//                ).build()
        //Antrikan ke work manager
//            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)

//        }


    }

    override fun onRadioPriorityClick(view: View, priority: Int, obj: Todo) {
        obj.priority = priority
    }

    override fun onAddNewTodo(view: View) {
        dataBinding.todo?.let {
            val list = listOf(it)
            viewModel.addTodo(list)
            Toast.makeText(view.context, "Todo created", Toast.LENGTH_LONG).show()
            Navigation.findNavController(view).popBackStack()
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "title" to "Todo ${it.title} created",
                        "message" to "A new todo has been created! Stay focus!"
                    )
                ).build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        }
    }

    override fun onDateClick(view: View) {
        //slide 69
    }

    override fun onTimeClick(view: View) {

    }
}
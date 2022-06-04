package com.ubaya.todoapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TimePicker
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
import kotlinx.android.synthetic.main.fragment_create_todo.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(), TodoAddListener, TodoPriorityClickListener, TodoDateListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var viewModel:DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding

    //Variable untuk perhitungan waktu tunda notifikasi
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0

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
        //Hitung selisih waktu sekarang dengan tenggat
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute, 0)
        val today = Calendar.getInstance()
        val diff = calendar.timeInMillis / 1000L - today.timeInMillis / 1000L
        dataBinding.todo?.let {
            val list = listOf(it)
            viewModel.addTodo(list)
            Toast.makeText(view.context, "Todo created", Toast.LENGTH_LONG).show()
            Navigation.findNavController(view).popBackStack()
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(diff, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "title" to "Todo ${it.title} created",
                        "message" to "A new todo has been created! Stay focus!"
                    )
                ).build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        }
    }

    //Untuk memunculkan datetime picker dgn tgl skrg
    override fun onDateClick(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        activity?.let{
            DatePickerDialog(it, this, year, month, day).show()
        }
    }

    override fun onTimeClick(view: View) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year, month, day)
            val date = day.toString().padStart(2, '0') + '-' + month.toString().padStart(2, '0') + '-' + year.toString().padStart(2, '0')
            dataBinding.root.txtDate.setText(date)

            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val time = hourOfDay.toString().padStart(2, '0') + ':' + minute.toString().padStart(2, '0')
        this.hour = hourOfDay
        this.minute = minute
    }
}
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
import com.ubaya.todoapp.R
import com.ubaya.todoapp.databinding.FragmentEditTodoBinding
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

class EditTodoFragment : Fragment(), TodoPriorityClickListener, TodoSaveChangesListener {
    private lateinit var viewModel : DetailTodoViewModel
    private lateinit var dataBinding: FragmentEditTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        //Menangkap id kiriman dari MainActivity
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        dataBinding.priorityListener = this
        dataBinding.saveListener = this
    }

    private fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner){
            dataBinding.todo = it
//            txtTitle.setText(it.title)
//            txtNotes.setText(it.note)
//            when(it.priority){
//                1 -> radioLow.isChecked = true
//                2 -> radioMedium.isChecked = true
//                else -> radioHigh.isChecked = true
//            }
        }
    }

    override fun onRadioPriorityClick(view: View, priority: Int, obj: Todo) {
        obj.priority = priority
    }

    override fun onSaveChanges(view: View, obj: Todo) {
        viewModel.update(obj.uuid, obj.title, obj.note, obj.priority)
        Toast.makeText(view.context, "Todo Updated", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(view).popBackStack()
    }

}
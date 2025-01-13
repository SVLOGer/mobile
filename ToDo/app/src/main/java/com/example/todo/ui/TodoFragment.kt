package com.example.todo.ui

import TodoAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.data.TodoRecord
import com.example.todo.databinding.FragmentTodoBinding
import com.example.todo.viewmodel.TodoViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class TodoFragment : Fragment(R.layout.fragment_todo) {
    private val viewModel: TodoViewModel by activityViewModels()
    private lateinit var binding: FragmentTodoBinding
    private lateinit var tomorrowAdapter: TodoAdapter
    private lateinit var futureAdapter: TodoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tomorrowAdapter = TodoAdapter(
            deleteTodoRecord = { todoRecord: TodoRecord ->
                viewModel.deleteTodoRecord(todoRecord)
            },
            gotoEditorFn = { args: Bundle ->
                findNavController().navigate(R.id.action_fragmentTodo_to_fragmentEditor, args)
            }
        )

        futureAdapter = TodoAdapter(
            deleteTodoRecord = { todoRecord: TodoRecord ->
                viewModel.deleteTodoRecord(todoRecord)
            },
            gotoEditorFn = { args: Bundle ->
                findNavController().navigate(R.id.action_fragmentTodo_to_fragmentEditor, args)
            }
        )

        binding.tomorrowListView.adapter = tomorrowAdapter
        binding.futureListView.adapter = futureAdapter

        val items = arrayOf("Все задачи", "Не начато", "В процессе", "Готово")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.statusSpinner.adapter = adapter

        binding.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                viewModel.setSelectedStatus(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.createButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentTodo_to_fragmentEditor)
        }

        binding.viewCreateButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentTodo_to_fragmentEditor)
        }

        lifecycleScope.launch {
            viewModel.filteredRecords.collect { filteredRecords ->
                val tomorrow = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, 1)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis

                val updatedRecords = filteredRecords.map { record ->
                    record.copy(status = when (record.status) {
                        "All" -> "Все задачи"
                        "Not started" -> "Не начато"
                        "In progress" -> "В процессе"
                        "Done" -> "Готово"
                        else -> "Неизвестно"
                    })
                }.filter { it.deadline >= tomorrow }


                val dayAfterTomorrow = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, 2)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis

                val tasksDueTomorrow = updatedRecords.filter { it.deadline in tomorrow until dayAfterTomorrow }
                val futureTasks = updatedRecords.filter { it.deadline >= dayAfterTomorrow }

                println("TodoFragment"+"Tasks due tomorrow: ${tasksDueTomorrow.size}")
                println("TodoFragment"+"Future tasks: ${futureTasks.size}")

                tomorrowAdapter.todoRecordList = tasksDueTomorrow.sortedByDescending { it.deadline }
                futureAdapter.todoRecordList = futureTasks.sortedByDescending { it.deadline }

                tomorrowAdapter.notifyDataSetChanged()
                futureAdapter.notifyDataSetChanged()

                updateVisibility(updatedRecords.isEmpty())
            }
        }
    }


    private fun updateVisibility(isEmpty: Boolean) {
        binding.viewTodo.visibility = View.GONE
        binding.emptyTodo.visibility = View.GONE
        if (isEmpty) {
            binding.emptyTodo.visibility = View.VISIBLE
            binding.viewTodo.visibility = View.GONE
        } else {
            binding.emptyTodo.visibility = View.GONE
            binding.viewTodo.visibility = View.VISIBLE
        }
    }
}
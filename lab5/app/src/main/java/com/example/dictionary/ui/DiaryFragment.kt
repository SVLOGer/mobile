package com.example.dictionary.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.data.DiaryAdapter
import com.example.dictionary.data.DiaryRecord
import com.example.dictionary.databinding.FragmentDiaryBinding
import com.example.dictionary.viewmodel.DiaryViewModel
import kotlinx.coroutines.launch

class DiaryFragment : Fragment(R.layout.fragment_diary) {
    private val viewModel: DiaryViewModel by activityViewModels()
    private lateinit var binding: FragmentDiaryBinding
    private lateinit var diaryAdapter: DiaryAdapter
    private lateinit var filterButton: ImageButton
    private var isFiltered = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDiaryBinding.bind(view)
        diaryAdapter = DiaryAdapter(
            deleteDiaryRecord = { diaryRecord: DiaryRecord ->
                viewModel.deleteDiaryRecord(diaryRecord)
            },
            gotoEditorFn = { args: Bundle ->
                findNavController().navigate(R.id.action_fragmentDiary_to_fragmentEditor, args)
            }
        )
        binding.listView.adapter = diaryAdapter

        binding.createButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentDiary_to_fragmentEditor)
        }

        binding.viewCreateButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentDiary_to_fragmentEditor)
        }

        filterButton = view.findViewById(R.id.select_date_button)

        binding.selectDateButton.setOnClickListener {
            if (isFiltered) {
                viewModel.setSelectedDate(null)
                filterButton.setImageResource(R.drawable.filter)
            }
            else {
                showDatePicker()
                filterButton.setImageResource(R.drawable.filtered)
            }
            isFiltered = !isFiltered
        }

        lifecycleScope.launch {
            viewModel.filteredRecords.collect { filteredRecords ->
                diaryAdapter.diaryRecordList = filteredRecords.sortedByDescending { it.createdAt }
                diaryAdapter.notifyDataSetChanged()
                updateVisibility(filteredRecords.isEmpty())
            }
        }

    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, selectedDay)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            viewModel.setSelectedDate(selectedDate.timeInMillis)
        }, year, month, day).show()
    }

    private fun updateVisibility(isEmpty: Boolean) {
        binding.viewDiary.visibility = View.GONE
        binding.emptyDiary.visibility = View.GONE
        if (isEmpty) {
            binding.emptyDiary.visibility = View.VISIBLE
            binding.viewDiary.visibility = View.GONE
        } else {
            binding.emptyDiary.visibility = View.GONE
            binding.viewDiary.visibility = View.VISIBLE
        }
    }
}
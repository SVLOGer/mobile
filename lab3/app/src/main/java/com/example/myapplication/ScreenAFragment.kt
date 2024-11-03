package com.example.myapplication

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentScreenABinding

class ScreenAFragment : Fragment() {

    private lateinit var binding: FragmentScreenABinding
    private var selectedDate: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenABinding.inflate(inflater, container, false)

        val name = arguments?.getString("name") ?: ""
        val surname = arguments?.getString("surname") ?: ""

        binding.textNameSurname.text = "$name $surname"

        binding.buttonDatePicker.setOnClickListener {
            showDatePicker()
        }

        binding.buttonToScreenC.setOnClickListener {
            val birthDate = selectedDate
            val action = ScreenAFragmentDirections.actionScreenAFragmentToScreenC(name, surname, birthDate)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.textBirthDate.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }
}

package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentScreenCBinding

class ScreenCFragment : Fragment() {

    private lateinit var binding: FragmentScreenCBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenCBinding.inflate(inflater, container, false)

        val name = arguments?.getString("name") ?: ""
        val surname = arguments?.getString("surname") ?: ""
        val birthDate = arguments?.getString("birthDate") ?: ""

        binding.textNameSurname.text = "$name $surname"
        binding.textBirthDate.text = birthDate

        binding.buttonBackToMain.setOnClickListener {
            findNavController().navigate(R.id.action_screenCFragment_to_mainFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.mainFragment, inclusive = true)
                    .build())
        }

        return binding.root
    }
}

package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentScreenBBinding

class ScreenBFragment : Fragment() {

    private lateinit var binding: FragmentScreenBBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenBBinding.inflate(inflater, container, false)

        binding.buttonToScreenD.setOnClickListener {
            findNavController().navigate(R.id.action_screenBFragment_to_screenD)
        }

        binding.buttonBackToMain.setOnClickListener {
            findNavController().navigate(R.id.action_screenBFragment_to_mainFragment)
        }

        return binding.root
    }
}


package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentScreenDBinding

class ScreenDFragment : Fragment() {

    private lateinit var binding: FragmentScreenDBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenDBinding.inflate(inflater, container, false)

        binding.buttonBackToMain.setOnClickListener {
            findNavController().navigate(R.id.action_screenDFragment_to_mainFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.mainFragment, inclusive = true)
                    .build())
        }

        return binding.root
    }
}


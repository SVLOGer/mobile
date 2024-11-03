package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.buttonToScreenA.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val surname = binding.editTextSurname.text.toString()
            val action = MainFragmentDirections.actionMainFragmentToScreenA(name, surname)
            findNavController().navigate(action)
        }

        binding.buttonToScreenB.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_screenB)
        }

        return binding.root
    }
}

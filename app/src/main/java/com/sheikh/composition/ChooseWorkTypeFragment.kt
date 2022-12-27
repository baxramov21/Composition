package com.sheikh.composition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.sheikh.composition.databinding.FragmentChooseWorkTypeBinding


class ChooseWorkTypeFragment : Fragment() {

    private var _binding: FragmentChooseWorkTypeBinding? = null
    private val binding: FragmentChooseWorkTypeBinding
        get() = _binding ?: throw RuntimeException("Binding not initialized or null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChooseWorkTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openAppropriateWorkType(
            binding.gamingType,
            R.id.action_chooseWorkTypeFragment_to_chooseLevelFragment
        )

        openAppropriateWorkType(
            binding.learningType,
            R.id.action_chooseWorkTypeFragment_to_calculatorFragment
        )

    }

    private fun openAppropriateWorkType(button: Button, destinationID: Int) {
        button.setOnClickListener {
            findNavController().navigate(destinationID)
        }
    }
}
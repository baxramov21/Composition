package com.sheikh.composition.presentation.fragment.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sheikh.composition.databinding.FragmentCalculatorBinding
import com.sheikh.composition.presentation.viewmodel.LearningViewModel
import java.lang.String
import java.util.*


class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding: FragmentCalculatorBinding
        get() = _binding ?: throw RuntimeException("Binding is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[LearningViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (checkTILCorrect()) {
                val input = binding.tilNumber.editText?.text.toString().toDouble()
                noError()
                calculate(input)
            } else {
                inError()
            }
        }
    }

    private fun convertToString(value: Double): kotlin.String {
        return String.format(Locale.getDefault(),"%.02f", value)
    }

    private fun checkTILCorrect(): Boolean {
        return binding.tilNumber.editText?.text.toString().isNotEmpty() ||
                binding.tilNumber.editText?.text.toString().isNotBlank()
    }

    private fun inError() {
        binding.tilNumber.error = "Please edit the input"
    }

    private fun noError() {
        binding.tilNumber.error = null
    }

    private fun calculate(input: Double) {
        val result = viewModel.calculateSqrt(input)
        binding.tvResult.text = convertToString(result)
    }
}
package com.sheikh.composition.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sheikh.composition.databinding.FragmentChooseLevelBinding
import com.sheikh.composition.domain.entities.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("ChooseLevelFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchAppropriateLeveledGame()
    }

    private fun launchAppropriateLeveledGame() {
        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameFragmentByLevel(Level.TEST)
            }

            buttonLevelEasy.setOnClickListener {
                launchGameFragmentByLevel(Level.EASY)
            }

            buttonLevelNormal.setOnClickListener {
                launchGameFragmentByLevel(Level.NORMAL)
            }

            buttonLevelHard.setOnClickListener {
                launchGameFragmentByLevel(Level.HARD)
            }
        }
    }

    private fun launchGameFragmentByLevel(level: Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
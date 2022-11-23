package com.sheikh.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sheikh.composition.R
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.FRAGMENT_NAME)
            .commit()
    }

    companion object {

        const val FRAGMENT_NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
}
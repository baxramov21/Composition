package com.sheikh.composition.presentation.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sheikh.composition.R
import com.sheikh.composition.databinding.FragmentGameBinding
import com.sheikh.composition.domain.entities.GameResult
import com.sheikh.composition.domain.entities.Level
import com.sheikh.composition.presentation.viewmodel.GameViewModel
import com.sheikh.composition.presentation.viewmodel.GameViewModelFactory

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var level: Level
    
    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }

    private val gameViewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val tvOptionsCollection by lazy {
        mutableListOf<TextView>().apply {
            with(binding) {
                add(tvOption1)
                add(tvOption2)
                add(tvOption3)
                add(tvOption4)
                add(tvOption5)
                add(tvOption6)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        putArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        onChooseAnswer()
    }

    private fun observeViewModel() {
        whenTimeChanges()
        newQuestion()
        answerPercents()
        progressAnswersChanged()
        isAnswerCountEnough()
        isAnswerPercentEnough()
        gameResult()
    }

    private fun answerPercents() {
        gameViewModel.answerAccuracyPercent.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }

        gameViewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
    }

    private fun progressAnswersChanged() {
        gameViewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    private fun gameResult() {
        gameViewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }

    private fun isAnswerCountEnough() {
        gameViewModel.enoughAnswerCount.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
    }

    private fun isAnswerPercentEnough() {
        gameViewModel.enoughAnswerPercent.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
    }

    private fun newQuestion() {
        gameViewModel.question.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()
            }
            for (i in 0 until tvOptionsCollection.size) {
                tvOptionsCollection[i].text = it.options[i].toString()
            }
        }
    }

    private fun onChooseAnswer() {
        for (tvOption in tvOptionsCollection) {
            tvOption.setOnClickListener {
                val chosenAnswer = tvOption.text.toString().toInt()
                gameViewModel.chooseAnswer(chosenAnswer)
            }
        }
    }

    private fun whenTimeChanges() {
        gameViewModel.timeLeft.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorID = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }

        return ContextCompat.getColor(requireContext(), colorID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        val args = Bundle().apply {
            putParcelable(GameFinishedFragment.GAME_RESULT, gameResult)
        }
        findNavController().navigate(R.id.action_gameFragment_to_gameFinishedFragment,args)
    }

    private fun putArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {

        const val FRAGMENT_NAME = "GameFragment"

        const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}
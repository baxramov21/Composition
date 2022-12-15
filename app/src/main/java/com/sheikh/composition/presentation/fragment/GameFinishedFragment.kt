package com.sheikh.composition.presentation.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sheikh.composition.R
import com.sheikh.composition.databinding.FragmentGameFinishedBinding
import com.sheikh.composition.domain.entities.GameResult

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("GameFinishedFragment == null")

    private lateinit var gameResult: GameResult

    private val contextT by lazy {
        requireContext()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        putArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, callback)

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }

        showGameDetails()
    }

    private fun showGameDetails() {
        showResultInPercents()
        showResultInNumbers()
        binding.emojiResult.setImageDrawable(getEmojiByWinState(gameResult.win))
    }

    private fun showResultInNumbers() {
        with(binding) {
            with(gameResult) {
                val requiredCountText =
                    getFormattedStringById(
                        R.string.required_score,
                        gameSettings.minCountOfRightAnswers
                    )

                tvRequiredAnswers.text = requiredCountText

                val scoredCountText = String.format(
                    contextT.getString(R.string.right_answers),
                    countOfRightAnswers,
                    gameSettings.minCountOfRightAnswers
                )

                tvScoreAnswers.text = scoredCountText

            }
        }
    }

    private fun showResultInPercents() {
        with(binding) {
            with(gameResult) {
                val minPercentText =
                    getFormattedStringById(
                        R.string.required_percentage,
                        gameSettings.minPercentOfRightAnswers
                    )
                tvRequiredPercentage.text = minPercentText

                val scoredPercent = getPercentOfRightAnswers(countOfQuestions, countOfRightAnswers)

                val scoredPercentText =
                    getFormattedStringById(R.string.score_percentage, scoredPercent)
                tvScorePercentage.text = scoredPercentText
            }
        }
    }

    private fun getPercentOfRightAnswers(countOfQuestions: Int, countOfRightAnswers: Int): Int {
        return if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun getFormattedStringById(stringID: Int, varArgForStringFormat: Int): String {
        return String.format(contextT.getString(stringID), varArgForStringFormat)
    }

    private fun getEmojiByWinState(win: Boolean): Drawable? {
        return if (win) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_smile)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_sad)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun putArgs() {
        requireArguments().getParcelable<GameResult>(GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager
            .popBackStack(
                GameFragment.FRAGMENT_NAME,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
    }

    companion object {

        const val FRAGMENT_NAME = "GameFinishedFragment"

        private const val GAME_RESULT = "RESULT"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT, gameResult)
                }
            }
        }
    }
}
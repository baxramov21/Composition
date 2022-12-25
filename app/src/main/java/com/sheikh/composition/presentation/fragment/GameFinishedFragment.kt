package com.sheikh.composition.presentation.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sheikh.composition.R
import com.sheikh.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("GameFinishedFragment == null")

    private val args by navArgs<GameFinishedFragmentArgs>()
    private val gameResult by lazy {
        args.gameResult
    }

    private val contextT by lazy {
        requireContext()
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


    private fun retryGame() {
        findNavController().popBackStack()

    }
}
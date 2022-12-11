package com.sheikh.composition.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sheikh.composition.R
import com.sheikh.composition.databinding.FragmentGameBinding
import com.sheikh.composition.domain.entities.GameResult
import com.sheikh.composition.domain.entities.GameSettings
import com.sheikh.composition.domain.entities.Level

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var level: Level

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
        binding.tvSum.setOnClickListener {
            launchGameFinishedFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFinishedFragment() {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container, GameFinishedFragment.newInstance(
                    GameResult(
                        true,
                        10,
                        20,
                        GameSettings
                            (
                            20,
                            10,
                            50,
                            20
                        )
                    )
                )
            )
            .addToBackStack(GameFinishedFragment.FRAGMENT_NAME)
            .commit()
    }

    private fun putArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {

        const val FRAGMENT_NAME = "GameFragment"

        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}
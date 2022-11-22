package com.sheikh.composition.domain.entities

import android.view.inspector.IntFlagMapping

data class GameResult(
    val win: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
)

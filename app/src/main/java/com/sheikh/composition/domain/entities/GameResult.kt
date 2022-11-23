package com.sheikh.composition.domain.entities

import java.io.Serializable

data class GameResult(
    val win: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
) : Serializable

package com.sheikh.composition.domain.entity

data class GameSettings(
    val gameTimeInSeconds: Int,
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,

    )
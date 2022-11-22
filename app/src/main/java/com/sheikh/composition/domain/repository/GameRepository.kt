package com.sheikh.composition.domain.repository

import com.sheikh.composition.domain.entities.GameSettings
import com.sheikh.composition.domain.entities.Level
import com.sheikh.composition.domain.entities.Question

interface GameRepository {

    fun getGameSettings(level: Level): GameSettings

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
}
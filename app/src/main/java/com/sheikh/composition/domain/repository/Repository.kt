package com.sheikh.composition.domain.repository

import com.sheikh.composition.domain.entity.GameSettings
import com.sheikh.composition.domain.entity.Level
import com.sheikh.composition.domain.entity.Question

interface Repository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}
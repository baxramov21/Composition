package com.sheikh.composition.domain.repository

import com.sheikh.composition.domain.entities.GameType

interface LearnRepository {

    fun getWorkType(isGameType: Boolean): GameType

    fun calculateSQRT(input: Double): Double

    fun calculateDegrees(input: Double, degree: Int): Double
}
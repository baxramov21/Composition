package com.sheikh.composition.data

import com.sheikh.composition.domain.entities.GameType
import com.sheikh.composition.domain.repository.LearnRepository
import java.math.RoundingMode
import kotlin.math.sqrt

object LearnRepositoryImpl : LearnRepository {

    private const val UNCALCULATE_ABLE_NUMBER: Double = 0.0

    override fun getWorkType(isGameType: Boolean): GameType {
        return if (isGameType) {
            GameType.GAMING
        } else {
            GameType.LEARNING
        }
    }

    override fun calculateSQRT(input: Double): Double {
        return if (input > 0) {
            sqrt(input)
        } else {
            UNCALCULATE_ABLE_NUMBER
        }
    }

    override fun calculateDegrees(input: Double, degree: Int): Double {
        var result = input
        for (i in 1 until degree) {
            result *= result
        }
        return result
    }
}
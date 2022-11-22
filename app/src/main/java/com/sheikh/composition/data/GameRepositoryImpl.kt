package com.sheikh.composition.data

import com.sheikh.composition.domain.entities.GameSettings
import com.sheikh.composition.domain.entities.Level
import com.sheikh.composition.domain.entities.Question
import com.sheikh.composition.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_ANSWER_VALUE = 1
    private const val MIN_SUM_VALUE = 2

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> GameSettings(
                10,
                5,
                65,
                10
            )
            Level.EASY -> GameSettings(
                20,
                10,
                70,
                15
            )

            Level.NORMAL -> GameSettings(
                50,
                15,
                80,
                25
            )

            Level.HARD -> GameSettings(
                100,
                20,
                95,
                30
            )
        }
    }

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }


}
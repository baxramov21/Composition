package com.sheikh.composition.domain.usecase

import com.sheikh.composition.domain.entities.Question
import com.sheikh.composition.domain.repository.GameRepository

class GenerateQuestion(private val repository: GameRepository) {

    operator fun invoke( maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}
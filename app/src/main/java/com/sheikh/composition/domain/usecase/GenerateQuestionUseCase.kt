package com.sheikh.composition.domain.usecase

import com.sheikh.composition.domain.entity.Question
import com.sheikh.composition.domain.repository.Repository

class GenerateQuestionUseCase(private val repository: Repository) {
    operator fun invoke(maxSumValue: Int)
    : Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}
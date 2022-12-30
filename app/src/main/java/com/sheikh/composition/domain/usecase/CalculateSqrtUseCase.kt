package com.sheikh.composition.domain.usecase

import com.sheikh.composition.domain.repository.LearnRepository

class CalculateSqrtUseCase(private val repository: LearnRepository) {

    operator fun invoke(input: Double): Double {
        return repository.calculateSQRT(input)
    }
}
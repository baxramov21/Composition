package com.sheikh.composition.domain.usecase

import com.sheikh.composition.domain.repository.LearnRepository

class CalculateDegreeUseCase(private val repository: LearnRepository) {

    operator fun invoke(input: Double, degree: Int): Double {
        return repository.calculateDegrees(input, degree)
    }
}
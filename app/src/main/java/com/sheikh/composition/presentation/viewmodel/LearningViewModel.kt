package com.sheikh.composition.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.sheikh.composition.data.LearnRepositoryImpl
import com.sheikh.composition.domain.usecase.CalculateDegreeUseCase
import com.sheikh.composition.domain.usecase.CalculateSqrtUseCase

class LearningViewModel(): ViewModel() {

    private val repository = LearnRepositoryImpl

    private val calculateSqrtUseCase = CalculateSqrtUseCase(repository)
    private val calculateDegreeUseCase = CalculateDegreeUseCase(repository)

    fun calculateSqrt(input: Double): Double {
        return calculateSqrtUseCase(input)
    }

    fun calculateDegree(input: Double, degree: Int): Double {
        return calculateDegreeUseCase(input, degree)
    }

}
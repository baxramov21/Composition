package com.sheikh.composition.domain.usecase

import com.sheikh.composition.domain.entities.GameType
import com.sheikh.composition.domain.repository.LearnRepository

class GetWorkTypeUseCase(private val repository: LearnRepository) {

    operator fun invoke(isGameType: Boolean): GameType {
        return repository.getWorkType(isGameType)
    }
}
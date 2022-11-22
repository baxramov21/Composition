package com.sheikh.composition.domain.usecase

import com.sheikh.composition.domain.entities.GameSettings
import com.sheikh.composition.domain.entities.Level
import com.sheikh.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}
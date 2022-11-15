package com.sheikh.composition.domain.usecase

import com.sheikh.composition.domain.entity.GameSettings
import com.sheikh.composition.domain.entity.Level
import com.sheikh.composition.domain.repository.Repository

class GetGameSettingsUseCase(private val repository: Repository) {
    operator fun invoke(level: Level)
            : GameSettings {
        return repository.getGameSettings(level)
    }
}
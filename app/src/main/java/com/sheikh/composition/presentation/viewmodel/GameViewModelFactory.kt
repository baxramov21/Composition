package com.sheikh.composition.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sheikh.composition.domain.entities.Level

class GameViewModelFactory(private val application: Application, private val level: Level) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application,level) as T
        }
        throw IllegalArgumentException("Unknown view mode class $modelClass")
    }
}
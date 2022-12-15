package com.sheikh.composition.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheikh.composition.R
import com.sheikh.composition.data.GameRepositoryImpl
import com.sheikh.composition.domain.entities.GameResult
import com.sheikh.composition.domain.entities.GameSettings
import com.sheikh.composition.domain.entities.Level
import com.sheikh.composition.domain.entities.Question
import com.sheikh.composition.domain.usecase.GenerateQuestion
import com.sheikh.composition.domain.usecase.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestion(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _timeLeft = MutableLiveData<String>()
    val timeLeft: LiveData<String>
        get() = _timeLeft

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _timeOut = MutableLiveData<Boolean>()
    val timeOut: LiveData<Boolean>
        get() = _timeOut

    private val _questionCount = MutableLiveData<Int>()
    val questionCount: LiveData<Int>
        get() = _questionCount

    private val _answerAccuracyPercent = MutableLiveData<Int>()
    val answerAccuracyPercent: LiveData<Int>
        get() = _answerAccuracyPercent

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughAnswerCount = MutableLiveData<Boolean>()
    val enoughAnswerCount: LiveData<Boolean>
        get() = _enoughAnswerCount

    private val _enoughAnswerPercent = MutableLiveData<Boolean>()
    val enoughAnswerPercent: LiveData<Boolean>
        get() = _enoughAnswerPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    private lateinit var gameSettings: GameSettings

    private var timer: CountDownTimer? = null
    private var countOfQuestions = 0
    private var countOfRightAnswers = 0

    // Must be overwritten
    private var timeInSeconds: Int = 0
    private var maxSumValue: Int = 0


    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateNewQuestion()
        updateProgress()
    }

    private fun getGameSettings(level: Level) {
        gameSettings = getGameSettingsUseCase(level)
        timeInSeconds = gameSettings.timeInSeconds
        maxSumValue = gameSettings.maxSumValue
        _minPercent.value = gameSettings.minPercentOfRightAnswers

    }

    private fun startTimer() {
        val timeInMilliSeconds = timeInSeconds * MILLIS_IN_SECONDS
        timer = object : CountDownTimer(
            timeInMilliSeconds,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(p0: Long) {
                _timeLeft.value = convertTime(p0)
                _timeOut.value = false
            }

            override fun onFinish() {
                finishGame()
            }
        }

        timer?.start()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _answerAccuracyPercent.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )

        with(gameSettings) {
            _enoughAnswerCount.value = countOfRightAnswers >= minCountOfRightAnswers
            _enoughAnswerPercent.value = percent >= minPercentOfRightAnswers
        }
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun convertTime(timeInMillis: Long): String {
        val seconds = timeInMillis / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    fun generateNewQuestion() {
        _question.value = generateQuestionUseCase(maxSumValue)
    }

    fun chooseAnswer(chosenAnswer: Int) {
        checkAnswer(chosenAnswer)
        updateProgress()
        generateNewQuestion()
    }

    private fun checkAnswer(chosenAnswer: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (chosenAnswer == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughAnswerCount.value == true && enoughAnswerPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()

    }

    companion object {

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}
package com.sheikh.composition.presentation.binding

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.sheikh.composition.R

private var requiredAnswerCount: Int = 0
private var countOfRightAnswers: Int = 0

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    val requiredCountText = getFormattedStringById(
        textView.context,
        R.string.required_score,
        count
    )
    textView.text = requiredCountText
    requiredAnswerCount = count
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percentage: Int) {
    val requiredCountText = getFormattedStringById(
        textView.context,
        R.string.required_percentage,
        percentage
    )
    textView.text = requiredCountText
}

@BindingAdapter("scoredCount")
fun bindScoredCount(textView: TextView, scoredCount: Int) {
    val scoredCountText = String.format(
        textView.context.getString(R.string.right_answers),
        scoredCount,
        requiredAnswerCount
    )

    textView.text = scoredCountText
    countOfRightAnswers = scoredCount
}

@BindingAdapter("scoredPercent")
fun bindScoredPercentage(textView: TextView, countOfQuestions: Int) {
    val scoredPercent = getPercentOfRightAnswers(
        countOfQuestions,
        countOfRightAnswers
    )

    val scoredPercentText = getFormattedStringById(
        textView.context,
        R.string.score_percentage, scoredPercent
    )

    textView.text = scoredPercentText
}

@BindingAdapter("resultEmoji")
fun bindEmoji(imageView: ImageView, win: Boolean) {
    imageView.setImageDrawable(getEmojiByWinState(imageView.context, win))
}

private fun getEmojiByWinState(context: Context, win: Boolean): Drawable? {
    return if (win) {
        ContextCompat.getDrawable(context, R.drawable.ic_smile)
    } else {
        ContextCompat.getDrawable(context, R.drawable.ic_sad)
    }
}

private fun getFormattedStringById(
    contextT: Context,
    stringID: Int,
    value: Int
): String {
    return String.format(contextT.getString(stringID), value)
}

private fun getPercentOfRightAnswers(countOfQuestions: Int, countOfRightAnswers: Int): Int {
    return if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}
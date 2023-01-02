package com.sheikh.composition.presentation.binding

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("numberToString")
fun bindNumberToString(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("mainProgress")
fun bindMainProgressBar(progressBar: ProgressBar, answerAccuracyPercent: Int) {
    progressBar.setProgress(answerAccuracyPercent, true)
}

@BindingAdapter("secondaryProgress")
fun bindSecondaryProgress(progressBar: ProgressBar, minAnswerAccuracyPercent: Int) {
    progressBar.secondaryProgress = minAnswerAccuracyPercent
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("onOptionsClickListener")
fun bindOptionsClick(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorID = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorID)
}
package com.chow.customview

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.core.widget.addTextChangedListener
import com.chow.customview.databinding.ViewGuessBinding

class GuessView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private val binding = ViewGuessBinding.inflate(LayoutInflater.from(context), this, true)
    private var answerSize: Int = 0
    private var textViews = mutableListOf<TextView>()

    init {
        binding.apply {
            llAnswer.setOnClickListener {
                etBackground.apply {
                    requestFocus()
                    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                        this,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }
            etBackground.apply {
                addTextChangedListener { s ->
                    if (s == null || s.isEmpty()) {
                        textViews[0].text = ""
                        return@addTextChangedListener
                    }
                    if (s.last().toString().isBlank() || s.length > answerSize) {
                        setText(s.toString().substring(0, s.length - 1))
                        setSelection(text.length)
                        return@addTextChangedListener
                    }
                    if (s.length <= answerSize - 1) textViews[s.length].text = ""
                    textViews[s.length - 1].text = s.last().toString()
                }
            }
        }
    }

    fun setAnswerSize(size: Int) {
        answerSize = size
        for (i in 0 until size) {
            binding.llAnswer.addView(
                TextView(context).apply {
                    layoutParams = LayoutParams(
                        context.resources.getDimensionPixelSize(R.dimen.dimen_30),
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        if (i < size - 1) {
                            marginEnd = context.resources.getDimensionPixelSize(R.dimen.dimen_5)
                        }
                    }
                    gravity = Gravity.CENTER
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                    typeface = Typeface.DEFAULT_BOLD
                    setBackgroundResource(R.color.black)
                    setTextColor(ContextCompat.getColor(context, R.color.green))
                }.also { textViews.add(it) }
            )
        }
    }
}
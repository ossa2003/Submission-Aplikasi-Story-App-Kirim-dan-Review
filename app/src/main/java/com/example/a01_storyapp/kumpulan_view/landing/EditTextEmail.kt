package com.example.a01_storyapp.kumpulan_view.landing

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.a01_storyapp.R

class EditTextEmail : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Email"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (!text.isNullOrBlank()) {
                    error = if (!isValid(text.toString())) {
                        resources.getString(R.string.message_mail)
                    } else {
                        null
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
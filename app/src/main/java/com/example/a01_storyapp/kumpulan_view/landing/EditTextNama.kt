package com.example.a01_storyapp.kumpulan_view.landing

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.a01_storyapp.R
import com.google.android.material.textfield.TextInputEditText


class EditTextNama @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {


    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Melihat nama diisi atau tidak
                if (s.toString().isEmpty()) {
                    setError(context.getString(R.string.nameNone), null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Tidak perlu dilakukan apa pun setelah teks berubah.
            }
        })
    }
}

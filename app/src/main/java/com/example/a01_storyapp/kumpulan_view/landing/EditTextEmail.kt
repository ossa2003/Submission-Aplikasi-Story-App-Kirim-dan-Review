package com.example.a01_storyapp.kumpulan_view.landing

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.example.a01_storyapp.R

class EditTextEmail @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tidak perlu dilakukan apa pun sebelum teks berubah.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tidak perlu dilakukan apa pun saat teks berubah.
            }

            override fun afterTextChanged(s: Editable?) {
                // Set pesan kesalahan jika alamat email tidak valid, atau hapus pesan kesalahan jika alamat email valid.
                error = if (s!!.contains("@")) null else (context.getString(R.string.alert_invalid_email))
            }
        })
    }
}

package com.example.a01_storyapp.kumpulan_view.masuk

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_storyapp.R
import com.example.a01_storyapp.databinding.ActivityMasukBinding
import com.example.a01_storyapp.kumpulan_response.MasukRespon
import com.example.a01_storyapp.kumpulan_view.cerita.ListCeritaActivity
import com.example.a01_storyapp.kumpulan_view.utama.MainActivity
import com.example.a01_storyapp.pengaturanapi.PengaturanAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Deklarasi kelas MasukActivity yang mewarisi AppCompatActivity
class MasukActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMasukBinding
    private lateinit var preferences: SharedPreferences
    private var isChecked = false

    companion object {
        val PREFS_NAME = "user_pref"
        val NAME = "name"
        val ID = "userId"
        val TOKEN = "token"
        val CHECK_BOX = "isChecked"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        isChecked = preferences.getBoolean(CHECK_BOX, false)
        checkLogin(isChecked)

        playAnimation()
        getApiLogin()
    }

    //animation
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val edtEmail = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val edtPass = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val btnLog = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val btnSign = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(title, edtEmail, edtPass, btnLog, btnSign)
            start()
        }
    }

    private fun checkLogin(login: Boolean) {
        if (login) {
            val intent = Intent(this@MasukActivity, ListCeritaActivity::class.java)
            startActivity(intent)
        }
    }

    fun validate(userId: String, name: String, token: String) {
        val editor = preferences.edit()
        editor.putString(NAME, name)
        editor.putString(ID, userId)
        editor.putString(TOKEN, token)
        editor.apply()
    }

    private fun getApiLogin() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditText.error = "Masukkan Email"
                }

                password.isEmpty() -> {
                    binding.passwordEditText.error = "Masukkan Password"
                }

                else -> {
                    showLoading(true)
                    val client =
                        PengaturanAPI.mendapatkanApiService().mendapatkanLogin(email, password)
                    client.enqueue(object : Callback<MasukRespon> {
                        override fun onResponse(
                            call: Call<MasukRespon>,
                            response: Response<MasukRespon>
                        ) {
                            showLoading(false)
                            if (response.isSuccessful) {
                                response.body()?.loginResult?.apply {
                                    validate(userId, name, token)
                                    AlertDialog.Builder(this@MasukActivity).apply {
                                        setTitle("Yeah!")
                                        setMessage(resources.getString(R.string.message_login))
                                        setPositiveButton(resources.getString(R.string.message_btn)) { _, _ ->
                                            val intent = Intent(
                                                this@MasukActivity,
                                                ListCeritaActivity::class.java
                                            )
                                            startActivity(intent)
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                                }
                            } else {
                                showLoading(false)
                                Toast.makeText(
                                    this@MasukActivity,
                                    resources.getString(R.string.toast_not),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<MasukRespon>, t: Throwable) {
                            Toast.makeText(
                                this@MasukActivity,
                                resources.getString(R.string.toast_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                }
            }
            binding.btnSignup.setOnClickListener {
                val intent = Intent(this@MasukActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            else -> false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun redirectToMain() {
        val intent = Intent(this@MasukActivity, ListCeritaActivity::class.java)
        startActivity(intent)
        finish() // Menyelesaikan activity login agar tidak bisa kembali lagi ke halaman login
    }

}
package com.example.a01_storyapp.kumpulan_view.masuk

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a01_storyapp.R
import com.example.a01_storyapp.databinding.ActivityMasukBinding
import com.example.a01_storyapp.kumpulan_view.utama.FactoryViewModel
import com.example.a01_storyapp.kumpulan_view.utama.MainActivity

// Deklarasi kelas MasukActivity yang mewarisi AppCompatActivity
class MasukActivity : AppCompatActivity() {

    // Inisialisasi viewModel dengan lazy instantiation menggunakan FactoryViewModel
    private val viewModel by viewModels<MasukViewModel> {
        FactoryViewModel.mendapatkanInstance(this)
    }
    // Mendeklarasikan variabel binding yang akan diinisialisasi dengan ActivityMasukBinding
    private lateinit var binding: ActivityMasukBinding

    // Fungsi onCreate yang dipanggil saat Activity pertama kali dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menginisialisasi binding dengan layout inflater
        binding = ActivityMasukBinding.inflate(layoutInflater)
        // Mengatur isi konten view dengan root dari binding
        setContentView(binding.root)

        // Mengatur window insets untuk menyesuaikan padding dengan sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }

        // Memanggil fungsi untuk mengatur tampilan
        setupView()
        // Memanggil fungsi untuk mengatur aksi tombol
        setupAction()
        // Memanggil fungsi untuk memainkan animasi
        playAnimation()
    }

    // Fungsi untuk mengatur tampilan
    private fun setupView() {
        @Suppress("DEPRECATION")
        // Memeriksa versi build
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Menyembunyikan status bar jika versi Android lebih besar atau sama dengan R
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            // Menyembunyikan status bar untuk versi Android yang lebih lama
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        // Menyembunyikan action bar
        supportActionBar?.hide()
    }

    // Fungsi untuk mengatur aksi tombol
    private fun setupAction() {
        // Menambahkan listener pada tombol login
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // Validasi input email dan password
            when {
                email.isEmpty() -> {
                    binding.emailEditText.error = ("Input the right Email!")
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = ("Input the 8 character!")
                }
                else -> {
                    // Memanggil fungsi masuk dari viewModel
                    viewModel.masuk(email, password)
                    // Menunjukkan loading
                    menunjukkanLoading(true)
                }
            }
        }
        // Mengamati perubahan pada live data
        observeLoginResponse()
        observeLoadingState()
        observeErrorState()
    }

    // Fungsi untuk memainkan animasi
    private fun playAnimation() {
        // Membuat animasi translasi pada imageView
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        // Membuat animasi alpha pada beberapa view

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

    private fun observeLoginResponse() {
        viewModel.masukRespon.observe(this){ response ->
            if (response.error == true){
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Berhasil Login!")
                    setMessage("Happy Exploring the app!")
                    setPositiveButton("Next") { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                        menunjukkanLoading(false)
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun observeErrorState() {
        viewModel.sedangError.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                menunjukkanError(errorMessage)
                menunjukkanLoading(false)
            }
        }
    }


    private fun menunjukkanError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }


    private fun menunjukkanLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun observeLoadingState() {
        viewModel.sedangLoading.observe(this){ isLoading ->
            menunjukkanLoading(isLoading)
        }
    }


}
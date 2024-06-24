//package com.example.a01_storyapp.kumpulan_view.daftar
//
//import android.animation.AnimatorSet
//import android.animation.ObjectAnimator
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import android.view.View
//import android.view.WindowInsets
//import android.view.WindowManager
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.viewModels
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.lifecycle.Observer
//import com.example.a01_storyapp.R
//import com.example.a01_storyapp.databinding.ActivityDaftarBinding
//import com.example.a01_storyapp.kumpulan_view.masuk.MasukActivity
//import com.example.a01_storyapp.kumpulan_view.utama.FactoryViewModel
//import com.google.android.material.snackbar.Snackbar
//
//// Mendeklarasikan kelas DaftarActivity yang merupakan turunan dari AppCompatActivity
//class DaftarActivity : AppCompatActivity() {
//    // Mendeklarasikan variabel binding untuk mengikat layout ActivityDaftarBinding
//    private lateinit var binding: ActivityDaftarBinding
//    // Mendeklarasikan variabel viewModel untuk mengakses DaftarViewModel dengan menggunakan FactoryViewModel
//    private val viewModel by viewModels<DaftarViewModel> {
//        FactoryViewModel.mendapatkanInstance(this)
//    }
//
//    // Fungsi yang dipanggil saat Activity dibuat
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge() // Mengaktifkan fitur edge-to-edge
//        binding = ActivityDaftarBinding.inflate(layoutInflater) // Mengikat layout
//        setContentView(binding.root) // Menetapkan konten tampilan dari binding.root
//
//        mengaturAction() // Mengatur aksi-aksi pada view
//        mengaturView() // Mengatur tampilan view
//        memainkanAnimation() // Memainkan animasi
//
//        // Mengatur inset jendela untuk menghindari overlay pada sistem bar
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
//            insets
//        }
//    }
//
//    // Fungsi untuk mengatur aksi-aksi pada view
//    private fun mengaturAction() {
//        // Mengatur aksi klik pada tombol signup
//        binding.signupButton.setOnClickListener {
//            val name = binding.nameEditText.text.toString()
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//
//            // Validasi input pengguna
//            when {
//                name.isEmpty() -> {
//                    binding.nameEditText.error = "Invalid name"
//                }
//                email.isEmpty() -> {
//                    binding.emailEditText.error = "Input the correct email"
//                }
//                password.isEmpty() -> {
//                    binding.passwordEditText.error = "Input 8 character of Password"
//                }
//                else -> {
//                    menunjukkanLoading(true) // Menampilkan loading
//                    viewModel.signup(name, email, password) // Memanggil fungsi signup pada viewModel
//                    // Mengamati perubahan isLoading pada viewModel
//                    viewModel.isLoading.observe(this, Observer { isLoading ->
//                        menunjukkanLoading(isLoading)
//                        if (!isLoading) {
//                            // Menampilkan dialog sukses jika registrasi berhasil
//                            AlertDialog.Builder(this).apply {
//                                setTitle("Yes!")
//                                val message = getString(R.string.contentalertdialog, email)
//                                setMessage(message)
//                                setPositiveButton("Next") { _, _ ->
//                                    val intent = Intent(context, MasukActivity::class.java)
//                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                                    startActivity(intent)
//                                    finish()
//                                }
//                                create()
//                                show()
//                            }.also {
//                                // Menampilkan snackbar jika registrasi gagal
//                                Snackbar.make(binding.root, ("Failed to Register"), Snackbar.LENGTH_SHORT).show()
//                            }
//                        } else {
//                            // Menampilkan toast jika registrasi berhasil
//                            Toast.makeText(
//                                this@DaftarActivity,
//                                ("Your Account is successfully registered!"),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    })
//                }
//            }
//        }
//    }
//
//    // Fungsi untuk mengatur tampilan view
//    private fun mengaturView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            // Menyembunyikan status bar pada versi Android R ke atas
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            // Menyembunyikan status bar pada versi Android di bawah R
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide() // Menyembunyikan action bar
//    }
//
//    // Fungsi untuk menampilkan atau menyembunyikan loading
//    private fun menunjukkanLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
//    }
//
//    // Fungsi untuk memainkan animasi
//    private fun memainkanAnimation() {
//        // Membuat animasi translasi untuk imageViewSignup
//        ObjectAnimator.ofFloat(binding.imageViewSignup, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        // Membuat animasi alpha untuk berbagai view
//        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
//        val message = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
//        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextViewSignup, View.ALPHA, 1f).setDuration(100)
//        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
//        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
//        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val login = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)
//
//        // Mengatur animasi secara berurutan dan memainkannya
//        AnimatorSet().apply {
//            playSequentially(
//                title,
//                message,
//                nameTextView,
//                nameEditTextLayout,
//                emailTextView,
//                emailEditTextLayout,
//                passwordTextView,
//                passwordEditTextLayout,
//                login
//            )
//            startDelay = 100
//        }.start()
//    }
//}

//package com.example.a01_storyapp.kumpulan_view.landing
//
//import android.animation.AnimatorSet
//import android.animation.ObjectAnimator
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import android.view.View
//import android.view.WindowInsets
//import android.view.WindowManager
//import androidx.appcompat.app.AppCompatActivity
//import com.example.a01_storyapp.databinding.ActivitySelamatDatangBinding
//import com.example.a01_storyapp.kumpulan_view.daftar.DaftarActivity
//import com.example.a01_storyapp.kumpulan_view.masuk.MasukActivity
//
//// Deklarasi kelas WelcomeActivity yang mewarisi AppCompatActivity
//class WelcomeActivity : AppCompatActivity() {
//
//    // Mendeklarasikan variabel binding yang akan diinisialisasi dengan ActivitySelamatDatangBinding
//    private lateinit var binding: ActivitySelamatDatangBinding
//
//    // Fungsi onCreate yang dipanggil saat Activity pertama kali dibuat
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Menginisialisasi binding dengan layout inflater
//        binding = ActivitySelamatDatangBinding.inflate(layoutInflater)
//        // Mengatur isi konten view dengan root dari binding
//        setContentView(binding.root)
//
//        // Memanggil fungsi untuk mengatur tampilan
//        pengaturanView()
//        // Memanggil fungsi untuk mengatur tindakan pada tombol
//        PengaturanAction()
//        // Memanggil fungsi untuk memainkan animasi
//        memainkanAnimation()
//    }
//
//    // Fungsi untuk mengatur tampilan
//    private fun pengaturanView() {
//        @Suppress("DEPRECATION")
//        // Memeriksa versi build
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            // Menyembunyikan status bar jika versi Android lebih besar atau sama dengan R
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            // Menyembunyikan status bar untuk versi Android yang lebih lama
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        // Menyembunyikan action bar
//        supportActionBar?.hide()
//    }
//
//    // Fungsi untuk mengatur tindakan pada tombol
//    private fun PengaturanAction() {
//        // Menambahkan listener pada tombol login untuk berpindah ke MasukActivity
//        binding.loginButton.setOnClickListener {
//            startActivity(Intent(this, MasukActivity::class.java))
//        }
//
//        // Menambahkan listener pada tombol signup untuk berpindah ke DaftarActivity
//        binding.signupButton.setOnClickListener {
//            startActivity(Intent(this, DaftarActivity::class.java))
//        }
//    }
//
//    // Fungsi untuk memainkan animasi
//    private fun memainkanAnimation() {
//        // Membuat animasi translasi pada imageView
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        // Membuat animasi alpha pada beberapa view
//        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
//        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)
//        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
//        val desc = ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(100)
//
//        // Menggabungkan animasi login dan signup untuk dimainkan bersama
//        val together = AnimatorSet().apply {
//            playTogether(login, signup)
//        }
//
//        // Memainkan animasi secara berurutan
//        AnimatorSet().apply {
//            playSequentially(title, desc, together)
//            start()
//        }
//    }
//}

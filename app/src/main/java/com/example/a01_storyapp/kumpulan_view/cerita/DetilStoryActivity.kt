package com.example.a01_storyapp.kumpulan_view.cerita

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.a01_storyapp.databinding.ActivityDetilStoryBinding

// Mendeklarasikan kelas DetilStoryActivity yang merupakan turunan dari AppCompatActivity
class DetilStoryActivity : AppCompatActivity() {

    // Mendeklarasikan variabel binding untuk mengikat layout ActivityDetilStoryBinding
    private lateinit var binding: ActivityDetilStoryBinding

    // Fungsi yang dipanggil saat Activity dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Mengaktifkan fitur edge-to-edge

        // Mengikat layout dengan menggunakan View Binding
        binding = ActivityDetilStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur judul ActionBar dan menampilkan tombol kembali
        supportActionBar?.title = ("This is the detail of the story")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Mendapatkan data yang dikirim melalui Intent
        val photoUrl = intent.getStringExtra(EXTRA_PHOTO_URL)
        val createdAt = intent.getStringExtra(EXTRA_CREATED_AT)
        val name = intent.getStringExtra(EXTRA_NAME)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val lon = intent.getDoubleExtra(EXTRA_LON, 0.0)
        val id = intent.getStringExtra(EXTRA_ID)
        val lat = intent.getDoubleExtra(EXTRA_LAT, 0.0)

        // Memanggil fungsi detilView untuk menampilkan detail cerita
        detilView(photoUrl, createdAt, name, description, lon, id, lat)
    }

    // Fungsi untuk menampilkan detail cerita pada layout
    private fun detilView(photoUrl: String?, createdAt: String?, name: String?, description: String?, lon: Double, id: String?, lat: Double) {
        // Menggunakan Glide untuk memuat gambar dari URL ke ImageView
        Glide.with(this@DetilStoryActivity)
            .load(photoUrl)
            .into(binding.profileImageView)

        // Mengatur teks pada TextView dengan data yang diterima
        binding.nameTextView.text = name
        binding.dateTextView.text = createdAt
        binding.descTextView.text = description
    }

    // Mendeklarasikan objek companion untuk menyimpan konstanta
    companion object {
        const val EXTRA_PHOTO_URL = "EXTRA_PHOTO_URL" // Konstanta untuk foto URL
        const val EXTRA_CREATED_AT = "EXTRA_CREATED_AT" // Konstanta untuk tanggal dibuat
        const val EXTRA_NAME = "EXTRA_NAME" // Konstanta untuk nama
        const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION" // Konstanta untuk deskripsi
        const val EXTRA_LON = "EXTRA_LON" // Konstanta untuk longitude
        const val EXTRA_ID = "EXTRA_ID" // Konstanta untuk ID
        const val EXTRA_LAT = "EXTRA_LAT" // Konstanta untuk latitude
    }
}

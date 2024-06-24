package com.example.a01_storyapp.kumpulan_view.cerita

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.a01_storyapp.R
import com.example.a01_storyapp.databinding.ActivityTambahCeritaBinding
import com.example.a01_storyapp.kumpulan_response.MenambahStoryGuestRespon
import com.example.a01_storyapp.kumpulan_view.utama.MainActivity
import com.example.a01_storyapp.kumpulan_view.utama.mendapatkanImageUri
import com.example.a01_storyapp.kumpulan_view.utama.reduceFileImage
import com.example.a01_storyapp.kumpulan_view.utama.uriKeFile
import com.example.a01_storyapp.pengaturanapi.PengaturanAPI
import com.example.a01_storyapp.preferensi.PreferensiUser
import com.example.a01_storyapp.preferensi.dataStore
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

// Mendeklarasikan kelas TambahCeritaActivity yang merupakan turunan dari AppCompatActivity
class TambahCeritaActivity : AppCompatActivity() {

    // Mendeklarasikan variabel userPref untuk menyimpan preferensi pengguna
    private lateinit var userPref: PreferensiUser

    // Mendeklarasikan variabel binding untuk mengikat layout ActivityTambahCeritaBinding
    private lateinit var binding: ActivityTambahCeritaBinding

    // Mendeklarasikan variabel currentImageUri untuk menyimpan URI gambar saat ini
    private var currentImageUri: Uri? = null

    // Fungsi yang dipanggil saat Activity dibuat
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Mengaktifkan fitur edge-to-edge
        binding = ActivityTambahCeritaBinding.inflate(layoutInflater) // Mengikat layout
        setContentView(binding.root) // Menetapkan konten tampilan dari binding.root

        // Mendapatkan instance PreferensiUser
        userPref = PreferensiUser.mendapatkanInstance(dataStore)
        setupUI() // Mengatur UI
        setupWindowInsets() // Mengatur inset jendela
    }

    // Fungsi untuk mengatur UI
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setupUI() {
        // Mengatur klik listener untuk tombol kamera
        binding.btnCamera.setOnClickListener { memulaiCamera() }
        // Mengatur klik listener untuk tombol galeri
        binding.btnGallery.setOnClickListener { memulaiGallery() }
        // Mengatur klik listener untuk tombol kirim cerita
        binding.sendStory.setOnClickListener { unggahImage() }
    }

    // Fungsi untuk mengatur inset jendela
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }
    }

    // Fungsi untuk memulai kamera
    private fun memulaiCamera() {
        currentImageUri = mendapatkanImageUri(this)
        launchIntentCamera.launch(currentImageUri!!)
    }

    // Mendaftarkan hasil aktivitas kamera
    private val launchIntentCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) menampilkanImage()
    }

    // Fungsi untuk memulai galeri
    private fun memulaiGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    // Mendaftarkan hasil aktivitas galeri
    private val launchGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        uri?.let {
            currentImageUri = it
            menampilkanImage()
        } ?: Log.d("Photo Picker", "No Media Selected")
    }

    // Fungsi untuk mengunggah gambar
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun unggahImage() {
        val description = binding.descText.text.toString()
        currentImageUri?.let { uri ->
            val imageFile = uriKeFile(uri, this).reduceFileImage()
            menunjukkanLoading(true)

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData("photo", imageFile.name, requestImageFile)

            lifecycleScope.launch {
                userPref.mendapatkanSession().collect { user ->
                    val token = user.token
                    if (token.isNotEmpty()) {
                        unggahStory(token, multipartBody, requestBody)
                    } else {
                        menunjukkanToast("Token salah")
                        menunjukkanLoading(false)
                    }
                }
            }
        } ?: menunjukkanToast("Gambar kosong")
    }

    // Fungsi untuk mengunggah cerita ke server
    private suspend fun unggahStory(token: String, multipartBody: MultipartBody.Part, requestBody: RequestBody) {
        try {
            val apiService = PengaturanAPI.mendapatkanApiService(token)
            val successResponse = apiService.unggahStory("Bearer $token", multipartBody, requestBody)
            menunjukkanToast(successResponse.message)
            menunjukkanSuccessDialog()
        } catch (e: retrofit2.HttpException) {
            mengatasiError(e)
        } finally {
            menunjukkanLoading(false)
        }
    }

    // Fungsi untuk mengatasi error dari response API
    private fun mengatasiError(e: retrofit2.HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, MenambahStoryGuestRespon::class.java)
        menunjukkanToast(errorResponse.message)
    }

    // Fungsi untuk menunjukkan dialog sukses
    private fun menunjukkanSuccessDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Success!")
            setMessage("Content uploaded!")
            setPositiveButton("Next") { _, _ ->
                val intent = Intent(this@TambahCeritaActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    // Fungsi untuk menunjukkan atau menyembunyikan loading
    private fun menunjukkanLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    // Fungsi untuk menunjukkan pesan toast
    private fun menunjukkanToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Fungsi untuk menampilkan gambar
    private fun menampilkanImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ImageViewResult.setImageURI(it)
        }
    }
}

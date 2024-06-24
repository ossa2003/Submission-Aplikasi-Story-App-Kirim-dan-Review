//package com.example.a01_storyapp.kumpulan_view.daftar
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.a01_storyapp.kumpulan_data.TempatPenyimpananUser
//import com.example.a01_storyapp.kumpulan_response.DaftarRespon
//import kotlinx.coroutines.launch
//
//// Mendeklarasikan kelas DaftarViewModel yang mewarisi kelas ViewModel
//class DaftarViewModel(private val userRepo: TempatPenyimpananUser) : ViewModel() {
//
//    // Mendeklarasikan variabel _signupResponse yang merupakan MutableLiveData untuk menyimpan respon pendaftaran
//    private val _signupResponse = MutableLiveData<DaftarRespon>()
//
//    // Mendeklarasikan variabel _isLoading yang merupakan MutableLiveData untuk menyimpan status loading
//    private val _isLoading = MutableLiveData<Boolean>()
//    // Mendeklarasikan variabel isLoading yang dapat diamati (LiveData)
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    // Mendeklarasikan variabel _isError yang merupakan MutableLiveData untuk menyimpan pesan error
//    private val _isError = MutableLiveData<String>()
//    // Mendeklarasikan variabel isError yang dapat diamati (LiveData)
//    val isError: LiveData<String> = _isError
//
//    // Fungsi untuk melakukan pendaftaran dengan parameter name, email, dan password
//    fun signup(name: String, email: String, password: String) {
//        _isLoading.value = true // Mengatur status loading menjadi true
//        // Meluncurkan coroutine di dalam viewModelScope
//        viewModelScope.launch {
//            try {
//                // Memanggil fungsi daftar pada userRepo dan menyimpan hasilnya ke dalam variabel message
//                val message = userRepo.daftar(name, email, password)
//                // Mengatur nilai _signupResponse dengan message (diasumsikan bahwa DaftarRespon berisi data yang relevan)
//                _signupResponse.value = message
//            } catch (e: Exception) {
//                // Jika terjadi kesalahan, mengatur nilai _isError dengan pesan error
//                _isError.value = e.message ?: "Unknown Error"
//            } finally {
//                // Mengatur status loading menjadi false
//                _isLoading.value = false
//            }
//        }
//    }
//
//}

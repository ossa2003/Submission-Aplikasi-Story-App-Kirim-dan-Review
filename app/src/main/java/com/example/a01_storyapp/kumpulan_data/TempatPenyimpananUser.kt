package com.example.a01_storyapp.kumpulan_data

import com.example.a01_storyapp.kumpulan_response.DaftarRespon
import com.example.a01_storyapp.kumpulan_response.MasukRespon
import com.example.a01_storyapp.kumpulan_response.MendapatkanStoryRespon
import com.example.a01_storyapp.pengaturanapi.ServisAPI
import com.example.a01_storyapp.preferensi.ModelUser
import com.example.a01_storyapp.preferensi.PreferensiUser
import kotlinx.coroutines.flow.Flow

// Mendeklarasikan kelas TempatPenyimpananUser dengan konstruktor privat
class TempatPenyimpananUser private constructor(
    // Mendeklarasikan variabel preferensiUser dengan tipe PreferensiUser
    private val preferensiUser: PreferensiUser,
    // Mendeklarasikan variabel servisAPI dengan tipe ServisAPI
    private val servisAPI: ServisAPI
) {

    // Mendeklarasikan fungsi suspend untuk menyimpan session user
    suspend fun menyimpanSession(user: ModelUser) {
        // Memanggil fungsi saveSession dari preferensiUser
        preferensiUser.saveSession(user)
    }

    // Mendeklarasikan fungsi untuk mendapatkan session user dalam bentuk Flow
    fun mendapatkanSession(): Flow<ModelUser> {
        // Mengembalikan hasil dari fungsi mendapatkanSession dari preferensiUser
        return preferensiUser.mendapatkanSession()
    }

    // Mendeklarasikan fungsi suspend untuk melakukan pendaftaran user
    suspend fun daftar(name: String, email: String, password: String): DaftarRespon {
        // Memanggil fungsi daftar dari servisAPI
        return servisAPI.daftar(name, email, password)
    }

    // Mendeklarasikan fungsi suspend untuk melakukan login user
    suspend fun masuk(email: String, password: String): MasukRespon {
        // Memanggil fungsi masuk dari servisAPI
        return servisAPI.masuk(email, password)
    }

    // Mendeklarasikan fungsi suspend untuk mendapatkan cerita (story) user
    suspend fun mendapatkanStory(token: String): MendapatkanStoryRespon {
        // Memanggil fungsi mendapatkanStory dari servisAPI
        return servisAPI.mendapatkanStory(token)
    }

    // Mendeklarasikan fungsi suspend untuk keluar (logout) user
    suspend fun keluar() {
        // Memanggil fungsi keluar dari preferensiUser untuk menghapus data session
        preferensiUser.keluar() // Pastikan ini menghapus semua data session
    }

    // Mendeklarasikan fungsi suspend untuk mengatur otentikasi user
    suspend fun mengaturAuth(user: ModelUser) = preferensiUser.saveSession(user)

    // Mendeklarasikan objek pendamping (companion object)
    companion object {
        // Mendeklarasikan variabel instance yang volatile untuk menyimpan instance tunggal TempatPenyimpananUser
        @Volatile
        private var instance: TempatPenyimpananUser? = null

        // Mendeklarasikan fungsi untuk mendapatkan instance dari TempatPenyimpananUser
        fun mendapatkanInstance(
            preferensiUser: PreferensiUser,
            servisAPI: ServisAPI
        ): TempatPenyimpananUser =
            // Memeriksa apakah instance sudah ada, jika belum membuat instance baru
            instance ?: synchronized(this) {
                instance ?: TempatPenyimpananUser(preferensiUser, servisAPI)
            }.also { instance = it }
    }
}

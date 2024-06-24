package com.example.a01_storyapp.injeksi

import android.content.Context
import com.example.a01_storyapp.kumpulan_data.ListDatabase
import com.example.a01_storyapp.kumpulan_data.TempatPenyimpananList
import com.example.a01_storyapp.pengaturanapi.PengaturanAPI
import com.example.a01_storyapp.preferensi.PreferensiUser
import com.example.a01_storyapp.preferensi.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

// Mendeklarasikan objek singleton bernama Injeksi
object Injeksi {

    // Fungsi untuk menyediakan instance dari TempatPenyimpananUser
    fun penyediaRepository(context: Context): TempatPenyimpananList {
        val database = ListDatabase.mendapatkanDatabase(context)
        val apiService = PengaturanAPI.mendapatkanApiService()
        return TempatPenyimpananList(database, apiService)
    }
}

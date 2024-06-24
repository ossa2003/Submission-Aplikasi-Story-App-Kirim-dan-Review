package com.example.a01_storyapp.injeksi

import android.content.Context
import com.example.a01_storyapp.kumpulan_data.TempatPenyimpananUser
import com.example.a01_storyapp.pengaturanapi.PengaturanAPI
import com.example.a01_storyapp.preferensi.PreferensiUser
import com.example.a01_storyapp.preferensi.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

// Mendeklarasikan objek singleton bernama Injeksi
object Injeksi {

    // Fungsi untuk menyediakan instance dari TempatPenyimpananUser
    fun penyediaRepository(context: Context): TempatPenyimpananUser {
        // Mendapatkan instance dari PreferensiUser dengan menggunakan context.dataStore
        val pref = PreferensiUser.mendapatkanInstance(context.dataStore)

        // Mengambil session pengguna secara sinkron dengan menggunakan runBlocking
        val user = runBlocking { pref.mendapatkanSession().first() }

        // Mendapatkan instance dari PengaturanAPI dengan menggunakan token pengguna
        val servisAPI = PengaturanAPI.mendapatkanApiService(user.token)

        // Mengembalikan instance dari TempatPenyimpananUser dengan parameter pref dan servisAPI
        return TempatPenyimpananUser.mendapatkanInstance(pref, servisAPI)
    }
}

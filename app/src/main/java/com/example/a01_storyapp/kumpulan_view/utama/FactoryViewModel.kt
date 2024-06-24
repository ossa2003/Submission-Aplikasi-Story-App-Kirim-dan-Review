//package com.example.a01_storyapp.kumpulan_view.utama
//
//import android.content.Context
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.a01_storyapp.injeksi.Injeksi
//import com.example.a01_storyapp.kumpulan_data.TempatPenyimpananUser
//import com.example.a01_storyapp.kumpulan_view.daftar.DaftarViewModel
//import com.example.a01_storyapp.kumpulan_view.masuk.MasukViewModel
//
//class FactoryViewModel(private val repository: TempatPenyimpananUser) : ViewModelProvider.NewInstanceFactory() {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <i : ViewModel> create(modelClass: Class<i>): i {
//        return when {
//            modelClass.isAssignableFrom(ViewModelMain::class.java) -> {
//                ViewModelMain(repository) as i
//            }
//            modelClass.isAssignableFrom(MasukViewModel::class.java) -> {
//                MasukViewModel(repository) as i
//            }
//            modelClass.isAssignableFrom(DaftarViewModel::class.java) -> {
//                DaftarViewModel(repository) as i
//            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//        }
//    }
//
//    companion object {
//        @Volatile
//        private var INSTANCE: FactoryViewModel? = null
//        @JvmStatic
//        fun mendapatkanInstance(context: Context): FactoryViewModel {
//            if (INSTANCE == null) {
//                synchronized(FactoryViewModel::class.java) {
//                    INSTANCE = FactoryViewModel(Injeksi.penyediaRepository(context))
//                }
//            }
//            return INSTANCE as FactoryViewModel
//        }
//    }
//}
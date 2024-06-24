package com.example.a01_storyapp.kumpulan_view.cerita

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.a01_storyapp.injeksi.Injeksi
import com.example.a01_storyapp.kumpulan_data.TempatPenyimpananList
import java.lang.IllegalArgumentException

class ListCeritaViewModel(private val listRepository: TempatPenyimpananList): ViewModel() {

    fun mendapatkanStory(token: String) = listRepository.mendapatkanStories(token).cachedIn(viewModelScope)

}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListCeritaViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ListCeritaViewModel(Injeksi.penyediaRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
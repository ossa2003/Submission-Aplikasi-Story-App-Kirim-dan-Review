package com.example.a01_storyapp.peta

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a01_storyapp.injeksi.Injeksi
import com.example.a01_storyapp.kumpulan_data.TempatPenyimpananList
import com.example.a01_storyapp.kumpulan_response.ListStoryItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PetaViewModel(private val listRepository: TempatPenyimpananList): ViewModel() {
    private val _listLoc = MutableLiveData<List<ListStoryItem>>()
    var lastLoc: LiveData<List<ListStoryItem>> = _listLoc

    fun mendapatkanLocation(token: String){
        viewModelScope.launch {
            _listLoc.postValue(listRepository.mendapatkanLoc(token))
        }
    }
}

class MapsViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetaViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PetaViewModel(Injeksi.penyediaRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown View Model Class: ")
    }
}
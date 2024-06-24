//package com.example.a01_storyapp.kumpulan_view.utama
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.asLiveData
//import androidx.lifecycle.viewModelScope
//import com.example.a01_storyapp.kumpulan_data.TempatPenyimpananUser
//import com.example.a01_storyapp.kumpulan_response.ListStoryItem
//import com.example.a01_storyapp.kumpulan_response.MenambahStoryGuestRespon
//import com.example.a01_storyapp.preferensi.ModelUser
//import com.google.gson.Gson
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.launch
//import retrofit2.HttpException
//
//class ViewModelMain(private val repository: TempatPenyimpananUser) : ViewModel() {
//
//    private val _storyList = MutableLiveData<List<ListStoryItem>>()
//    val listStory: LiveData<List<ListStoryItem>> get() = _storyList
//
//    private val _isLoading = MutableLiveData<Boolean>()
//
//    private val _message = MutableLiveData<String>()
//    val message: LiveData<String> get() = _message
//
//    fun mendapatkanSession(): LiveData<ModelUser> {
//        return repository.mendapatkanSession().asLiveData()
//    }
//
//    fun keluar() {
//        viewModelScope.launch {
//            repository.keluar()
//        }
//    }
//
//    fun mendapatkanSemuaStory() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val token = repository.mendapatkanSession().first().token
//                val storyResponse = repository.mendapatkanStory("Bearer $token")
//                val message = storyResponse.message
//
//                val nonNullStoryList = storyResponse.listStory?.filterNotNull() ?: emptyList()
//
//                _storyList.value = nonNullStoryList
//                _message.value = message ?: "Unknown Message"
//            } catch (e: HttpException) {
//                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, MenambahStoryGuestRespon::class.java)
//                val errorMessage = errorBody.message
//                _message.value = errorMessage ?: "Unknown Error"
//            } catch (e: Exception) {
//                _message.value = e.message ?: "Unknown Error"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//}

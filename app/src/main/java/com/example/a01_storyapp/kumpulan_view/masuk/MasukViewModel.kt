package com.example.a01_storyapp.kumpulan_view.masuk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a01_storyapp.kumpulan_data.TempatPenyimpananUser
import com.example.a01_storyapp.kumpulan_response.MasukRespon
import com.example.a01_storyapp.preferensi.ModelUser
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MasukViewModel(private val repository: TempatPenyimpananUser) : ViewModel() {

    fun menyimpanSession(user: ModelUser) {
        viewModelScope.launch {
            repository.menyimpanSession(user)
        }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val sedangLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String?>()
    val sedangError: MutableLiveData<String?> get() = _isError

    private val _loginResponse = MutableLiveData<MasukRespon>()
    val masukRespon: LiveData<MasukRespon> = _loginResponse

    fun masuk(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.masuk(email, password)
                val loginResult = response.loginResult

                // Check for null values and handle appropriately
                val userId = loginResult?.userId ?: ""
                val name = loginResult?.name ?: ""
                val token = loginResult?.token ?: ""

                val user = ModelUser(
                    userId = userId,
                    name = name,
                    email = email,
                    token = token,
                    isLogin = true
                )
                mengaturAuth(user)
                _loginResponse.postValue(response)
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                handleGeneralException(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }


    private fun handleHttpException(e: HttpException) {
        val jsonInString = e.response()?.errorBody()?.string()
        val errorBody = Gson().fromJson(jsonInString, MasukRespon::class.java)
        _isError.postValue(errorBody.message)
    }

    private fun handleGeneralException(e: Exception) {
        _isError.postValue(e.message ?: "An unexpected error occurred")
    }

    private fun mengaturAuth(userModel: ModelUser) {
        viewModelScope.launch {
            repository.mengaturAuth(userModel)
        }
    }

}
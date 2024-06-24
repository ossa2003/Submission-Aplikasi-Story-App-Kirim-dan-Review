package com.example.a01_storyapp.preferensi

data class ModelUser(
    val userId: String,
    val name: String,
    val email: String,
    val token: String,
    val isLogin: Boolean
)
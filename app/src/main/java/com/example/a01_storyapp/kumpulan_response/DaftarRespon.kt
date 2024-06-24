package com.example.a01_storyapp.kumpulan_response

import com.google.gson.annotations.SerializedName

data class DaftarRespon(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

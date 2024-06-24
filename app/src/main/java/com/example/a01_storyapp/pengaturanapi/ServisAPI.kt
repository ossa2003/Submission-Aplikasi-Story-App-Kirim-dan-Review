package com.example.a01_storyapp.pengaturanapi

import com.example.a01_storyapp.kumpulan_response.DaftarRespon
import com.example.a01_storyapp.kumpulan_response.MasukRespon
import com.example.a01_storyapp.kumpulan_response.MenambahStoryRespon
import com.example.a01_storyapp.kumpulan_response.MendapatkanStoryRespon
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ServisAPI {

	@FormUrlEncoded
	@POST("register")
	suspend fun daftar(
		@Field("name") name: String,
		@Field("email") email: String,
		@Field("password") password: String
	): DaftarRespon

	@FormUrlEncoded
	@POST("login")
	suspend fun masuk(
		@Field("email") email: String,
		@Field("password") password: String
	): MasukRespon

	@Multipart
	@POST("stories")
	suspend fun unggahStory(
		@Header("Authorization") token: String,
		@Part file: MultipartBody.Part,
		@Part("description") description: RequestBody,
	): MenambahStoryRespon

	@GET("stories")
	suspend fun mendapatkanStory(
		@Header("Authorization") token: String,
	) : MendapatkanStoryRespon
}
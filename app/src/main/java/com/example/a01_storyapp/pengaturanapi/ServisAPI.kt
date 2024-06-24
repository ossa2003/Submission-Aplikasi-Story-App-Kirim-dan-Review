package com.example.a01_storyapp.pengaturanapi

import com.example.a01_storyapp.kumpulan_response.DaftarRespon
import com.example.a01_storyapp.kumpulan_response.MasukRespon
import com.example.a01_storyapp.kumpulan_response.MenambahStoryRespon
import com.example.a01_storyapp.kumpulan_response.MendapatkanStoryRespon
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ServisAPI {

	@FormUrlEncoded
	@POST("register")
	fun getRegist(
		@Field("name") name :String,
		@Field("email") email : String,
		@Field("password") password : String
	): Call<DaftarRespon>

	@POST("login")
	fun mendapatkanLogin(
		@Field("email") email: String,
		@Field("password") password: String
	): Call<MasukRespon>

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
		@Query("page") page: Int,
		@Query("size") size: Int,
		@Query("location") location: Int
	) : MendapatkanStoryRespon

	@Multipart
	@POST("stories")
	fun uploadImage(
		@Header("Authorization") token: String,
		@Part file: MultipartBody.Part,
		@Part("description") description: RequestBody,
		@Part("lat") lat: Float,
		@Part("lon") lon: Float
	): Call<MenambahStoryRespon>

	@GET("stories?location=1")
	suspend fun mendapatkanLocation(
		@Header("Authorization") token: String
	): MendapatkanStoryRespon
}
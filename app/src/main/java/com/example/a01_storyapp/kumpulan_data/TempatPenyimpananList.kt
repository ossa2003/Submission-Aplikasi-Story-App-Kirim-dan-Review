package com.example.a01_storyapp.kumpulan_data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.a01_storyapp.kumpulan_response.ListStoryItem
import com.example.a01_storyapp.pengaturanapi.ServisAPI

class TempatPenyimpananList(private val listDatabase: ListDatabase, private val apiService: ServisAPI) {
    @OptIn(ExperimentalPagingApi::class)
    fun mendapatkanStories(token: String): LiveData<PagingData<ListStoryItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediasi(listDatabase, apiService, token),
            pagingSourceFactory = {
                listDatabase.ceritaDao().mendapatkanAllStory()
            }
        ).liveData
    }

    suspend fun mendapatkanLoc(token: String): List<ListStoryItem>{
        return apiService.mendapatkanLocation("Bearer $token").listStory
    }
}
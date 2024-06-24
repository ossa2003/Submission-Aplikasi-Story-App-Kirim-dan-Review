package com.example.a01_storyapp.kumpulan_data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.a01_storyapp.kumpulan_response.ListStoryItem
import com.example.a01_storyapp.pengaturanapi.ServisAPI
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediasi(
    private val database: ListDatabase,
    private val apiService: ServisAPI,
    private val token: String
): RemoteMediator<Int, ListStoryItem>(){

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoryItem>
    ): MediatorResult {
        val page = when (loadType){
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosesToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND ->{
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND ->{
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.mendapatkanStory("Bearer $token",page, state.config.pageSize, 1 and 0).listStory
            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    database.remotKeysDao().hapusRemoteKeys()
                    database.ceritaDao().hapusAll()
                }
                val prevKey = if(page == 1) null else page -1
                val nextKey = if(endOfPaginationReached) null else page +1
                val keys = responseData.map{
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remotKeysDao().memasukkanAll(keys)
                database.ceritaDao().memasukkanStory(responseData)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception){
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListStoryItem>): RemoteKeys? {
        return state.pages.lastOrNull{it.data.isNotEmpty()}?.data?.lastOrNull()?.let { data ->
            database.remotKeysDao().mendapatkanRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListStoryItem>): RemoteKeys? {
        return state.pages.firstOrNull{it.data.isNotEmpty()}?.data?.firstOrNull()?.let { data ->
            database.remotKeysDao().mendapatkanRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosesToCurrentPosition(state: PagingState<Int, ListStoryItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remotKeysDao().mendapatkanRemoteKeysId(id)
            }
        }
    }
}
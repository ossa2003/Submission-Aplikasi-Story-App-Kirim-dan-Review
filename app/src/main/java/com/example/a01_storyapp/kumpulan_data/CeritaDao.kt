package com.example.a01_storyapp.kumpulan_data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.a01_storyapp.kumpulan_response.ListStoryItem

@Dao
interface CeritaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun memasukkanStory(story: List<ListStoryItem>)

    @Query("SELECT * FROM story")
    fun mendapatkanAllStory(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM story")
    suspend fun hapusAll()
}
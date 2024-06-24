package com.example.a01_storyapp.kumpulan_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun memasukkanAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM story_keys WHERE id = :id")
    suspend fun mendapatkanRemoteKeysId(id: String): RemoteKeys?

    @Query("DELETE FROM story_keys")
    suspend fun hapusRemoteKeys()
}
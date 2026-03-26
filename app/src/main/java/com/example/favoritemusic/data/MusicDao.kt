package com.example.favoritemusic.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.favoritemusic.model.Music

@Dao
interface MusicDao {
    @Query("SELECT * FROM Music")
    fun getAll(): Flow<List<Music>>

    @Insert
    suspend fun insert(music: Music)

    @Delete
    suspend fun delete(music: Music)
}
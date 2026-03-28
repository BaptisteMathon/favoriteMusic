package com.example.favoritemusic.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.favoritemusic.model.Music
import com.example.favoritemusic.model.MusicByUser

@Dao
interface MusicDao {
    @Query("SELECT * FROM Music")
    fun getAll(): Flow<List<Music>>

    @Insert
    suspend fun insert(music: Music)

    @Delete
    suspend fun delete(music: Music)
}

@Dao
interface UserFavoriteMusicDao {
    @Query("SELECT * FROM MusicByUser")
    fun getAll(): Flow<List<MusicByUser>>

    @Query("SELECT * FROM MusicByUser WHERE userId = :userId")
    suspend fun getFavoriteMusicFromUser(userId: Int): MusicByUser?

    @Insert
    suspend fun insert(music: MusicByUser)

    @Delete
    suspend fun delete(music: MusicByUser)


}
package com.example.favoritemusic.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.favoritemusic.model.Users

@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    fun getAll(): Flow<List<Users>>

    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun getUserByEmail(email: String): Users?

    @Insert
    suspend fun insert(user: Users)

    @Delete
    suspend fun delete(user: Users)
}
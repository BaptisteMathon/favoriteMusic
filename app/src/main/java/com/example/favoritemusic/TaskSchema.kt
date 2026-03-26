package com.example.favoritemusic

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Définition de la table

@Entity
data class Music(
    @PrimaryKey(autoGenerate = true) val musicId: Int = 0,
    val titre: String,
    val artiste: String,
    val cover: Int,
)

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val username: String,
    val email: String,
    val password: String
)

//Les requêtes (DAO)

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

// La Database
// Lien entre les entités et le DAO
@Database(entities = [Music::class, Users::class], version = 4)
abstract class AppDb: RoomDatabase() {
    abstract fun daoMusic(): MusicDao
    abstract fun daoUser(): UserDao
}


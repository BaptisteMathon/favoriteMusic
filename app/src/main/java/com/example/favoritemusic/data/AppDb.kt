package com.example.favoritemusic.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.favoritemusic.model.Music
import com.example.favoritemusic.model.MusicByUser
import com.example.favoritemusic.model.Users

@Database(entities = [Music::class, Users::class, MusicByUser::class], version = 5)
abstract class AppDb: RoomDatabase() {
    abstract fun daoMusic(): MusicDao
    abstract fun daoUser(): UserDao

    abstract fun daoFavoriteMusicByUser(): UserFavoriteMusicDao
}
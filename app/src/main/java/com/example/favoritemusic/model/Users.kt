package com.example.favoritemusic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val username: String,
    val email: String,
    val password: String
)
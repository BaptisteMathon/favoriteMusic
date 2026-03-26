package com.example.favoritemusic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Music(
    @PrimaryKey(autoGenerate = true) val musicId: Int = 0,
    val titre: String,
    val artiste: String,
    val cover: Int,
)
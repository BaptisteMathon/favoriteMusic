package com.example.favoritemusic.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.favoritemusic.model.Users

@Entity
data class Music(
    @PrimaryKey(autoGenerate = true) val musicId: Int = 0,
    val titre: String,
    val artiste: String,
    val cover: Int,
)

@Entity (
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MusicByUser(
    @PrimaryKey(autoGenerate = true) val musicId: Int = 0,
    val titre: String,
    val artiste: String,
    val cover: Int,
    val userId: Int
)
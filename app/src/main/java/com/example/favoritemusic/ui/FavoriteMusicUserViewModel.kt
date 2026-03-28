package com.example.favoritemusic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritemusic.data.UserFavoriteMusicDao
import com.example.favoritemusic.model.MusicByUser
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteMusicUserViewModel(private val dao: UserFavoriteMusicDao) : ViewModel() {

    val getAllFavoritesMusicsByUsers: StateFlow<List<MusicByUser>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addFavoriteMusic(title: String, artist: String, cover: Int, userId: Int) {
        if(title.isNotBlank() && artist.isNotBlank() && (cover != 0) && (userId != 0)){
            viewModelScope.launch{
                dao.insert(MusicByUser(titre = title, artiste = artist, cover = cover, userId = userId))
            }
        }
    }

    fun deleteFavoriteMusic(music: MusicByUser) {
        viewModelScope.launch{
            dao.delete(music)
        }
    }
}
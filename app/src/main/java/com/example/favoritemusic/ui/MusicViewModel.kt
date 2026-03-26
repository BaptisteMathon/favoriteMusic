package com.example.favoritemusic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritemusic.data.MusicDao
import com.example.favoritemusic.model.Music
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MusicViewModel(private val dao: MusicDao) : ViewModel() {

    val musics: StateFlow<List<Music>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTask(title: String, artist: String, cover: Int) {
        if(title.isNotBlank()) {
            viewModelScope.launch{
                dao.insert(Music(titre = title, artiste = artist, cover = cover))
            }
        }
    }

    fun deleteTask(music: Music) {
        viewModelScope.launch {
            dao.delete(music)
        }
    }
}
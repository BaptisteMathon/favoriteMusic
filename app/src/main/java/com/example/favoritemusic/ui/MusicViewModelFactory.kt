package com.example.favoritemusic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoritemusic.data.MusicDao

class MusicViewModelFactory(private val dao: MusicDao) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MusicViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MusicViewModel(dao) as T
        }
        throw IllegalArgumentException("Classe ViewModel inconnue")
    }
}
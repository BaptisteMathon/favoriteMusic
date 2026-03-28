package com.example.favoritemusic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoritemusic.data.UserFavoriteMusicDao

class FavoriteMusicUserViewModelFactory (private val dao: UserFavoriteMusicDao): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteMusicUserViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavoriteMusicUserViewModel(dao) as T
        }
        throw IllegalArgumentException("Classe ViewModel inconnue")
    }
}
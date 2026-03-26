package com.example.favoritemusic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritemusic.data.UserDao
import com.example.favoritemusic.model.Users
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val dao: UserDao): ViewModel() {

    val users: StateFlow<List<Users>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    suspend fun getUserByEmail(email: String): Users ? {
        return dao.getUserByEmail(email)
    }

    fun addUser(username: String, email: String, password: String) {
        if(username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch{
                dao.insert(Users(username = username, email = email, password = password))
            }
        }
    }

    fun deleteUser(user: Users) {
        viewModelScope.launch{
            dao.delete(user)
        }
    }
}
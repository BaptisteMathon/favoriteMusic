package com.example.favoritemusic.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritemusic.data.UserDao
import com.example.favoritemusic.model.Users
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.security.MessageDigest
import androidx.compose.runtime.getValue

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
                val hashedPassword = hashPassword(password)

                dao.insert(Users(
                    username = username,
                    email = email,
                    password = hashedPassword
                ))
            }
        }
    }

    fun deleteUser(user: Users) {
        viewModelScope.launch{
            dao.delete(user)
        }
    }

    public fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") {str, it -> str + "%02x".format(it)}
    }

    var currentUser by mutableStateOf<Users?>(null)

}
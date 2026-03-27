package com.example.favoritemusic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.favoritemusic.data.UserDao
import com.example.favoritemusic.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(navController: NavController, viewModel: UserViewModel) {
    var isLoginMode by remember {mutableStateOf(true)}

    val scope = rememberCoroutineScope()

    var username by remember {mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}

    var errorFieldsEmpty by remember {mutableStateOf(false)}
    var errorWrongUser by remember {mutableStateOf(false)}
    var errorUserExist by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(isLoginMode) {

            when (true) {
                errorFieldsEmpty -> {
                    Text(
                        text = "Veuillez renseigné tous les champs",
                        modifier = Modifier
                            .background(Color.Red)
                            .padding(horizontal = 16.dp, vertical = 5.dp),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
                errorWrongUser -> {
                    Text(
                        text = "L'email ou mot de passe incorrect",
                        modifier = Modifier
                            .background(Color.Red)
                            .padding(horizontal = 16.dp, vertical = 5.dp),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                }
                else -> {}
            }

            Text("Connexion : ")

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = {email = it},
                label = { Text("Email")},
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = {password = it},
                label = {Text("Mot de passe")},
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button( onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    scope.launch(Dispatchers.IO) {
//                        val user = daoUser.getUserByEmail(email)
                        val user = viewModel.getUserByEmail(email)

                        if(user != null && user.password == viewModel.hashPassword(password)) {
                            launch(Dispatchers.Main) {
                                viewModel.currentUser = user
                                navController.navigate("home")
                            }
                        } else {
                            errorWrongUser = true
                            errorFieldsEmpty = false
                        }
                    }
                } else {
                    errorFieldsEmpty = true
                    errorWrongUser = false
                }
            }) {
                Text("Se connecter")
            }
        } else {

            when (true) {
                errorFieldsEmpty -> {
                    Text(
                        text = "Veuillez renseigné tous les champs",
                        modifier = Modifier
                            .background(Color.Red)
                            .padding(horizontal = 16.dp, vertical = 5.dp),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
                errorUserExist -> {
                    Text(
                        text = "Un utilisateur existe déjà avec cette adresse mail",
                        modifier = Modifier
                            .background(Color.Red)
                            .padding(horizontal = 16.dp, vertical = 5.dp),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                }
                else -> {}
            }

            Text("Inscription : ")

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = {email = it},
                label = { Text("Email")},
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = username,
                onValueChange = {username = it},
                label = {Text("Nom d'utilisateur")}
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = {password = it},
                label = {Text("Mot de passe")},
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button( onClick = {
                if (email.isNotBlank() && password.isNotBlank() && username.isNotBlank()) {
                    scope.launch{

//                        val user = daoUser.getUserByEmail(email)
                        val existingUser = viewModel.getUserByEmail(email)

                        if(existingUser == null) {
                            val newUser = Users (
                                username = username,
                                email = email,
                                password = password
                            )
                            viewModel.addUser( username,  email,  password)
                            viewModel.currentUser = newUser
                            navController.navigate("home")
                        } else {
                            errorUserExist = true
                            errorFieldsEmpty = false
                        }
                    }
                } else {
                    errorFieldsEmpty = true
                    errorUserExist = false
                }
            }) {
                Text("S'inscrire")
            }
        }

        TextButton( onClick = {isLoginMode = !isLoginMode}) {
            if(isLoginMode) {
                Text("Inscrivez-vous")
            } else {
                Text("Connectez-vous")
            }
        }


    }

}
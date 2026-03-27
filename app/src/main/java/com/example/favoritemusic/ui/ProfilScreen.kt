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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp

@Composable
fun ProfilScreen(viewModel: UserViewModel, navController: NavController) {

    val user = viewModel.currentUser

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profil Pic",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Bonjour, ${user?.username}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column (
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileInfoRow(
                    icon = Icons.Default.Person,
                    label = "Username",
                    value = user?.username ?: "-"
                )

                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

                ProfileInfoRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = user?.email ?: "-"
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.currentUser = null
                navController.navigate("auth")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.error
            ),
            shape = MaterialTheme.shapes.medium
            ) {

            Icon(Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Se déconnecter", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
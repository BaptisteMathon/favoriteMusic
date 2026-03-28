package com.example.favoritemusic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.example.favoritemusic.data.MusicDao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(userViewModel: UserViewModel, allFavoriteMusic: FavoriteMusicUserViewModel) {

    val user = userViewModel.currentUser

    val favoriteList by allFavoriteMusic.getAllFavoritesMusicsByUsers.collectAsState()

    val userFavorites = favoriteList.filter {it.userId == user?.userId}

    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Voici vos musiques préférés ${user?.username}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
           items(userFavorites) { musics ->
               Card (
                   modifier = Modifier.fillMaxSize()
               ) {
                   Row (
                       verticalAlignment = Alignment.CenterVertically,
                   ) {
                       Image(
                           painter = painterResource(id = musics.cover),
                           contentDescription = "MusicCover",
                           modifier = Modifier.size(86.dp)
                       )
                       Column (
                           verticalArrangement = Arrangement.Center,
                           modifier = Modifier.padding(horizontal = 10.dp)
                       ) {
                           Text(
                               text = "${musics.titre}",
                               style = MaterialTheme.typography.headlineSmall,
                               fontWeight = FontWeight.Bold,
                               modifier = Modifier.padding(vertical = 5.dp)
                           )
                           Text("${musics.artiste}")
                       }
                   }

               }
               Spacer(modifier = Modifier.padding(16.dp))
           }
        }
    }
}
package com.example.favoritemusic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.favoritemusic.R
import com.example.favoritemusic.data.MusicDao
import androidx.compose.runtime.*
import com.example.favoritemusic.model.Music

@Composable
fun HomeScreen(navController: NavController, viewModel: MusicViewModel) {

//    val musics by daoMusic.getAll().collectAsState(initial = emptyList())
    val musics by viewModel.musics.collectAsState()

    var currentIndex by remember { mutableIntStateOf(0) }

    var musicTitle = ""
    var musicArtist = ""
    var musicCover = R.drawable.ic_launcher_background

    if (musics.isNotEmpty()){
        musicTitle = musics[currentIndex].titre
        musicArtist = musics[currentIndex].artiste
        musicCover = musics[currentIndex].cover
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = musicCover),
                    contentDescription = "Music Cover",
                    modifier = Modifier
                        .fillMaxWidth(1f)
//                            .fillMaxHeight(0.9f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .shadow(8.dp, RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop,
                )

                Column (
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 40.dp, bottom = 40.dp)
                ) {
                    Text(
                        text = musicTitle,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.Black,
                            shadow = Shadow(color = Color.Black, blurRadius = 10f)
                        ),

                        color = Color.White,
                    )
                    Text(
                        text = musicArtist,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.Black,
                            shadow = Shadow(color = Color.Black, blurRadius = 10f)
                        ),
                        modifier = Modifier.padding(1.dp),
                        color = Color.White,
                    )
                }

            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button( onClick = {
                    if(currentIndex+1 != musics.size) {
                        currentIndex += 1
                    }
                } ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dislike"
                    )
                }

                Button( onClick = {
                    if(currentIndex+1 != musics.size) {
                        currentIndex += 1
                    }
                } ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like"
                    )
                }
            }
        }

    }
}
package com.example.favoritemusic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.favoritemusic.data.MusicDao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: MusicViewModel) {
//    val musics by daoMusic.getAll().collectAsState(initial = emptyList())
    val musics by viewModel.musics.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Toutes les musiques")

        if (musics.isEmpty()) {
            Text("Pas de musique pour le moment")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(musics) { music ->
                    Card (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("${music.titre}")
                        Text("${music.artiste}")
                        Image(
                            painter = painterResource(id = music.cover),
                            contentDescription = "Music Cover",
                        )
                    }
                }
            }
        }
    }
}
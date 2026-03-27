package com.example.favoritemusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.favoritemusic.ui.theme.FavoriteMusicTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.favoritemusic.model.Music
import com.example.favoritemusic.model.Users
import com.example.favoritemusic.data.AppDb
import com.example.favoritemusic.data.MusicDao
import com.example.favoritemusic.data.UserDao
import com.example.favoritemusic.ui.HomeScreen
import com.example.favoritemusic.ui.DetailScreen
import com.example.favoritemusic.ui.AuthScreen
import com.example.favoritemusic.ui.MusicViewModel
import com.example.favoritemusic.ui.MusicViewModelFactory
import com.example.favoritemusic.ui.ProfilScreen
import com.example.favoritemusic.ui.UserViewModel
import com.example.favoritemusic.ui.UserViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation de la base de données
        val db = Room.databaseBuilder(applicationContext, AppDb::class.java, "db")
            .fallbackToDestructiveMigration()
            .build()
        val musicDao = db.daoMusic()
        val userDao = db.daoUser()

        val viewModelMusic : MusicViewModel by viewModels { MusicViewModelFactory(musicDao) }
        val viewModelUser : UserViewModel by viewModels { UserViewModelFactory(userDao) }

        lifecycleScope.launch(Dispatchers.IO){
            try {
                val listMusic = musicDao.getAll().first()

                if(listMusic.isEmpty()){
                    musicDao.insert(Music(titre = "Starboy", artiste = "The Weeknd", cover = R.drawable.starboy))
                    musicDao.insert(Music(titre = "Immortel", artiste = "Bushi", cover = R.drawable.immortel))
                    musicDao.insert(Music(titre = "Le bruit et le silence", artiste = "NeS", cover = R.drawable.nes))
                    musicDao.insert(Music(titre = "GMK", artiste = "WIKO & LA2S", cover = R.drawable.gmk))
                    musicDao.insert(Music(titre = "Encore une fois", artiste = "Orelsan, Yamê", cover = R.drawable.encore))
                    musicDao.insert(Music(titre = "Terrain", artiste = "Nono La Grinta", cover = R.drawable.terrain))
                    musicDao.insert(Music(titre = "Eternelle 2", artiste = "So La Lune", cover = R.drawable.eternelle))
                    musicDao.insert(Music(titre = "OG", artiste = "Genezio, Niska", cover = R.drawable.og))
                    musicDao.insert(Music(titre = "B.M.S", artiste = "Rambo goyard", cover = R.drawable.bms))
                    musicDao.insert(Music(titre = "melodrama", artiste = "disiz, Theodora", cover = R.drawable.melodrama))
                    musicDao.insert(Music(titre = "LOVE YOU", artiste = "Nono La Grinta", cover = R.drawable.loveyou))
                    musicDao.insert(Music(titre = "RUINART", artiste = "R2", cover = R.drawable.ruinart))
                    musicDao.insert(Music(titre = "Génération impolie", artiste = "Franglish, Keblack", cover = R.drawable.generation))
                    musicDao.insert(Music(titre = "NUEVAYoL", artiste = "Bad Bunny", cover = R.drawable.badbuny))
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }

        }

        enableEdgeToEdge()
        setContent {
            FavoriteMusicTheme {
                    MyApp(viewModelMusic, viewModelUser)
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(musicViewModel: MusicViewModel, userViewModel: UserViewModel) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorite Music") })
        },
        bottomBar = {
            if(currentRoute != "auth"){
                BottomAppBar {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button( onClick = {navController.navigate("home")} ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Accueil"
                            )
                        }

                        Button( onClick = { navController.navigate("detail")} ) {
                            Icon (
                                imageVector = Icons.Default.List,
                                contentDescription = "Library"
                            )
                        }

                        Button( onClick = { navController.navigate("profil") }) {
                            Icon (
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profil"
                            )
                        }
                    }

                }
            }

        }
    )
    { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "auth",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(navController, musicViewModel)
            }
            composable("detail") {
                DetailScreen(musicViewModel)
            }
            composable("auth") {
                AuthScreen(navController, userViewModel)
            }
            composable("profil") {
                ProfilScreen(userViewModel, navController)
            }
        }

//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(innerPadding)
//        ) {
//
//        }
    }

}

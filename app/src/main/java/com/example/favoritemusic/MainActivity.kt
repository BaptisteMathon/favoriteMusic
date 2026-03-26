package com.example.favoritemusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.favoritemusic.ui.theme.FavoriteMusicTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation de la base de données
        val db = Room.databaseBuilder(applicationContext, AppDb::class.java, "db")
            .fallbackToDestructiveMigration()
            .build()
        val musicDao = db.daoMusic()
        val userDao = db.daoUser()

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
                    MyApp(musicDao, userDao)
            }
        }

    }
}
@Composable
fun HomeScreen(navController: NavController, daoMusic: MusicDao) {

    val musics by daoMusic.getAll().collectAsState(initial = emptyList())

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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(daoMusic: MusicDao) {
    val musics by daoMusic.getAll().collectAsState(initial = emptyList())

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

@Composable
fun AuthScreen(navController: NavController, daoUser: UserDao) {
    var isLoginMode by remember {mutableStateOf(true)}

    val scope = rememberCoroutineScope()

    var username by remember {mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}

    var error1 by remember {mutableStateOf(false)}
    var error2 by remember {mutableStateOf(false)}
    var error3 by remember {mutableStateOf(false)}
    var error4 by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(isLoginMode) {

            when (true) {
                error1 -> {
                    Text(
                        text = "Veuillez renseigné tous les champs",
                        modifier = Modifier
                            .background(Color.Red)
                            .padding(horizontal = 16.dp, vertical = 5.dp),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
                error2 -> {
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
                        val user = daoUser.getUserByEmail(email)

                        if(user != null && user.password == password) {
                            launch(Dispatchers.Main) {
                                navController.navigate("home")
                            }
                        } else {
                            error2 = true
                            error1 = false
                        }
                    }
                } else {
                    error1 = true
                    error2 = false
                }
            }) {
                Text("Se connecter")
            }
        } else {

            when (true) {
                error3 -> {
                    Text(
                        text = "Veuillez renseigné tous les champs",
                        modifier = Modifier
                            .background(Color.Red)
                            .padding(horizontal = 16.dp, vertical = 5.dp),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
                error4 -> {
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

                        val user = daoUser.getUserByEmail(email)

                        if(user == null) {
                            daoUser.insert(Users(username = username, email = email, password = password))
                            navController.navigate("home")
                        } else {
                            error4 = true
                            error3 = false
                        }
                    }
                } else {
                    error3 = true
                    error4 = false
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(daoMusic: MusicDao, daoUser: UserDao) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorite Music") })
        },
        bottomBar = {
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

                    Button( onClick = {}) {
                        Icon (
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profil"
                        )
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
                HomeScreen(navController, daoMusic)
            }
            composable("detail") {
                DetailScreen(daoMusic)
            }
            composable("auth") {
                AuthScreen(navController, daoUser)
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

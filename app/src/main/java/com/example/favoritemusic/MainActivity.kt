package com.example.favoritemusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.favoritemusic.ui.theme.FavoriteMusicTheme


data class Chanson(
    val id: Int,
    val titre: String,
    val Artiste: String,
    val imagesRes: String
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FavoriteMusicTheme {
                    MyApp()
            }
        }

    }
}
@Composable
fun HomeScreen(navController: NavController) {

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card (
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Music Cover",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.9f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .shadow(8.dp, RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop,
                )

                Column (
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 40.dp, bottom = 60.dp)
                ) {
                    Text(
                        text = "Titre de la musique",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                    )
                    Text(
                        text = "Artiste",
                        style = MaterialTheme.typography.titleMedium,
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
                Button( onClick = { } ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dislike"
                    )
                }

                Button( onClick = {} ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like"
                    )
                }
            }
        }

    }
//        Button(
//            onClick = {
//                navController.navigate("detail")
//            },
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            Text("Go to Detail")
//        }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My App") })
        }
    )
    {
            innerPadding ->
        Text("This is Detail Screen", modifier = Modifier.padding(innerPadding))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorite Music") })
        },
        bottomBar = {
            BottomAppBar() {
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
                }

            }
        }
    )
    { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(navController)
            }
            composable("detail") {
                DetailScreen()
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

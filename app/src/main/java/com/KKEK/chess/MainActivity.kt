package com.KKEK.chess

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.KKEK.chess.ui.theme.ChessTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.KKEK.chess.ui.GameViewModel
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAnalytics = Firebase.analytics
        super.onCreate(savedInstanceState)
        if (Firebase.auth.currentUser == null) Firebase.auth.signInAnonymously()
        Firebase.database.getReference(Firebase.auth.currentUser?.uid.toString()).setValue("online")
        enableEdgeToEdge()
        setContent {
            ChessTheme {
                GameScreen()
            }
        }
    }

    override fun onStop() {
        Firebase.database.getReference(Firebase.auth.currentUser!!.uid).setValue("offline")
        super.onStop()
    }

    override fun onRestart() {
        Firebase.database.getReference(Firebase.auth.currentUser!!.uid).setValue("online")
        super.onRestart()
    }
    override fun onPause() {
        Firebase.database.getReference(Firebase.auth.currentUser!!.uid).setValue("offline")
        super.onPause()
    }
    override fun onStart() {
        Firebase.database.getReference(Firebase.auth.currentUser!!.uid).setValue("online")
        super.onStart()
    }
    override fun onDestroy() {
        Firebase.database.getReference(Firebase.auth.currentUser!!.uid).setValue("offline")
        super.onDestroy()
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun Online(
//    game: GameViewModel = viewModel()
//) {
//    val gameUiState by game.uiState.collectAsState()
//    Scaffold(
//        Modifier.fillMaxSize()
//    ) {
//        Column(
//            Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            var inputCode by remember { mutableStateOf("") }
//            Text(text = gameUiState.currentPlayer.toString())
//            Text(text = gameUiState.ConnectionCode.toString())
//            IconButton(onClick = {
//            game.updateBoard() }) {
//                Icons.Default.Refresh
//            }
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Button(onClick = { game.invitePlayer("B") }
//                ) {
//                    Text(text = "White")
//                }
//                Button(onClick = { game.invitePlayer("W") }
//                ) {
//                    Text(text = "Black")
//                }
//            }
//            TextField(value = inputCode, onValueChange = { inputCode = it })
//            Button(onClick = { game.acceptInvite(inputCode) }) {
//                Text(text = "Accept")
//            }
//            ChessBoard()
//
//        }
////        if (gameUiState.ConnectionCode != null && !gameUiState.isConnected){
////            BasicAlertDialog(onDismissRequest = { game.cancelConnection() }) {
////                    Surface(
////                        shape = MaterialTheme.shapes.large,
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .padding(16.dp)
////                            .aspectRatio(5f)
////                    ) {
////                        Column(
////                            horizontalAlignment = Alignment.CenterHorizontally,
////                            verticalArrangement = Arrangement.Center
////                        ) {
////                            Text(text = "${gameUiState.ConnectionCode}")
////                        }
////                    }
////            }
////        }
//    }
//}
//
//

package com.kkek.chess

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

val data = mutableListOf<String>()
val lightcolor = Color.White
val darkcolor = Color.Black
val chess = Chess()

private val img = mapOf(
    Peice.King to R.drawable.king_w,
    Peice.Queen to R.drawable.queen_w,
    Peice.Rook to R.drawable.rook_w,
    Peice.Bishop to R.drawable.bishop_w,
    Peice.Knight to R.drawable.knight_w,
    Peice.Pawn to R.drawable.pawn_w,
    Peice.NONE to R.drawable.none
)

/**
 * Composable function to display the chessboard.
 */
@Composable
fun Board() {
    Column(
        Modifier.wrapContentSize(
            Alignment.Center
        )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(64) { index ->
                val row = index / 8 + 1
                val col = index % 8 + 1
                val bg = if ((row + col) % 2 == 0) lightcolor else darkcolor
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(bg)
                ) {
                    Text(text = "$row$col")
                    val piece = chess.pos["$row$col"]
                    piece?.let {
                        val imageRes = img[it] ?: R.drawable.none
                        Image(painter = painterResource(id = imageRes), contentDescription = "")
                    }
                }
            }
        }
    }
}

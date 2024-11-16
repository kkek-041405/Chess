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
    Piece.PieceType.KING to R.drawable.king_w,
    Piece.PieceType.QUEEN to R.drawable.queen_w,
    Piece.PieceType.ROOK to R.drawable.rook_w,
    Piece.PieceType.BISHOP to R.drawable.bishop_w,
    Piece.PieceType.KNIGHT to R.drawable.knight_w,
    Piece.PieceType.PAWN to R.drawable.pawn_w,
    Piece.PieceType.NONE to R.drawable.ic_launcher_background
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
                        val imageRes = img[it.type] ?: R.drawable.ic_launcher_background
                        Image(painter = painterResource(id = imageRes), contentDescription = "")
                    }
                }
            }
        }
    }
}

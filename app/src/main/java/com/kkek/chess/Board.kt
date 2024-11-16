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
    Piece.PieceType.KING to Pair(R.drawable.king_w, R.drawable.king_b),
    Piece.PieceType.QUEEN to Pair(R.drawable.queen_w, R.drawable.queen_b),
    Piece.PieceType.ROOK to Pair(R.drawable.rook_w, R.drawable.rook_b),
    Piece.PieceType.BISHOP to Pair(R.drawable.bishop_w, R.drawable.bishop_b),
    Piece.PieceType.KNIGHT to Pair(R.drawable.knight_w, R.drawable.knight_b),
    Piece.PieceType.PAWN to Pair(R.drawable.pawn_w, R.drawable.pawn_b),
    Piece.PieceType.NONE to Pair(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background)
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
                        val imageRes = if (it.color == Piece.PieceColor.WHITE) {
                            img[it.type]?.first ?: R.drawable.ic_launcher_background
                        } else {
                            img[it.type]?.second ?: R.drawable.ic_launcher_background
                        }
                        Image(painter = painterResource(id = imageRes), contentDescription = "")
                    }
                }
            }
        }
    }
}

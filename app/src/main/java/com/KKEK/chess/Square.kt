package com.KKEK.chess

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Square(
    val row: Int,
    val col: Int,
    var piece: MutableState<Piece> = mutableStateOf(Piece(PieceType.None, PieceColor.None))
)

data class Piece(
    val type: PieceType,
    val color: PieceColor
)

enum class PieceType { PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING, None }
enum class PieceColor { WHITE, BLACK,None }

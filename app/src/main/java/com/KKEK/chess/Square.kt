package com.KKEK.chess

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Data class representing a square on the chessboard.
 * @param row The row index of the square.
 * @param col The column index of the square.
 * @param piece The piece currently on the square.
 */
data class Square(
    val row: Int,
    val col: Int,
    var piece: MutableState<Piece> = mutableStateOf(Piece(PieceType.None, PieceColor.None))
)

/**
 * Data class representing a chess piece.
 * @param type The type of the piece.
 * @param color The color of the piece.
 */
data class Piece(
    val type: PieceType,
    val color: PieceColor
)

/**
 * Enum class representing the different types of chess pieces.
 */
enum class PieceType { PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING, None }

/**
 * Enum class representing the different colors of chess pieces.
 */
enum class PieceColor { WHITE, BLACK, None }

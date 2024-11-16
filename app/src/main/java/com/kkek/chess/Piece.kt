package com.kkek.chess

class Piece(val type: PieceType, var position: String, val color: PieceColor) {

    enum class PieceType {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN, NONE
    }

    enum class PieceColor {
        WHITE, BLACK, NONE
    }

    fun move(newPosition: String) {
        position = newPosition
    }
}

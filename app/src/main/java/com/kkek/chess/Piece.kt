package com.kkek.chess

class Piece(val type: PieceType, var position: String) {

    enum class PieceType {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN, NONE
    }

    fun move(newPosition: String) {
        position = newPosition
    }
}

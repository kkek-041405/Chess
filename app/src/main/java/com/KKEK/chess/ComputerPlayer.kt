package com.KKEK.chess

import kotlin.random.Random

class ComputerPlayer {

    fun makeMove(gameViewModel: GameViewModel) {
        val validMoves = mutableListOf<Pair<Square, Square>>()

        for (row in 0..7) {
            for (col in 0..7) {
                val startSquare = gameViewModel.board[row][col]
                if (startSquare.piece.value.color == PieceColor.BLACK) {
                    for (endRow in 0..7) {
                        for (endCol in 0..7) {
                            val endSquare = gameViewModel.board[endRow][endCol]
                            if (gameViewModel.isValidMove(startSquare, endSquare, PieceColor.BLACK)) {
                                validMoves.add(Pair(startSquare, endSquare))
                            }
                        }
                    }
                }
            }
        }

        if (validMoves.isNotEmpty()) {
            val randomMove = validMoves[Random.nextInt(validMoves.size)]
            gameViewModel.movePiece(randomMove.first, randomMove.second)
        }
    }
}

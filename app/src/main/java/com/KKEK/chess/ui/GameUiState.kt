package com.KKEK.chess.ui

import com.KKEK.chess.Piece

data class GameUiState(
    val isLoggedIn: Boolean = false,
    var ConnectionCode: String? = null,
    val isConnected: Boolean = false,
    val currentPlayer: Int = 6, // 3->white 2->Black 6->Both
    val knockedPieces_W: List<Piece> = mutableListOf(),
    val knockedPieces_B: List<Piece> = mutableListOf(),
)

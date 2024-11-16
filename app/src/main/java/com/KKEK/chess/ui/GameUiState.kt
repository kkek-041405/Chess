package com.KKEK.chess.ui

import com.KKEK.chess.Piece

/**
 * Data class representing the UI state of the game.
 * @param isLoggedIn Indicates if the user is logged in.
 * @param ConnectionCode The connection code for the game.
 * @param isConnected Indicates if the game is connected.
 * @param currentPlayer The current player (3->white, 2->Black, 6->Both).
 * @param knockedPieces_W List of knocked pieces for the white player.
 * @param knockedPieces_B List of knocked pieces for the black player.
 */
data class GameUiState(
    val isLoggedIn: Boolean = false,
    var ConnectionCode: String? = null,
    val isConnected: Boolean = false,
    val currentPlayer: Int = 6, // 3->white 2->Black 6->Both
    val knockedPieces_W: List<Piece> = mutableListOf(),
    val knockedPieces_B: List<Piece> = mutableListOf(),
)

package com.KKEK.chess

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testIsValidMove() {
        val gameViewModel = GameViewModel()
        val startSquare = Square(1, 0, mutableStateOf(Piece(PieceType.PAWN, PieceColor.BLACK)))
        val endSquare = Square(2, 0, mutableStateOf(Piece(PieceType.None, PieceColor.None)))
        assertTrue(gameViewModel.isValidMove(startSquare, endSquare, PieceColor.BLACK))
    }

    @Test
    fun testMovePiece() {
        val gameViewModel = GameViewModel()
        val startSquare = Square(1, 0, mutableStateOf(Piece(PieceType.PAWN, PieceColor.BLACK)))
        val endSquare = Square(2, 0, mutableStateOf(Piece(PieceType.None, PieceColor.None)))
        assertTrue(gameViewModel.movePiece(startSquare, endSquare))
        assertEquals(PieceType.PAWN, endSquare.piece.value.type)
        assertEquals(PieceType.None, startSquare.piece.value.type)
    }

    @Test
    fun testIsCheck() {
        val gameViewModel = GameViewModel()
        val kingSquare = gameViewModel.board[0][4]
        kingSquare.piece.value = Piece(PieceType.KING, PieceColor.BLACK)
        val attackingSquare = gameViewModel.board[1][4]
        attackingSquare.piece.value = Piece(PieceType.ROOK, PieceColor.WHITE)
        assertTrue(gameViewModel.isCheck(PieceColor.BLACK))
    }

    @Test
    fun testIsCheckmate() {
        val gameViewModel = GameViewModel()
        val kingSquare = gameViewModel.board[0][4]
        kingSquare.piece.value = Piece(PieceType.KING, PieceColor.BLACK)
        val attackingSquare1 = gameViewModel.board[1][4]
        attackingSquare1.piece.value = Piece(PieceType.ROOK, PieceColor.WHITE)
        val attackingSquare2 = gameViewModel.board[1][5]
        attackingSquare2.piece.value = Piece(PieceType.ROOK, PieceColor.WHITE)
        assertTrue(gameViewModel.isCheckmate(PieceColor.BLACK))
    }

    @Test
    fun testIsStalemate() {
        val gameViewModel = GameViewModel()
        val kingSquare = gameViewModel.board[0][4]
        kingSquare.piece.value = Piece(PieceType.KING, PieceColor.BLACK)
        val blockingSquare = gameViewModel.board[1][4]
        blockingSquare.piece.value = Piece(PieceType.PAWN, PieceColor.BLACK)
        assertTrue(gameViewModel.isStalemate(PieceColor.BLACK))
    }

    @Test
    fun testCanPromote() {
        val gameViewModel = GameViewModel()
        val pawnSquare = gameViewModel.board[0][0]
        pawnSquare.piece.value = Piece(PieceType.PAWN, PieceColor.WHITE)
        assertTrue(gameViewModel.canPromote())
    }

    @Test
    fun testPromote() {
        val gameViewModel = GameViewModel()
        val pawnSquare = gameViewModel.board[0][0]
        pawnSquare.piece.value = Piece(PieceType.PAWN, PieceColor.WHITE)
        gameViewModel.promote(PieceType.QUEEN)
        assertEquals(PieceType.QUEEN, pawnSquare.piece.value.type)
    }

    @Test
    fun testInvitePlayer() {
        val gameViewModel = GameViewModel()
        gameViewModel.invitePlayer("W")
        assertNotNull(gameViewModel.uiState.value.ConnectionCode)
    }

    @Test
    fun testAcceptInvite() {
        val gameViewModel = GameViewModel()
        gameViewModel.acceptInvite("1234567")
        assertTrue(gameViewModel.uiState.value.isConnected)
    }

    @Test
    fun testReset() {
        val gameViewModel = GameViewModel()
        gameViewModel.reset()
        assertEquals(PieceType.PAWN, gameViewModel.board[1][0].piece.value.type)
        assertEquals(PieceType.PAWN, gameViewModel.board[6][0].piece.value.type)
        assertEquals(PieceType.ROOK, gameViewModel.board[0][0].piece.value.type)
        assertEquals(PieceType.ROOK, gameViewModel.board[7][0].piece.value.type)
    }
}

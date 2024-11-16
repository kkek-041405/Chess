package com.kkek.chess

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ChessTest {

    private lateinit var chess: Chess

    @Before
    fun setUp() {
        chess = Chess()
    }

    @Test
    fun testInitialSetup() {
        val expectedInitialSetup = mapOf(
            "11" to Piece(Piece.PieceType.ROOK, "11"),
            "81" to Piece(Piece.PieceType.ROOK, "81"),
            "88" to Piece(Piece.PieceType.ROOK, "88"),
            "18" to Piece(Piece.PieceType.ROOK, "18")
        )
        for ((position, piece) in expectedInitialSetup) {
            assertEquals(piece.type, chess.pos[position]?.type)
        }
    }

    @Test
    fun testReset() {
        chess.reset()
        val expectedResetSetup = mapOf(
            "11" to Piece(Piece.PieceType.ROOK, "11"),
            "81" to Piece(Piece.PieceType.ROOK, "81"),
            "88" to Piece(Piece.PieceType.ROOK, "88"),
            "18" to Piece(Piece.PieceType.ROOK, "18")
        )
        for ((position, piece) in expectedResetSetup) {
            assertEquals(piece.type, chess.pos[position]?.type)
        }
    }

    @Test
    fun testPlacePiece() {
        val piece = Piece(Piece.PieceType.KING, "55")
        chess.placePiece("55", piece)
        assertEquals(piece.type, chess.pos["55"]?.type)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testPlacePieceInvalidPosition() {
        val piece = Piece(Piece.PieceType.KING, "99")
        chess.placePiece("99", piece)
    }
}

class PieceTest {

    @Test
    fun testMove() {
        val piece = Piece(Piece.PieceType.QUEEN, "22")
        piece.move("33")
        assertEquals("33", piece.position)
    }

    @Test
    fun testPieceType() {
        val piece = Piece(Piece.PieceType.BISHOP, "44")
        assertEquals(Piece.PieceType.BISHOP, piece.type)
    }
}

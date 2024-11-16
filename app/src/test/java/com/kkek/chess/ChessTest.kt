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
            "11" to Peice.Rook,
            "81" to Peice.Rook,
            "88" to Peice.Rook,
            "18" to Peice.Rook
        )
        for ((position, piece) in expectedInitialSetup) {
            assertEquals(piece, chess.pos[position])
        }
    }

    @Test
    fun testReset() {
        chess.reset()
        val expectedResetSetup = mapOf(
            "11" to Peice.Rook,
            "81" to Peice.Rook,
            "88" to Peice.Rook,
            "18" to Peice.Rook
        )
        for ((position, piece) in expectedResetSetup) {
            assertEquals(piece, chess.pos[position])
        }
    }
}

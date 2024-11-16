package com.kkek.chess;

import java.util.HashMap;
import java.util.Map;

public class Chess {
    Map<String, Peice> pos = new HashMap<String, Peice>();

    /**
     * Constructor for the Chess class.
     * Initializes the chessboard with empty positions and sets up the initial pieces.
     */
    public Chess() {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                pos.put("" + i + j, Peice.NONE);
            }
        }
        reset();
    }

    /**
     * Resets the chessboard to the initial setup.
     * Places the rooks in their starting positions.
     */
    public void reset() {
        pos.put("11", Peice.Rook);
        pos.put("81", Peice.Rook);
        pos.put("88", Peice.Rook);
        pos.put("18", Peice.Rook);
        System.out.println(pos);
    }

    /**
     * Validates the position on the chessboard.
     * Ensures that the position is within the valid range (1-8 for both rows and columns).
     *
     * @param position The position to validate.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValidPosition(String position) {
        if (position == null || position.length() != 2) {
            return false;
        }
        char row = position.charAt(0);
        char col = position.charAt(1);
        return row >= '1' && row <= '8' && col >= '1' && col <= '8';
    }

    /**
     * Places a piece on the chessboard at the specified position.
     * Validates the position before placing the piece.
     *
     * @param position The position to place the piece.
     * @param piece    The piece to place.
     * @throws IllegalArgumentException if the position is invalid.
     */
    public void placePiece(String position, Peice piece) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
        pos.put(position, piece);
    }
}

enum Peice {
    King,
    Queen,
    Rook,
    Bishop,
    Knight,
    Pawn,
    NONE
}

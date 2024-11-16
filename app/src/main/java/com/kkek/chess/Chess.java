package com.kkek.chess;

import java.util.HashMap;
import java.util.Map;

public class Chess {
    Map<String, Piece> pos = new HashMap<String, Piece>();

    /**
     * Constructor for the Chess class.
     * Initializes the chessboard with empty positions and sets up the initial pieces.
     */
    public Chess() {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                pos.put("" + i + j, new Piece(Piece.PieceType.NONE, "" + i + j, Piece.PieceColor.NONE));
            }
        }
        reset();
    }

    /**
     * Resets the chessboard to the initial setup.
     * Places the rooks in their starting positions.
     */
    public void reset() {
        pos.put("11", new Piece(Piece.PieceType.ROOK, "11", Piece.PieceColor.WHITE));
        pos.put("81", new Piece(Piece.PieceType.ROOK, "81", Piece.PieceColor.WHITE));
        pos.put("88", new Piece(Piece.PieceType.ROOK, "88", Piece.PieceColor.BLACK));
        pos.put("18", new Piece(Piece.PieceType.ROOK, "18", Piece.PieceColor.BLACK));
        pos.put("12", new Piece(Piece.PieceType.KNIGHT, "12", Piece.PieceColor.WHITE));
        pos.put("82", new Piece(Piece.PieceType.KNIGHT, "82", Piece.PieceColor.WHITE));
        pos.put("87", new Piece(Piece.PieceType.KNIGHT, "87", Piece.PieceColor.BLACK));
        pos.put("17", new Piece(Piece.PieceType.KNIGHT, "17", Piece.PieceColor.BLACK));
        pos.put("13", new Piece(Piece.PieceType.BISHOP, "13", Piece.PieceColor.WHITE));
        pos.put("83", new Piece(Piece.PieceType.BISHOP, "83", Piece.PieceColor.WHITE));
        pos.put("86", new Piece(Piece.PieceType.BISHOP, "86", Piece.PieceColor.BLACK));
        pos.put("16", new Piece(Piece.PieceType.BISHOP, "16", Piece.PieceColor.BLACK));
        pos.put("14", new Piece(Piece.PieceType.QUEEN, "14", Piece.PieceColor.WHITE));
        pos.put("84", new Piece(Piece.PieceType.QUEEN, "84", Piece.PieceColor.BLACK));
        pos.put("15", new Piece(Piece.PieceType.KING, "15", Piece.PieceColor.WHITE));
        pos.put("85", new Piece(Piece.PieceType.KING, "85", Piece.PieceColor.BLACK));
        for (int i = 1; i < 9; i++) {
            pos.put("2" + i, new Piece(Piece.PieceType.PAWN, "2" + i, Piece.PieceColor.WHITE));
            pos.put("7" + i, new Piece(Piece.PieceType.PAWN, "7" + i, Piece.PieceColor.BLACK));
        }
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
    public void placePiece(String position, Piece piece) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
        pos.put(position, piece);
    }
}

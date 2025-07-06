package com.chinesechess;

public interface MoveStrategy {
    boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board);
} 
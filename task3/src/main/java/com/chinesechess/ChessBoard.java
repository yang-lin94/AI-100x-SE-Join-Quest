package com.chinesechess;

public class ChessBoard {
    private ChessPiece[][] board;
    private static final int ROWS = 10;
    private static final int COLS = 9;
    
    public ChessBoard() {
        this.board = new ChessPiece[ROWS][COLS];
    }
    
    public void placePiece(ChessPiece piece, int row, int col) {
        if (isValidPosition(row, col)) {
            board[row - 1][col - 1] = piece;
        }
    }
    
    public ChessPiece getPiece(int row, int col) {
        if (isValidPosition(row, col)) {
            return board[row - 1][col - 1];
        }
        return null;
    }
    
    public void removePiece(int row, int col) {
        if (isValidPosition(row, col)) {
            board[row - 1][col - 1] = null;
        }
    }
    
    public boolean isEmpty(int row, int col) {
        return getPiece(row, col) == null;
    }
    
    public boolean isValidPosition(int row, int col) {
        return row >= 1 && row <= ROWS && col >= 1 && col <= COLS;
    }
    
    public void clearBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = null;
            }
        }
    }
} 
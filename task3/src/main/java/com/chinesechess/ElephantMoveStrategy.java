package com.chinesechess;

public class ElephantMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board) {
        // 檢查是否為2格對角線移動
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        if (rowDiff != 2 || colDiff != 2) {
            return false;
        }
        
        // 檢查是否跨河
        if (piece.isRed() && toRow > 5) {
            return false;
        }
        if (piece.isBlack() && toRow < 6) {
            return false;
        }
        
        // 檢查象眼是否被阻擋
        int midRow = (fromRow + toRow) / 2;
        int midCol = (fromCol + toCol) / 2;
        
        return board.getPiece(midRow, midCol) == null;
    }
} 
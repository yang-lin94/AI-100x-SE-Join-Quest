package com.chinesechess;

public class GuardMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board) {
        // 檢查是否在九宮格內移動
        if (!isInPalace(toRow, toCol, piece.getColor())) {
            return false;
        }
        
        // 檢查是否只移動一格且為斜線移動
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        // 士只能斜向移動一格
        return rowDiff == 1 && colDiff == 1;
    }
    
    private boolean isInPalace(int row, int col, String color) {
        if ("Red".equals(color)) {
            // 紅方九宮格：行1-3，列4-6
            return row >= 1 && row <= 3 && col >= 4 && col <= 6;
        } else {
            // 黑方九宮格：行8-10，列4-6
            return row >= 8 && row <= 10 && col >= 4 && col <= 6;
        }
    }
} 
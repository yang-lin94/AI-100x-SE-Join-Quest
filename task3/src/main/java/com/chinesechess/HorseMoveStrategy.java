package com.chinesechess;

public class HorseMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board) {
        // 檢查是否為"日"字形移動
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        if (!((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2))) {
            return false;
        }
        
        // 檢查馬腳是否被阻擋
        int blockRow, blockCol;
        if (rowDiff == 2) {
            blockRow = fromRow + (toRow > fromRow ? 1 : -1);
            blockCol = fromCol;
        } else {
            blockRow = fromRow;
            blockCol = fromCol + (toCol > fromCol ? 1 : -1);
        }
        
        return board.getPiece(blockRow, blockCol) == null;
    }
} 
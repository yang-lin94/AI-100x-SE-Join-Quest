package com.chinesechess;

public class RookMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board) {
        // 檢查是否為直線移動（橫向或縱向）
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }
        
        // 檢查路徑上是否有阻擋
        return isPathClear(fromRow, fromCol, toRow, toCol, board);
    }
    
    private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board) {
        int rowStep = 0;
        int colStep = 0;
        
        // 確定移動方向
        if (fromRow != toRow) {
            rowStep = (toRow > fromRow) ? 1 : -1;
        }
        if (fromCol != toCol) {
            colStep = (toCol > fromCol) ? 1 : -1;
        }
        
        // 檢查路徑上的每個位置
        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;
        
        while (currentRow != toRow || currentCol != toCol) {
            if (board.getPiece(currentRow, currentCol) != null) {
                return false; // 路徑被阻擋
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        
        return true; // 路徑暢通
    }
} 
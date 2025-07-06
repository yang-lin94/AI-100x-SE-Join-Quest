package com.chinesechess;

public class CannonMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board) {
        // 檢查是否為直線移動
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }
        
        // 計算路徑上的棋子數量
        int pieceCount = countPiecesInPath(fromRow, fromCol, toRow, toCol, board);
        
        // 如果目標位置有棋子，需要正好一個砲架
        ChessPiece targetPiece = board.getPiece(toRow, toCol);
        if (targetPiece != null) {
            return pieceCount == 1;
        } else {
            return pieceCount == 0;
        }
    }
    
    private int countPiecesInPath(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board) {
        int count = 0;
        int rowStep = 0;
        int colStep = 0;
        
        if (fromRow != toRow) {
            rowStep = (toRow > fromRow) ? 1 : -1;
        }
        if (fromCol != toCol) {
            colStep = (toCol > fromCol) ? 1 : -1;
        }
        
        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;
        
        while (currentRow != toRow || currentCol != toCol) {
            if (board.getPiece(currentRow, currentCol) != null) {
                count++;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        
        return count;
    }
} 
package com.chinesechess;

public class SoldierMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board) {
        int rowDiff = toRow - fromRow;
        int colDiff = Math.abs(toCol - fromCol);
        
        if (piece.isRed()) {
            // 紅兵只能向前移動或橫向移動（過河後）
            if (rowDiff < 0 || (rowDiff == 0 && colDiff == 0)) {
                return false; // 不能向後移動或不移動
            }
            
            // 只能移動一格
            if (rowDiff > 1 || colDiff > 1) {
                return false;
            }
            
            // 不能同時向前和橫向移動
            if (rowDiff > 0 && colDiff > 0) {
                return false;
            }
            
            // 過河前只能直走
            if (fromRow <= 5 && colDiff != 0) {
                return false;
            }
            
            return true;
        } else {
            // 黑兵只能向前移動（向下）或橫向移動（過河後）
            if (rowDiff > 0 || (rowDiff == 0 && colDiff == 0)) {
                return false; // 不能向後移動或不移動
            }
            
            // 只能移動一格
            if (Math.abs(rowDiff) > 1 || colDiff > 1) {
                return false;
            }
            
            // 不能同時向前和橫向移動
            if (rowDiff < 0 && colDiff > 0) {
                return false;
            }
            
            // 過河前只能直走
            if (fromRow >= 6 && colDiff != 0) {
                return false;
            }
            
            return true;
        }
    }
} 
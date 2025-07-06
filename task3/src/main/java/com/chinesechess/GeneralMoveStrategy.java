package com.chinesechess;

public class GeneralMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board) {
        // 檢查是否在九宮格內移動
        if (!isInPalace(toRow, toCol, piece.getColor())) {
            return false;
        }
        
        // 檢查是否只移動一格且為直線移動（不能斜移）
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
            // 檢查是否會導致兩個將軍面對面
            return !wouldGeneralsFaceEachOther(fromRow, fromCol, toRow, toCol, piece, board);
        }
        
        return false;
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
    
    private boolean wouldGeneralsFaceEachOther(int fromRow, int fromCol, int toRow, int toCol, ChessPiece piece, ChessBoard board) {
        // 暫時移動棋子來檢查
        ChessPiece originalTargetPiece = board.getPiece(toRow, toCol);
        board.removePiece(fromRow, fromCol);
        board.placePiece(piece, toRow, toCol);
        
        boolean faceEachOther = checkGeneralsFaceEachOther(board);
        
        // 恢復棋盤狀態
        board.removePiece(toRow, toCol);
        board.placePiece(piece, fromRow, fromCol);
        if (originalTargetPiece != null) {
            board.placePiece(originalTargetPiece, toRow, toCol);
        }
        
        return faceEachOther;
    }
    
    private boolean checkGeneralsFaceEachOther(ChessBoard board) {
        // 找到兩個將軍的位置
        int redGeneralRow = -1, redGeneralCol = -1;
        int blackGeneralRow = -1, blackGeneralCol = -1;
        
        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= 9; col++) {
                ChessPiece piece = board.getPiece(row, col);
                if (piece != null && "General".equals(piece.getPieceType())) {
                    if ("Red".equals(piece.getColor())) {
                        redGeneralRow = row;
                        redGeneralCol = col;
                    } else {
                        blackGeneralRow = row;
                        blackGeneralCol = col;
                    }
                }
            }
        }
        
        // 檢查是否在同一縱行
        if (redGeneralCol == blackGeneralCol) {
            // 檢查中間是否有其他棋子
            int minRow = Math.min(redGeneralRow, blackGeneralRow);
            int maxRow = Math.max(redGeneralRow, blackGeneralRow);
            
            for (int row = minRow + 1; row < maxRow; row++) {
                if (board.getPiece(row, redGeneralCol) != null) {
                    return false; // 中間有棋子，不會面對面
                }
            }
            return true; // 同一縱行且中間無棋子
        }
        
        return false;
    }
} 
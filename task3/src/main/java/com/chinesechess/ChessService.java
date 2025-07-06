package com.chinesechess;

import java.util.Map;
import java.util.HashMap;

public class ChessService {
    private ChessBoard board;
    private String currentPlayer;
    private boolean gameEnded;
    private String winner;
    
    public ChessService() {
        this.board = new ChessBoard();
        this.currentPlayer = "Red";
        this.gameEnded = false;
        this.winner = null;
    }
    
    public void initializeEmptyBoard() {
        board = new ChessBoard();
    }
    
    public void placePiece(String pieceType, String color, int row, int col) {
        ChessPiece piece = new ChessPiece(pieceType, color);
        board.placePiece(piece, row, col);
    }
    
    public boolean isMoveLegal(int fromRow, int fromCol, int toRow, int toCol) {
        // 檢查棋盤邊界
        if (!board.isValidPosition(fromRow, fromCol) || !board.isValidPosition(toRow, toCol)) {
            return false;
        }
        
        // 檢查起始位置是否有棋子
        ChessPiece piece = board.getPiece(fromRow, fromCol);
        if (piece == null) {
            return false;
        }
        
        // 檢查目標位置是否有同色棋子
        ChessPiece targetPiece = board.getPiece(toRow, toCol);
        if (targetPiece != null && targetPiece.getColor().equals(piece.getColor())) {
            return false;
        }
        
        // 使用策略模式檢查移動規則
        MoveStrategy strategy = MoveStrategyFactory.getStrategy(piece.getPieceType());
        if (strategy == null) {
            return false;
        }
        
        return strategy.isMoveLegal(fromRow, fromCol, toRow, toCol, piece, board);
    }
    
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        ChessPiece piece = board.getPiece(fromRow, fromCol);
        ChessPiece targetPiece = board.getPiece(toRow, toCol);
        
        // 移動棋子
        board.removePiece(fromRow, fromCol);
        board.placePiece(piece, toRow, toCol);
        
        // 檢查是否捕獲了對方將軍
        if (targetPiece != null && "General".equals(targetPiece.getPieceType())) {
            gameEnded = true;
            winner = currentPlayer;
        }
    }
    
    public boolean isGameEnded() {
        return gameEnded;
    }
    
    public String getWinner() {
        return winner;
    }
    
    public void setCurrentPlayer(String player) {
        this.currentPlayer = player;
    }
    
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    
    public ChessBoard getBoard() {
        return board;
    }
} 
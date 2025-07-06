package com.chinesechess;

public class ChessPiece {
    private String pieceType;
    private String color;
    
    public ChessPiece(String pieceType, String color) {
        this.pieceType = pieceType;
        this.color = color;
    }
    
    public String getPieceType() {
        return pieceType;
    }
    
    public String getColor() {
        return color;
    }
    
    public boolean isRed() {
        return "Red".equals(color);
    }
    
    public boolean isBlack() {
        return "Black".equals(color);
    }
    
    @Override
    public String toString() {
        return color + " " + pieceType;
    }
} 
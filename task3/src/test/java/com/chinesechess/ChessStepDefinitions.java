package com.chinesechess;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class ChessStepDefinitions {
    private ChessService chessService;
    private boolean moveResult;
    private String lastMoveStatus;
    
    public ChessStepDefinitions() {
        this.chessService = new ChessService();
    }
    
    @Given("the board is empty except for a {word} {word} at \\({int}, {int}\\)")
    public void theBoardIsEmptyExceptForAPieceAt(String color, String pieceType, int row, int col) {
        chessService.initializeEmptyBoard();
        chessService.placePiece(pieceType, color, row, col);
    }
    
    @Given("the board has:")
    public void theBoardHas(DataTable dataTable) {
        chessService.initializeEmptyBoard();
        List<Map<String, String>> pieces = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> piece : pieces) {
            String pieceStr = piece.get("Piece");
            String positionStr = piece.get("Position");
            
            // 解析 "Red General" 格式
            String[] parts = pieceStr.split(" ");
            String color = parts[0];
            String pieceType = parts[1];
            
            // 解析 "(2, 4)" 格式
            String pos = positionStr.replaceAll("[()]", "");
            String[] coords = pos.split(", ");
            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);
            
            chessService.placePiece(pieceType, color, row, col);
        }
    }
    
    @When("{word} moves the {word} from \\({int}, {int}\\) to \\({int}, {int}\\)")
    public void playerMovesThePieceFromTo(String player, String pieceType, int fromRow, int fromCol, int toRow, int toCol) {
        chessService.setCurrentPlayer(player);
        moveResult = chessService.isMoveLegal(fromRow, fromCol, toRow, toCol);
        
        if (moveResult) {
            chessService.movePiece(fromRow, fromCol, toRow, toCol);
            lastMoveStatus = "legal";
        } else {
            lastMoveStatus = "illegal";
        }
    }
    
    @Then("the move is legal")
    public void theMoveIsLegal() {
        Assertions.assertTrue(moveResult, "Expected move to be legal");
    }
    
    @Then("the move is illegal")
    public void theMoveIsIllegal() {
        Assertions.assertFalse(moveResult, "Expected move to be illegal");
    }
    
    @Then("{word} wins immediately")
    public void playerWinsImmediately(String player) {
        Assertions.assertTrue(chessService.isGameEnded(), "Game should be ended");
        Assertions.assertEquals(player, chessService.getWinner(), "Winner should be " + player);
    }
    
    @Then("the game is not over just from that capture")
    public void theGameIsNotOverJustFromThatCapture() {
        Assertions.assertFalse(chessService.isGameEnded(), "Game should not be ended");
    }
} 
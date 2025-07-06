package com.chinesechess;

import java.util.HashMap;
import java.util.Map;

public class MoveStrategyFactory {
    private static final Map<String, MoveStrategy> strategies = new HashMap<>();
    
    static {
        strategies.put("General", new GeneralMoveStrategy());
        strategies.put("Guard", new GuardMoveStrategy());
        strategies.put("Rook", new RookMoveStrategy());
        strategies.put("Horse", new HorseMoveStrategy());
        strategies.put("Cannon", new CannonMoveStrategy());
        strategies.put("Elephant", new ElephantMoveStrategy());
        strategies.put("Soldier", new SoldierMoveStrategy());
    }
    
    public static MoveStrategy getStrategy(String pieceType) {
        return strategies.get(pieceType);
    }
} 
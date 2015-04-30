/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.BoardState;
import java.util.List;

/**
 *
 * @author Isti
 */
public class CheckersRules implements IGameRules {
    
    private IStepGenerator stepGenerator = null;
    private IBoardInitializer boardInitializer = null;
    private IBoardGenerator boardGenerator = null;
    private int blackPlayerId = 1;
    private int whitePlayerId = 2;
    
    public CheckersRules()
    {
        stepGenerator = new StepGenerator();
        boardInitializer = new CheckersBoardInitializer();
        boardGenerator = new ChessBoardGenerator();
    }

    @Override
    public BoardState getInitialState() {
        BoardState emptyBoard = boardGenerator.getInitialBoard();
        BoardState initialState = boardInitializer.initializeBoardStateElements(emptyBoard, blackPlayerId, whitePlayerId);
        
        return initialState;
    }

    @Override
    public boolean isValidMove(BoardState oldState, BoardState newState, int player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BoardState> getPossibleMoves(BoardState state, int player) {
        return stepGenerator.getSteps(state, player);
    }

    @Override
    public boolean isGameOver(BoardState state, int player) {
        if (getPossibleMoves(state, player).size() > 0)
        {
            return false;
        }
        return true;
    }

    @Override
    public int getFinalState(BoardState state, int player) {
        int winnerPlayerId = 0;
        boolean isGameOverBlackPlayer = false;
        boolean isGameOverWhitePlayer = false;
        if (this.isGameOver(state, blackPlayerId))
        {
            isGameOverBlackPlayer = true;
        }
        if (this.isGameOver(state, whitePlayerId))
        {
            isGameOverWhitePlayer = true;
        }
        
        if (isGameOverBlackPlayer && isGameOverWhitePlayer)
        {
            winnerPlayerId = 0; // egal
        }
        
        if (isGameOverBlackPlayer && !isGameOverWhitePlayer)
        {
           winnerPlayerId = whitePlayerId;
        }
        
        if (!isGameOverBlackPlayer && isGameOverWhitePlayer)
        {
           winnerPlayerId = blackPlayerId;
        }
        
        return winnerPlayerId;
    }
    
}

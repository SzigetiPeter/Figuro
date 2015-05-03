/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.BoardState;
import com.figuro.common.ICell;
import com.figuro.common.IUnit;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Isti
 */
public class StepGenerator implements IStepGenerator {
    IGameRules gameRules = null;
    
    public StepGenerator()
    {
        gameRules = new CheckersRules();
    }
    
    @Override
    public List<BoardState> getSteps(BoardState state, int player) {
        List<BoardState> steps = new ArrayList<BoardState>();
        ICell[][] cells = state.getBoard();
        
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[0].length; ++j) {
                ICell cell = cells[i][j];
                if (cell.hasUnit())
                {
                    IUnit unit = cell.getUnit();
                    if(unit.getOwnerId() == player)
                    {
                        Point point = new Point(i, j);
                        List<BoardState> partialStates = getStepInAllDirection(state, point);
                        steps.addAll(partialStates);
                    }
                }
            }
        }
        
        //validate all steps
        for(int i =0; i< steps.size(); ++i)
        {
            BoardState currentState = steps.get(i);
            
            if (!gameRules.isValidMove(state, currentState, player))
            {
                steps.remove(i);
            }
        }
        
        return steps;
    }
    
    //each position has 8 neighbours
    // returns all state when move to a neighbour
    private List<BoardState> getStepInAllDirection(BoardState state, Point actualPosition)
    {
        ICell actualCell = state.get(actualPosition);
        if (!actualCell.hasUnit())
        {
            return null;
        }
        
        List<BoardState> steps = new ArrayList<BoardState>();
        ICell emptyCell = new Cell();
        ICell neighbourCell = null;
        ICell temp = null;
        
        Point leftUp = new Point(actualPosition.x - 1, actualPosition.y - 1);
        neighbourCell = state.get(leftUp);
        if (neighbourCell != null && !neighbourCell.hasUnit())
        {
            BoardState newState = new BoardState(state);
            newState.set(actualPosition, emptyCell);
            newState.set(leftUp, actualCell);
            steps.add(newState);
        }
        
        Point centerUp = new Point(actualPosition.x, actualPosition.y - 1);
        neighbourCell = state.get(centerUp);
        if (neighbourCell != null && !neighbourCell.hasUnit())
        {
            BoardState newState = new BoardState(state);
            newState.set(actualPosition, emptyCell);
            newState.set(centerUp, actualCell);
            steps.add(newState);
        }
        
        Point rightUp = new Point(actualPosition.x + 1, actualPosition.y - 1);
        neighbourCell = state.get(rightUp);
        if (neighbourCell != null && !neighbourCell.hasUnit())
        {
            BoardState newState = new BoardState(state);
            newState.set(actualPosition, emptyCell);
            newState.set(rightUp, actualCell);
            steps.add(newState);
        }
        
        Point leftMiddle = new Point(actualPosition.x - 1, actualPosition.y);
        neighbourCell = state.get(leftMiddle);
        if (neighbourCell != null && !neighbourCell.hasUnit())
        {
            BoardState newState = new BoardState(state);
            newState.set(actualPosition, emptyCell);
            newState.set(leftMiddle, actualCell);
            steps.add(newState);
        }
        
        Point rightMiddle = new Point(actualPosition.x + 1, actualPosition.y );
        neighbourCell = state.get(rightMiddle);
        if (neighbourCell != null && !neighbourCell.hasUnit())
        {
            BoardState newState = new BoardState(state);
            newState.set(actualPosition, emptyCell);
            newState.set(rightMiddle, actualCell);
            steps.add(newState);
        }
        
        Point leftBottom = new Point(actualPosition.x - 1, actualPosition.y + 1);
        neighbourCell = state.get(leftBottom);
        if (neighbourCell != null && !neighbourCell.hasUnit())
        {
            BoardState newState = new BoardState(state);
            newState.set(actualPosition, emptyCell);
            newState.set(leftBottom, actualCell);
            steps.add(newState);
        }
        
        Point centerBottom = new Point(actualPosition.x, actualPosition.y + 1);
        neighbourCell = state.get(centerBottom);
        if (neighbourCell != null && !neighbourCell.hasUnit())
        {
            BoardState newState = new BoardState(state);
            newState.set(actualPosition, emptyCell);
            newState.set(centerBottom, actualCell);
            steps.add(newState);
        }
        
        Point rightBottom = new Point(actualPosition.x + 1, actualPosition.y + 1);
        neighbourCell = state.get(rightBottom);
        if (neighbourCell != null && !neighbourCell.hasUnit())
        {
            BoardState newState = new BoardState(state);
            newState.set(actualPosition, emptyCell);
            newState.set(rightBottom, actualCell);
            steps.add(newState);
        }        
        
        return steps;
    }
}

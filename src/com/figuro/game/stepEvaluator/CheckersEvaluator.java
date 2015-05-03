/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.stepEvaluator;

import com.figuro.common.BoardState;
import com.figuro.common.Evaluation;
import com.figuro.common.ICell;
import com.figuro.common.IUnit;
import com.figuro.game.rules.UnitEnum;

/**
 *
 * @author Isti
 */
public class CheckersEvaluator implements IStepEvaluator {

    private IMinimumCalculator minimumCalculator;
    
    public CheckersEvaluator()
    {
        minimumCalculator = new MinimumCalculator();
    }
    
    @Override
    public Evaluation evaluate(BoardState state, int player) {
        if (state == null)
            return null;
        
        ICell[][] cells = state.getBoard();
        
        if (cells == null)
            return null;
        
        Evaluation evaluation = new Evaluation();
        int score = 0;
        
        
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[0].length; ++j) {
                ICell currentCell = cells[i][j];
                if (currentCell != null && currentCell.hasUnit())
                {
                    IUnit unit = currentCell.getUnit();
                    
                    if (unit.getOwnerId() == player)
                    {
                        if (unit.getType() == UnitEnum.PEASANT)
                        {
                            score += 1;
                        }
                        else if (unit.getType() == UnitEnum.KING)
                        {
                            score += 3;
                        }
                    }
                }
            }
        }
            
        evaluation.setPlayer(player);
        evaluation.setScore(score);
        
        return evaluation;
    }

    @Override
    public Evaluation min(Evaluation a, Evaluation b, int player) {
        return minimumCalculator.min(a, b, player);
    }
    
}

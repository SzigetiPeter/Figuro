/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.stepEvaluator;

import com.figuro.common.BoardState;
import com.figuro.common.Evaluation;

/**
 * @author Aszalos Gyorgy
 */
public interface IStepEvaluator {
    public Evaluation evaluate(BoardState state, int player);
    public Evaluation min(Evaluation a, Evaluation b, int player);
}

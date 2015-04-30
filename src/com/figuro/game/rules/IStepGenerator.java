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
public interface IStepGenerator {
    public List<BoardState> getSteps(BoardState state, int player);
}

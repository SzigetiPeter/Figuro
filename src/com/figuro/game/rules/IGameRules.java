/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import java.util.List;

import com.figuro.common.BoardState;

/**
 * @author Aszalos Gyorgy
 */
public interface IGameRules {
    public BoardState getInitialState();
    public boolean isValidMove(BoardState oldState, BoardState newState, int player);
    public List<BoardState> getPossibleMoves(BoardState state, int player);
    public boolean isGameOver(BoardState state, int player);
    public int getFinalState(BoardState state, int player);
}

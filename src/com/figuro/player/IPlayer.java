/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.player;

import com.figuro.common.BoardState;
import com.figuro.engine.IMoveComplete;

/**
 * @author Aszalos Gyorgy
 */
public interface IPlayer {
    public int getId();
    public void setId(int id);
    public void setInitialState(BoardState board);
    public void move(IMoveComplete callback);
    public void wrongMoveResetTo(BoardState board);
    public void notify(BoardState counterMove);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.BoardState;
import com.figuro.common.ICell;

/**
 *
 * @author Isti
 */
public class ChessBoardGenerator implements IBoardGenerator {

    @Override
    public BoardState getInitialBoard() {
        ICell[][] boardCells = new Cell[8][8];
        BoardState state = new BoardState(boardCells);
        
        return state;
    }
    
}

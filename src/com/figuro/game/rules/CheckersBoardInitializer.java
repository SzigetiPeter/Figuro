/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.BoardState;
import com.figuro.common.Cell;
import com.figuro.common.ICell;
import com.figuro.common.IUnit;
import com.figuro.common.Unit;
import java.awt.Point;

/**
 *
 * @author Isti
 */
public class CheckersBoardInitializer implements IBoardInitializer {

    @Override
    public BoardState initializeBoardStateElements(BoardState state, int blackPlayerId, int whitePlayerId) {
        ICell cell = null;
        Point point = null;
        IUnit unit = null;
        
    //initialize black
        
        //--1. row
        cell = new Cell();
        point = new Point();
        point.x = 1;
        point.y = 0;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 3;
        point.y = 0;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 5;
        point.y = 0;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 7;
        point.y = 0;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        //--2. row
        cell = new Cell();
        point = new Point();
        point.x = 0;
        point.y = 1;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 2;
        point.y = 1;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 4;
        point.y = 1;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 6;
        point.y = 1;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        //--3. row
        cell = new Cell();
        point = new Point();
        point.x = 1;
        point.y = 2;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 3;
        point.y = 2;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 5;
        point.y = 2;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 7;
        point.y = 2;
        unit = new CheckersUnit(Unit.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
    //initialize while
        
        //--1. row
        cell = new Cell();
        point = new Point();
        point.x = 0;
        point.y = 0;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 2;
        point.y = 7;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 4;
        point.y = 7;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 6;
        point.y = 7;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        //--2. row
        cell = new Cell();
        point = new Point();
        point.x = 1;
        point.y = 6;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 3;
        point.y = 6;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 5;
        point.y = 6;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 7;
        point.y = 6;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        //--3. row
        cell = new Cell();
        point = new Point();
        point.x = 0;
        point.y = 5;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 2;
        point.y = 5;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 4;
        point.y = 5;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 6;
        point.y = 5;
        unit = new CheckersUnit(Unit.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        state.set(point, cell);
        
        return state;
    }
    
}
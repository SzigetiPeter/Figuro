/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import com.figuro.common.BoardState;
import com.figuro.common.ICell;
import com.figuro.common.IUnit;

/**
 *
 * @author Isti
 */
public class CheckersBoardInitializer implements IBoardInitializer {

	@Override
	public BoardState initializeBoardStateElements(BoardState state,
			int blackPlayerId, int whitePlayerId) {
		ICell cell = null;
		IUnit unit = null;

		// initialize black
		ArrayList<Point> blackPuppetPositions = new ArrayList<Point>();

		// --1. row
		blackPuppetPositions.add(new Point(1, 7));
		blackPuppetPositions.add(new Point(3, 7));
		blackPuppetPositions.add(new Point(5, 7));
		blackPuppetPositions.add(new Point(7, 7));
		// --2. row
		blackPuppetPositions.add(new Point(0, 6));
		blackPuppetPositions.add(new Point(2, 6));
		blackPuppetPositions.add(new Point(4, 6));
		blackPuppetPositions.add(new Point(6, 6));
		// --3. row
		blackPuppetPositions.add(new Point(1, 5));
		blackPuppetPositions.add(new Point(3, 5));
		blackPuppetPositions.add(new Point(5, 5));
		blackPuppetPositions.add(new Point(7, 5));

		for (Iterator<Point> it = blackPuppetPositions.iterator(); it.hasNext();) {
			Point point = it.next();

			cell = new Cell();
			unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
			cell.setUnit(unit);
			state.set(point, cell);
		}

		// initialize while
		ArrayList<Point> whitePuppetPositions = new ArrayList<Point>();

		// --1. row
		whitePuppetPositions.add(new Point(0, 0));
		whitePuppetPositions.add(new Point(2, 0));
		whitePuppetPositions.add(new Point(4, 0));
		whitePuppetPositions.add(new Point(6, 0));
		// --2. row
		whitePuppetPositions.add(new Point(1, 1));
		whitePuppetPositions.add(new Point(3, 1));
		whitePuppetPositions.add(new Point(5, 1));
		whitePuppetPositions.add(new Point(7, 1));
		// --3. row
		whitePuppetPositions.add(new Point(0, 2));
		whitePuppetPositions.add(new Point(2, 2));
		whitePuppetPositions.add(new Point(4, 2));
		whitePuppetPositions.add(new Point(6, 2));

		for (Iterator<Point> it = whitePuppetPositions.iterator(); it.hasNext();) {
			Point point = it.next();

			cell = new Cell();
			unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
			cell.setUnit(unit);
			state.set(point, cell);
		}

		return state;
	}

}

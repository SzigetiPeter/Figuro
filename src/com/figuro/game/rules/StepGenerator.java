/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.common.ICell;
import com.figuro.common.IUnit;

/**
 *
 * @author Isti
 */
public class StepGenerator implements IStepGenerator {

	@Override
	public List<BoardState> getSteps(BoardState state, int player) {

		if (state == null)
			return null;
		if (player == 0)
			return null;

		ICell[][] cells = state.getBoard();
		if (cells == null)
			return null;

		List<BoardState> steps = new ArrayList<BoardState>();

		for (int i = 0; i < cells.length; ++i) {
			for (int j = 0; j < cells[0].length; ++j) {
				ICell cell = cells[i][j];
				if (cell != null && cell.hasUnit()) {
					IUnit unit = cell.getUnit();
					if (unit.getOwnerId() == player) {
						Point point = new Point(i, j);
						List<BoardState> partialStates = getStepInAllDirection(
								state, point);

						if (partialStates != null) {
							steps.addAll(partialStates);
						}
					}
				}
			}
		}

		return steps;
	}

	// each position has 8 neighbours
	// returns all state when move to a neighbour
	private List<BoardState> getStepInAllDirection(BoardState state,
			Point actualPosition) {
		ICell actualCell = state.get(actualPosition);
		if (actualCell == null || !actualCell.hasUnit()) {
			return null;
		}

		List<BoardState> steps = new ArrayList<BoardState>();
		ICell emptyCell = new Cell();
		ICell neighbourCell = null;

		// 1 step distance
		Point leftUp = new Point(actualPosition.x - 1, actualPosition.y + 1);
		neighbourCell = state.get(leftUp);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(leftUp, actualCell);
			steps.add(newState);
		}

		Point centerUp = new Point(actualPosition.x, actualPosition.y + 1);
		neighbourCell = state.get(centerUp);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(centerUp, actualCell);
			steps.add(newState);
		}

		Point rightUp = new Point(actualPosition.x + 1, actualPosition.y + 1);
		neighbourCell = state.get(rightUp);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(rightUp, actualCell);
			steps.add(newState);
		}

		Point leftMiddle = new Point(actualPosition.x - 1, actualPosition.y);
		neighbourCell = state.get(leftMiddle);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(leftMiddle, actualCell);
			steps.add(newState);
		}

		Point rightMiddle = new Point(actualPosition.x + 1, actualPosition.y);
		neighbourCell = state.get(rightMiddle);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(rightMiddle, actualCell);
			steps.add(newState);
		}

		Point leftBottom = new Point(actualPosition.x - 1, actualPosition.y - 1);
		neighbourCell = state.get(leftBottom);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(leftBottom, actualCell);
			steps.add(newState);
		}

		Point centerBottom = new Point(actualPosition.x, actualPosition.y - 1);
		neighbourCell = state.get(centerBottom);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(centerBottom, actualCell);
			steps.add(newState);
		}

		Point rightBottom = new Point(actualPosition.x + 1,
				actualPosition.y - 1);
		neighbourCell = state.get(rightBottom);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(rightBottom, actualCell);
			steps.add(newState);
		}

		// 2 steps distance
		Point point = null;

		// Top

		point = new Point(actualPosition.x - 2, actualPosition.y + 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x - 1, actualPosition.y + 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x, actualPosition.y + 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x + 1, actualPosition.y + 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x + 2, actualPosition.y + 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		//

		point = new Point(actualPosition.x - 2, actualPosition.y + 1);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x + 2, actualPosition.y + 1);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		// Middle

		point = new Point(actualPosition.x - 2, actualPosition.y);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x - 1, actualPosition.y);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		//

		point = new Point(actualPosition.x - 2, actualPosition.y - 1);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x + 2, actualPosition.y - 1);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		// Bottom

		point = new Point(actualPosition.x - 2, actualPosition.y - 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x - 1, actualPosition.y - 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x, actualPosition.y - 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x + 1, actualPosition.y - 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		point = new Point(actualPosition.x + 2, actualPosition.y - 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}

		return steps;
	}

	@Override
	public List<BoardState> getDiagonalSteps(BoardState state, Point actualPosition, int player) {
		
		if (state == null)
			return null;
		if (actualPosition == null)
			return null;
		if (player == 0)
			return null;

		ICell[][] cells = state.getBoard();
		if (cells == null)
			return null;
		
		ICell actualCell = state.get(actualPosition);
		if (actualCell == null || !actualCell.hasUnit()) {
			return null;
		}

		List<BoardState> steps = new ArrayList<BoardState>();
		ICell emptyCell = new Cell();
		ICell neighbourCell = null;
		
		Point point = null;
		
		point = new Point(actualPosition.x - 2, actualPosition.y + 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}
		
		point = new Point(actualPosition.x + 2, actualPosition.y + 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}
		
		point = new Point(actualPosition.x - 2, actualPosition.y - 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}
		
		point = new Point(actualPosition.x + 2, actualPosition.y + 2);
		neighbourCell = state.get(point);
		if (neighbourCell != null && !neighbourCell.hasUnit()) {
			BoardState newState = new BoardState(state);
			newState.set(actualPosition, emptyCell);
			newState.set(point, actualCell);
			steps.add(newState);
		}
		
		
		return steps;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.awt.Point;
import java.util.List;

import com.figuro.common.BoardState;
import com.figuro.common.ICell;
import com.figuro.common.IUnit;
import com.figuro.common.PlayerConverter;
import com.figuro.common.PlayerEnum;

/**
 *
 * @author Isti
 */
public class CheckersRules implements IGameRules {

	private IStepGenerator stepGenerator = null;
	private IBoardInitializer boardInitializer = null;
	private IBoardGenerator boardGenerator = null;
	private int blackPlayerId = 1;
	private int whitePlayerId = 2;

	public CheckersRules() {
		stepGenerator = new StepGenerator();
		boardInitializer = new CheckersBoardInitializer();
		boardGenerator = new ChessBoardGenerator();
	}

	@Override
	public BoardState getInitialState() {
		BoardState emptyBoard = boardGenerator.getInitialBoard();
		BoardState initialState = boardInitializer
				.initializeBoardStateElements(emptyBoard, blackPlayerId,
						whitePlayerId);

		return initialState;
	}

	@Override
	public boolean isValidMove(BoardState oldState, BoardState newState,
			int player) {

		if (oldState == null)
			return false;
		if (newState == null)
			return false;
		if (player == 0) // 0 means egal
			return false;

		ICell[][] oldCells = oldState.getBoard();
		ICell[][] newCells = newState.getBoard();

		if (oldCells == null || newCells == null)
			return false;

		boolean isValidMove = false;

		if (oldState.equals(newState) == false) {
			Point beginPoint = new Point();
			Point endPoint = new Point();
			getMoveEndPoints(oldCells, newCells, player, beginPoint, endPoint);
			if (isValidMove(oldCells, beginPoint, endPoint, player)) {
				isValidMove = true;
			}
		}

		return isValidMove;
	}
	
	public BoardState applyMoveEffect(BoardState oldState, BoardState newState,
			int player) {
		
		//TODO: implement
		return newState;
	}

	private void getMoveEndPoints(ICell[][] oldCells, ICell[][] newCells,
			int player, Point beginPoint, Point endPoint) {
		Point temp = getChange(oldCells, newCells, player);
		beginPoint.x = temp.x;
		beginPoint.y = temp.y;

		temp = getChange(newCells, oldCells, player);
		endPoint.x = temp.x;
		endPoint.y = temp.y;
	}

	private Point getChange(ICell[][] cells1, ICell[][] cells2, int player) {
		Point difference = null;
		for (int i = 0; i < cells1.length; ++i) {
			for (int j = 0; j < cells1[0].length; ++j) {
				ICell cell1 = cells1[i][j];
				ICell cell2 = cells2[i][j];

				if (isCellChanged(cell1, cell2) && cell1.hasUnit()) {
					IUnit unit1 = cell1.getUnit();
					if (unit1.getOwnerId() == player) {
						difference = new Point(i, j);
					}
					// else something went wrong, the unit which has been moved
					// is not belong to the player
				}
			}
		}

		return difference;
	}

	private boolean isValidMove(ICell[][] cells, Point beginPoint,
			Point endPoint, int player) {
		boolean isValidMove = false;
		ICell beginCell = cells[beginPoint.x][beginPoint.y];
		ICell endCell = cells[endPoint.x][endPoint.y];

		if (beginCell.hasUnit() && !endCell.hasUnit()) {
			IUnit unit = beginCell.getUnit();
			if (unit.getType() == UnitEnum.PEASANT) {
				isValidMove = isValidPeasantMove(cells, beginPoint, endPoint,
						player);
			} else if (unit.getType() == UnitEnum.KING) {
				isValidMove = isValidKingMove(cells, beginPoint, endPoint,
						player);
			}
		}

		return isValidMove;
	}

	private boolean isValidPeasantMove(ICell[][] cells, Point beginPoint,
			Point endPoint, int player) {
		boolean isValidPeasantMove = false;

		int distance = getDistance(beginPoint, endPoint);
		if (distance == 1 && isDiagonal(beginPoint, endPoint)
				&& isMovingAhead(player, beginPoint, endPoint)) {
			isValidPeasantMove = true;
		} else if (distance == 2 && isDiagonal(beginPoint, endPoint)
				&& isMovingAhead(player, beginPoint, endPoint)
				&& isEnemyInBetween(cells, player, beginPoint, endPoint)) {
			isValidPeasantMove = true;
		}

		return isValidPeasantMove;
	}

	private boolean isValidKingMove(ICell[][] cells, Point beginPoint,
			Point endPoint, int player) {
		boolean isValidKingMove = false;

		int distance = getDistance(beginPoint, endPoint);
		if (distance == 1 && isDiagonal(beginPoint, endPoint)) {
			isValidKingMove = true;
		} else if (distance == 2 && isDiagonal(beginPoint, endPoint)
				&& isEnemyInBetween(cells, player, beginPoint, endPoint)) {
			isValidKingMove = true;
		}

		return isValidKingMove;
	}

	private int getDistance(Point beginPoint, Point endPoint) {
		int distance = 0;

		int distanceX = abs(beginPoint.x - endPoint.x);
		int distanceY = abs(beginPoint.y - endPoint.y);

		distance = max(distanceX, distanceY);

		return distance;
	}

	// true if both x and y has the same distance from the beginPoint
	private boolean isDiagonal(Point beginPoint, Point endPoint) {
		boolean isDiagonal = false;

		int diagonalDistanceX = abs(beginPoint.x - endPoint.x);
		int diagonalDistanceY = abs(beginPoint.y - endPoint.y);

		if (diagonalDistanceX == diagonalDistanceY) {
			isDiagonal = true;
		}

		return isDiagonal;
	}

	private boolean isMovingAhead(int player, Point beginPoint, Point endPoint) {
		boolean isMovingAhead = false;

		Point rightClosestCellPoint = getPlayerRightClosestPoint(player);

		if (abs(beginPoint.y - rightClosestCellPoint.y) < abs(endPoint.y
				- rightClosestCellPoint.y)) {
			isMovingAhead = true;
		}

		return isMovingAhead;
	}

	private Point getPlayerRightClosestPoint(int playerId) {
		Point rightClosestCellPoint = new Point();

		PlayerEnum player = PlayerConverter.PlayerIdToPlayerEnum(playerId);

		if (player == PlayerEnum.BLACK) {
			rightClosestCellPoint = BoardState.getBlackPlayerRightClosestCell();
		} else if (player == PlayerEnum.WHITE) {
			rightClosestCellPoint = BoardState.getWhitePlayerRightClosestCell();
		}

		return rightClosestCellPoint;
	}

	private boolean isEnemyInBetween(ICell[][] cells, int player,
			Point beginPoint, Point endPoint) {
		boolean isEnemyInBetween = false;

		Point pointInTheMiddle = getThePointInBetween(beginPoint, endPoint);

		if (pointInTheMiddle != null) {
			ICell cell = cells[pointInTheMiddle.x][pointInTheMiddle.y];

			if (cell.hasUnit()) {
				IUnit unit = cell.getUnit();

				if (unit.getOwnerId() != player) {
					isEnemyInBetween = true;
				}
			}
		}

		return isEnemyInBetween;
	}

	private Point getThePointInBetween(Point beginPoint, Point endPoint) {
		Point pointInBetween = null;

		int x = 0;
		int y = 0;
		// negativ -- move down
		if (beginPoint.x - endPoint.x < 0) {
			x = beginPoint.x + 1;
		}
		// positive -- move up
		else if (beginPoint.x - endPoint.x > 0) {
			x = beginPoint.x - 1;
		}

		// negativ -- move right
		if (beginPoint.y - endPoint.y < 0) {
			y = beginPoint.y + 1;
		}
		// positive -- move left
		else if (beginPoint.y - endPoint.y > 0) {
			y = beginPoint.y - 1;
		}

		pointInBetween = new Point(x, y);

		return pointInBetween;
	}

	private boolean isCellChanged(ICell oldCell, ICell newCell) {
		boolean isChanged = true;

		if (newCell.hasUnit() && oldCell.hasUnit()) {
			IUnit oldUnit = oldCell.getUnit();
			IUnit newUnit = newCell.getUnit();
			if (oldUnit.getOwnerId() == newUnit.getOwnerId()) {
				if (oldUnit.getType() == newUnit.getType()) {
					isChanged = false;
				}
			}
		}

		return isChanged;
	}

	@Override
	public List<BoardState> getPossibleMoves(BoardState state, int player) {
		if (state == null)
			return null;
		if (player == 0)
			return null;

		List<BoardState> boardStateCandidates = stepGenerator.getSteps(state,
				player);

		if (boardStateCandidates == null)
			return null;

		// validate all steps
		for (int i = 0; i < boardStateCandidates.size(); ++i) {
			BoardState currentState = boardStateCandidates.get(i);

			if (!this.isValidMove(state, currentState, player)) {
				boardStateCandidates.remove(i);
			}
		}

		return boardStateCandidates;
	}

	@Override
	public boolean isGameOver(BoardState state, int player) {
		if (state == null)
			return true;
		if (player == 0)
			return true;

		List<BoardState> possibleMoves = getPossibleMoves(state, player);

		if (possibleMoves != null && possibleMoves.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public int getFinalState(BoardState state, int player) {
		int winnerPlayerId = 0;
		boolean isGameOverBlackPlayer = false;
		boolean isGameOverWhitePlayer = false;
		if (this.isGameOver(state, blackPlayerId)) {
			isGameOverBlackPlayer = true;
		}
		if (this.isGameOver(state, whitePlayerId)) {
			isGameOverWhitePlayer = true;
		}

		if (isGameOverBlackPlayer && isGameOverWhitePlayer) {
			winnerPlayerId = 0; // egal
		}

		if (isGameOverBlackPlayer && !isGameOverWhitePlayer) {
			winnerPlayerId = whitePlayerId;
		}

		if (!isGameOverBlackPlayer && isGameOverWhitePlayer) {
			winnerPlayerId = blackPlayerId;
		}

		return winnerPlayerId;
	}

	@Override
	public int getNextPlayer(BoardState oldState, BoardState newState,
			int player) {
		if (oldState == null)
			return PlayerConverter.PlayerEnumToPlayerId(PlayerEnum.BLACK);
		;
		if (newState == null)
			return 0;
		if (player == 0)
			return 0;

		int nextPlayerId = 0;
		Point beginPoint = new Point();
		Point endPoint = new Point();
		ICell[][] oldCells = oldState.getBoard();
		ICell[][] newCells = newState.getBoard();
		getMoveEndPoints(oldCells, newCells, player, beginPoint, endPoint);

		int distance = getDistance(beginPoint, endPoint);

		if (distance == 1) {
			PlayerEnum playerEnum = PlayerConverter
					.PlayerIdToPlayerEnum(player);

			if (playerEnum == PlayerEnum.BLACK) {
				nextPlayerId = PlayerConverter
						.PlayerEnumToPlayerId(PlayerEnum.WHITE);
			} else if (playerEnum == PlayerEnum.WHITE) {
				nextPlayerId = PlayerConverter
						.PlayerEnumToPlayerId(PlayerEnum.BLACK);
			}
		} else {
			nextPlayerId = player;
		}

		return nextPlayerId;
	}

}

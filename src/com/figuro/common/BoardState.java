package com.figuro.common;

import java.awt.Point;

/**
 * @author Mathe E. Botond
 * 
 * Represents a state in the game.
 */
public class BoardState {
	private int[][] board;
	
	private Point lastMove = null;
	private Point lastMoveFrom = null;
	
	public BoardState(int height, int width) {
		board = new int[height][width];
	}
	
	public void set(Point position, int value) {
		board[position.x][position.y] = value;
	}
	
	public int get(Point position) {
		return board[position.x][position.y];
	}
	
	public void setBoard(int[][] board) {
		this.board = board;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	/**
	 * Specify where the latest moved piece ended up
	 * @param poistion
	 */
	public void setLatestMoved(Point posistion) {
		lastMove = posistion;
	}
	
	/**
	 * Specify where the latest moved piece came from
	 * (leave blank if a newly placed piece was put on the board)
	 * @param poistion
	 */
	public void setLatestMovedFrom(Point position) {
		lastMoveFrom = position;
	}
	
	/**
	 * Gets where the latest moved piece ended up
	 * @param poistion
	 */
	public Point getLastMove() {
		return lastMove;
	}
	
	/**
	 * Gets where the latest moved piece came from
	 * (value is null if a newly placed piece was put on the board)
	 * @param poistion
	 */
	public Point getLastMoveFrom() {
		return lastMoveFrom;
	}
}

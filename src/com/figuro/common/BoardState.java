package com.figuro.common;

import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.io.*;

import com.figuro.game.rules.Cell;

/**
 * @author Mathe E. Botond
 *
 *  Represents a state in the game.
 */
public class BoardState implements Serializable {

	private static final long serialVersionUID = 2915303719032409877L;

	private ICell[][] board;

	private static Point whitePlayerRightClosestCell = new Point(0, 7);
	private static Point blackPlayerRightClosestCell = new Point(7, 0);
	
	private Point lastMove = null;
	private Point lastMoveFrom = null;

	public BoardState(ICell[][] board) {
		if (board != null) {
			this.board = board;
		}
	}

	public BoardState(int height, int width) {
		board = new Cell[height][width];
	}

	public BoardState(BoardState state) {
		ICell[][] cells = state.getBoard();
		this.board = new Cell[cells.length][cells[0].length];

		for (int i = 0; i < cells.length; ++i) {
			for (int j = 0; j < cells[0].length; ++j) {
				this.board[i][j] = cells[i][j];
			}
		}

		this.lastMove = state.lastMove;
		this.lastMoveFrom = state.lastMoveFrom;
	}

	public void set(Point position, ICell value) {
		board[position.x][position.y] = value;
	}

	public ICell get(Point position) {
		if (position.x < 0 || position.x >= board.length) {
			return null;
		}

		if (position.y < 0 || position.y >= board[0].length) {
			return null;
		}

		return board[position.x][position.y];
	}

	public void setBoard(ICell[][] board) {
		this.board = board;
	}

	public ICell[][] getBoard() {
		return board;
	}

	/**
	 * Specify where the latest moved piece ended up
	 *
	 * @param poistion
	 */
	public void setLatestMoved(Point posistion) {
		lastMove = posistion;
	}

	/**
	 * Specify where the latest moved piece came from (leave blank if a newly
	 * placed piece was put on the board)
	 *
	 * @param poistion
	 */
	public void setLatestMovedFrom(Point position) {
		lastMoveFrom = position;
	}

	/**
	 * Gets where the latest moved piece ended up
	 *
	 * @param poistion
	 */
	public Point getLastMove() {
		return lastMove;
	}

	/**
	 * Gets where the latest moved piece came from (value is null if a newly
	 * placed piece was put on the board)
	 *
	 * @param poistion
	 */
	public Point getLastMoveFrom() {
		return lastMoveFrom;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardState))
			return false;
		if (obj == this)
			return true;

		boolean isEqual = true;
		BoardState state = (BoardState) obj;
		ICell[][] cells = state.getBoard();

		if (this.board.length == cells.length) {
			for (int i = 0; i < cells.length; ++i) {
				for (int j = 0; j < cells[0].length; ++j) {
					ICell cell = cells[i][j];
					ICell cell2 = board[i][j];

					if (!cell.equals(cell2)) {
						return false;
					}
				}
			}
		}

		return isEqual;
	}

	public static Point getBlackPlayerRightClosestCell() {
		return blackPlayerRightClosestCell;
	}

	public static Point getWhitePlayerRightClosestCell() {
		return whitePlayerRightClosestCell;
	}

	public String toString() {
		String output = "";
		for (int i = 0; i < this.board.length; ++i) {
			for (int j = 0; j < this.board[0].length; ++j) {
				if (this.board[i][j].hasUnit()) {
					IUnit unit = this.board[i][j].getUnit();
					output += "[" + unit.getOwnerId() + "-" + unit.getType().toString() + "]\t";
				} else {
					output += "[\t]\t";
				}
			}
			output += "\n";
		}
		return output;
	}

	/** Read the object from Base64 string. */
	public static Object fromString( String s ) {
		byte [] data = Base64.getDecoder().decode(s);
		ObjectInputStream ois;
		Object o;
		try {
			ois = new ObjectInputStream( 
					new ByteArrayInputStream(  data ) );
			o  = ois.readObject();
			ois.close();
			
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
			return null;
		}
		
		return o;
	}

	/** Write the object to a Base64 string. */
	public static String toString( Serializable o ) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( baos );
			oos.writeObject( o );
			oos.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
		
		return new String( Base64.getEncoder().encode( baos.toByteArray() ) );
	}
}

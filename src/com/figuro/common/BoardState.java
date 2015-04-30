package com.figuro.common;

import java.awt.Point;

/**
 * @author Mathe E. Botond
 *
 * Represents a state in the game.
 */
public class BoardState {

    private ICell[][] board;

    private Point lastMove = null;
    private Point lastMoveFrom = null;

    public BoardState(ICell[][] board) {
        if (board != null) {
            this.board = board;
        }
    }

    public BoardState(int height, int width) {
        board = new ICell[height][width];
    }

    public BoardState(BoardState state) {
        ICell[][] cells = state.getBoard();
        this.board = new ICell[cells.length][cells[0].length];

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
        if (position.x < board.length || position.x > board.length) {
            return null;
        }

        if (position.y < board[0].length || position.y > board[0].length) {
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
}

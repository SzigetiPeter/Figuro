package com.figuro.common;

import java.awt.Point;

/**
 * @author Mathe E. Botond
 *
 * Represents a state in the game.
 */
public class BoardState {

    private ICell[][] board;
    
    private static Point blackPlayerRightClosestCell = new Point(7, 7);
    private static Point whitePlayerRightClosestCell = new Point(0, 0);

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
    
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof BoardState))
            return false;
        if (obj == this)
            return true;

        boolean isEqual = false;
        BoardState state = (BoardState) obj;
        ICell[][] cells = state.getBoard();
        
        if (this.board.length == cells.length)
        {
            for (int i = 0; i < cells.length; ++i) {
                for (int j = 0; j < cells[0].length; ++j) {
                    ICell cell = cells[i][j];
                    ICell cell2 = board[i][j];
                    if (cell.hasUnit() && cell2.hasUnit())
                    {
                        IUnit unit = cell.getUnit();
                        IUnit unit2 = cell2.getUnit();
                        
                        if (unit.getOwnerId() == unit2.getOwnerId())
                        {
                            if (unit.getType() == unit2.getType())
                            {
                                isEqual = true;
                            }
                        }
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
}

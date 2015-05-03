package com.figuro.game.boardGraphics;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 *
 * @author SzPeter
 */
public interface IBoard {
    /**
     * Gets the x size of the board,
     * 
     * @return boardSizeX
     * */
    public int getBoardSizeX();

    /**
     * Sets the x size of the board.
     * 
     * @param boardSizeX
     *            The x size of the board.
     * */
    public void setBoardSizeX(int boardSizeX);

    /**
     * Gets the y size of the board,
     * 
     * @return boardSizeY
     * */
    public int getBoardSizeY();

    /**
     * Sets the y size of the board.
     * 
     * @param boardSizeY
     *            The y size of the board.
     * */
    public void setBoardSizeY(int boardSizeY);

    /**
     * Gets the primary color of the game board.
     * 
     * @return mainColor The main color of the board.
     * */
    public Color getColorMain();

    /**
     * Gets the primary color of the game board.
     * 
     * @return colorMain The main color of the board.
     * */
    public void setColorMain(Color colorMain);

    /**
     * Gets the filler color of the game board.
     * 
     * @return fillerColor The filler color of the board.
     * */
    public Color getColorFiller();

    /**
     * Gets the filler color of the game board.
     * 
     * @param colorFiller
     *            The main color of the board.
     * */
    public void setColorFiller(Color colorFiller);

    /**
     * Gets the resizable gui element of the board.
     * 
     * @return boardPane The resizable gui element of the board.
     * */
    public BorderPane getBoardPane();
}

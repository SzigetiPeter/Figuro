package com.figuro.game.boardGraphics;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author SzPeter
 */
public class Board implements IBoard {

	private int boardSizeX;
	private int boardSizeY;
	private Color colorMain;
	private Color colorFiller;
	private BorderPane boarderPane;

	public Board() {
		this.boardSizeX = 8;
		this.boardSizeY = 8;
		this.colorMain = Color.RED;
		this.colorFiller = Color.BROWN;
		createBoard();
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Board(int x, int y) {
		this.boardSizeX = x;
		this.boardSizeY = y;
		this.colorMain = Color.RED;
		this.colorFiller = Color.BROWN;
		createBoard();
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param colorM
	 * @param colorF
	 */
	public Board(int x, int y, Color colorM, Color colorF) {
		this.boardSizeX = x;
		this.boardSizeY = y;
		this.colorMain = colorM;
		this.colorFiller = colorF;
		createBoard();
	}

	@Override
	public int getBoardSizeX() {
		return this.boardSizeX;
	}

	/**
	 * 
	 * @param boardSizeX
	 */
	@Override
	public void setBoardSizeX(int boardSizeX) {
		this.boardSizeX = boardSizeX;
	}

	@Override
	public int getBoardSizeY() {
		return this.boardSizeY;
	}

	/**
	 * 
	 * @param boardSizeY
	 */
	@Override
	public void setBoardSizeY(int boardSizeY) {
		this.boardSizeY = boardSizeY;
	}

	@Override
	public Color getColorMain() {
		return this.colorMain;
	}

	/**
	 * 
	 * @param colorMain
	 */
	@Override
	public void setColorMain(Color colorMain) {
		this.colorMain = colorMain;
	}

	@Override
	public Color getColorFiller() {
		return this.colorFiller;
	}

	/**
	 * 
	 * @param colorFiller
	 */
	@Override
	public void setColorFiller(Color colorFiller) {
		this.colorFiller = colorFiller;
	}

	@Override
	public BorderPane getBoardPane() {
		return boarderPane;
	}

	/**
	 * 
	 * @param boarderPane
	 */
	public void setBoarderPane(BorderPane boarderPane) {
		this.boarderPane = boarderPane;
	}

	private Group createBoard() {
		boarderPane = new BorderPane();
		Group board = new Group();
		NumberBinding rectsAreaSize = Bindings.min(
				boarderPane.heightProperty(), boarderPane.widthProperty());

		int m = 0;
		for (int i = 0; i < this.boardSizeX; i++) {
			for (int j = 0; j < this.boardSizeY; j++) {
				Rectangle rectangle = new Rectangle(
						rectsAreaSize.doubleValue() / 10,
						rectsAreaSize.doubleValue() / 10);
				rectangle.xProperty().bind(
						rectsAreaSize.multiply(i).divide(this.boardSizeX));
				rectangle.yProperty().bind(
						rectsAreaSize.multiply(j).divide(this.boardSizeY));

				rectangle.heightProperty().bind(
						rectsAreaSize.divide(this.boardSizeX));
				rectangle.widthProperty().bind(rectangle.heightProperty());

				m = i % 2 == 0 ? 1 : 0;
				if ((m + j) % 2 == 0) {
					rectangle.setFill(this.colorMain);
				} else {
					rectangle.setFill(this.colorFiller);
				}
				board.getChildren().add(rectangle);
			}
		}
		boarderPane.getChildren().add(board);
		return board;
	}

}
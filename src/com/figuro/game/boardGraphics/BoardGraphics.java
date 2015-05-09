package com.figuro.game.boardGraphics;

import java.util.ArrayList;

import com.figuro.common.IUnit;

/**
 *
 * @author SzPeter
 */
public class BoardGraphics implements IBoardGraphics {

	private IBoard board;
	private ArrayList<IPip> pips = new ArrayList<IPip>();
	private IAnimation animation;

	public BoardGraphics() {
		throw new UnsupportedOperationException();
	}

	public BoardGraphics(int x, int y) {
		this.board = new Board(x, y);
		this.animation = new Animation(board, pips);
	}

	public BoardGraphics(int x, int y, ArrayList<IUnit> units) {
		this.board = new Board(x, y);
		this.pips = new ArrayList<IPip>();
		for (int i = 0; i < units.size(); i++) {
			this.pips.add(new Pip(board, units.get(i)));
		}
		this.animation = new Animation(board, pips);
	}

	public IBoard getBoard() {
		return this.board;
	}

	/**
	 * 
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	public ArrayList<IPip> getPips() {
		return this.pips;
	}

	/**
	 * 
	 * @param pips
	 */
	public void setPips(ArrayList<IPip> pips) {
		this.pips = pips;
	}

	public IAnimation getAnimation() {
		return this.animation;
	}

	/**
	 * 
	 * @param animation
	 */
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	@Override
	public IPip getFigureGraphic(IUnit unit) {
		IPip newPip = new Pip(board, unit);
		pips.add(newPip);
		return newPip;
	}

	@Override
	public IBoard getBoardGraphic() {
		return this.board;
	}

}
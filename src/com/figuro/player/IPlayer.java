/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.player;

import javafx.scene.Group;
import javafx.scene.control.Button;

import com.figuro.common.BoardState;
import com.figuro.engine.IMoveComplete;

/**
 * @author Aszalos Gyorgy, Mathe E. Botond
 */
public interface IPlayer {
	/**
	 * Gets the player id
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * Sets the player ID, which can be either 1 or 2 if it's a plyer, or above
	 * 2 if it's a spectator
	 * 
	 * @param id
	 */
	public void setId(int id);

	/**
	 * Sets the initial state of the game
	 * 
	 * @param board
	 */
	public void setInitialState(BoardState board);

	/**
	 * Makes a move then responds trough the callback
	 * 
	 * @param callback
	 */
	public void move(IMoveComplete callback);

	/**
	 * Called when validating with errors This does not mean an implementation
	 * cannot depend on Rules It means that it is not mandatory
	 * 
	 * @param board
	 */
	public void wrongMoveResetTo(BoardState board);

	/**
	 * Notifies the player that a new move has been made by the adversary.
	 * 
	 * @param counterMove
	 * */
	public void notify(BoardState counterMove);

	/**
	 * @return boolean if UI can call setup()
	 */
	public boolean needsSetup();

	/**
	 * Displays a small setup UI to the user. Will get the internal part of the
	 * dialog as parameter to add controls to it.
	 * 
	 * Note that if you use it save upon user modification, because you don't
	 * have control over parent dialog Crucial to NetPlayuer
	 * 
	 * @param parent
	 *            An object inside which you should place your controls
	 */
	public void setup(Group parent, Button okButton);

	/**
	 * Get the preferred order in the game
	 * 
	 * @return either 1 or 2, the player ID you might hold during game or 0 if
	 *         don't care (crucial to NetPlayer)
	 */
	public int getPrefferedOrder();
}

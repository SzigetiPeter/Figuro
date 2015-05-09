/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.common;

import com.figuro.engine.IEngineHandler;
import com.figuro.game.Game;
import com.figuro.player.IPlayer;

/**
 * @author Aszalos Gyorgy
 */
public interface IBuilder {
	public static final String NET_PLAYER = "player.net";
	public static final String ALPHABETA_PLAYER = "player.alphabeta";
	public static final String UI_PLAYER = "player.ui";

	public static final String CHECKERS_GAME = "game.checkers";

	public IEngineHandler createEngine();

	public String[] getGameTypes();

	public Game createGame(String type);

	public IPlayer createPlayer(String type);

	public void free();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.engine;

import com.figuro.player.IPlayer;

/**
 * @author Aszalos Gyorgy
 */
public interface IEngineHandler {
	public IPlayer addPlayer(String playerType);

	public IPlayer addSpectator(String playerType);

	public void removePlayers();

	public void runGame(String gameType, IGameoverCallback callback);

	public boolean isGameResumable();

	public void resumeGame(IGameoverCallback callback);

	public void exit();
}

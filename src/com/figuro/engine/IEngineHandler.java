/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.engine;

import com.figuro.game.Game;
import com.figuro.player.IPlayer;

/**
 * @author Aszalos Gyorgy
 */
public interface IEngineHandler {
    public void start();
    public void addPlayer(IPlayer player);
    public void addSpectator(IPlayer player);
    public void removePlayers();
    public void runGame(Game game);
    public void exit();
}

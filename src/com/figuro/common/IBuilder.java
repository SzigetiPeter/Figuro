/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.common;

import com.figuro.game.Game;
import com.figuro.player.IPlayer;

/**
 * @author Aszalos Gyorgy
 */
public interface IBuilder {
    public Game createGame(String type);
    public IPlayer createPlayer(String type);
}

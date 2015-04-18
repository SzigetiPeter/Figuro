/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.boardGraphics;

import javafx.scene.Scene;

/**
 * @author Aszalos Gyorgy
 */
public interface IBoardGraphics {
    public Scene getFigureGraphic(int figureId);
    public Scene getBoardGraphic();
}

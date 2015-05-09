/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.boardGraphics;

import com.figuro.common.IUnit;

/**
 * @author Aszalos Gyorgy
 */
public interface IBoardGraphics {
	public IPip getFigureGraphic(IUnit iUnit);

	public IBoard getBoardGraphic();

	public IAnimation getAnimation();
}

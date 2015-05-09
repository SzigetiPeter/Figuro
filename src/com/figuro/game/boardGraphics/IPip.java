package com.figuro.game.boardGraphics;

import javafx.scene.Group;

import com.figuro.common.IUnit;

/**
 *
 * @author SzPeter
 */
public interface IPip {
	public Group getPip();

	public void setPip(Group pip);

	public IUnit getUnit();

	public void setUnit(IUnit unit);
}

package com.figuro.game.boardGraphics;

import com.figuro.common.IUnit;

import javafx.scene.Group;

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

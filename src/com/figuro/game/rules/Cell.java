/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.ICell;
import com.figuro.common.IUnit;
import com.sun.glass.ui.Window;

/**
 *
 * @author Isti
 */
public class Cell implements ICell {
    private IUnit unit;
    
    public Cell()
    { }
    
    public Cell(IUnit unit)
    {
        this.unit = unit;
    }
    
    @Override
    public boolean hasUnit() {
        if (unit == null)
            return false;
        return true;
    }

    @Override
    public IUnit getUnit() {
        return unit;
    }

    @Override
    public void setUnit(IUnit unit) {
        this.unit = unit;
    }
    
}

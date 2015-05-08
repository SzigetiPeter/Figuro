/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.ICell;
import com.figuro.common.IUnit;
import com.sun.glass.ui.Window;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.unit);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cell other = (Cell) obj;
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        return true;
    }
    
    
    
}

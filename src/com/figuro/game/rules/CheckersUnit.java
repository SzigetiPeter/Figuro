/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.IUnit;
import com.figuro.common.Unit;

/**
 *
 * @author Isti
 */
public class CheckersUnit implements IUnit {
    private Unit unit;
    private int ownerId;
    
    public CheckersUnit(Unit unit, int ownerId)
    {
        this.unit = unit;
    }
    
    @Override
    public Unit getType() {
        return unit;
    }

    @Override
    public int getOwnerId() {
        return ownerId;
    }
    
}

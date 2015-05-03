/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.IUnit;

/**
 *
 * @author Isti
 */
public class CheckersUnit implements IUnit {
    private UnitEnum unit;
    private int ownerId;
    
    public CheckersUnit(UnitEnum unit, int ownerId)
    {
        this.unit = unit;
        this.ownerId = ownerId;
    }
    
    @Override
    public UnitEnum getType() {
        return unit;
    }

    @Override
    public int getOwnerId() {
        return ownerId;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.common;

/**
 *
 * @author Isti
 */
public interface ICell {
    public boolean hasUnit();
    public IUnit getUnit();
    public void setUnit(IUnit unit);
}

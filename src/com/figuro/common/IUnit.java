/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.common;

import com.figuro.game.rules.UnitEnum;

/**
 *
 * @author Isti
 */
public interface IUnit {
	public UnitEnum getType();

	public int getOwnerId(); // player id
}

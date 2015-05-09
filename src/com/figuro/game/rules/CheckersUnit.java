/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import java.util.Objects;

import com.figuro.common.IUnit;

/**
 *
 * @author Isti
 */
public class CheckersUnit implements IUnit {
	private UnitEnum unit;
	private int ownerId;

	public CheckersUnit(UnitEnum unit, int ownerId) {
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

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.unit);
		hash = 29 * hash + this.ownerId;
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
		final CheckersUnit other = (CheckersUnit) obj;
		if (this.unit != other.unit) {
			return false;
		}
		if (this.ownerId != other.ownerId) {
			return false;
		}
		return true;
	}

}

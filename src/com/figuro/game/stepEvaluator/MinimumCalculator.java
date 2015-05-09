/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.stepEvaluator;

import com.figuro.common.Evaluation;

/**
 *
 * @author Isti
 */
public class MinimumCalculator implements IMinimumCalculator {

	@Override
	public Evaluation min(Evaluation a, Evaluation b, int player) {
		if (Math.min(a.getScore(), b.getScore()) == a.getScore())
			return a;
		else if (Math.min(b.getScore(), a.getScore()) == b.getScore())
			return b;

		// if the scores are equal
		// the one who just performed a step has disadvantage

		if (b.getPlayer() == player)
			return b;
		// else if (a.getPlayer() == player)
		return a;
	}

}

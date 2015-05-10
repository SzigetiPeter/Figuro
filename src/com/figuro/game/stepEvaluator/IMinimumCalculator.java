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
public interface IMinimumCalculator {
	public Evaluation min(Evaluation a, Evaluation b, int player);
}

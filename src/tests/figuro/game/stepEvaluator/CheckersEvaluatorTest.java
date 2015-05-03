/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.stepEvaluator;

import com.figuro.common.BoardState;
import com.figuro.game.rules.Cell;
import com.figuro.common.Evaluation;
import com.figuro.common.ICell;
import com.figuro.common.IUnit;
import com.figuro.game.rules.UnitEnum;
import com.figuro.game.rules.CheckersUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Isti
 */
public class CheckersEvaluatorTest {
    
    public CheckersEvaluatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of evaluate method, of class CheckersEvaluator.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        int player = 1;
        IUnit peasant = new CheckersUnit(UnitEnum.PEASANT,1);
        ICell peasentCell = new Cell(peasant);
        ICell[][] cells = new Cell[8][8];
        cells[0][0] = peasentCell;
        BoardState state = new BoardState(cells);
        
        CheckersEvaluator instance = new CheckersEvaluator();
        Evaluation expResult = new Evaluation(player, 1);
        Evaluation result = instance.evaluate(state, player);
        assertEquals(expResult, result);
    }
    
        /**
     * Test of evaluate method, of class CheckersEvaluator.
     */
    @Test
    public void testEvaluate2() {
        System.out.println("evaluate");
        
        //player 1
        int player = 1;
        IUnit peasant = new CheckersUnit(UnitEnum.PEASANT,1);
        IUnit king = new CheckersUnit(UnitEnum.KING,1);
        ICell peasentCell = new Cell(peasant);
        ICell kingCell = new Cell(king);
        ICell[][] cells = new Cell[8][8];
        cells[0][0] = peasentCell;
        cells[3][4] = kingCell;
        
        //player 2
        IUnit kingEnemy = new CheckersUnit(UnitEnum.KING,2);
        ICell enemyKingCell = new Cell(kingEnemy);
        cells[6][5] = enemyKingCell;
        
        BoardState state = new BoardState(cells);
        
        CheckersEvaluator instance = new CheckersEvaluator();
        Evaluation expResult = new Evaluation(player, 4);
        Evaluation result = instance.evaluate(state, player);
        assertEquals(expResult, result);
    }

    /**
     * Test of min method, of class CheckersEvaluator.
     */
    @Test
    public void testMin() {
        System.out.println("min");
        int player = 1;
        Evaluation a = new Evaluation(player, 2);
        Evaluation b = new Evaluation(player, 3);
        
        CheckersEvaluator instance = new CheckersEvaluator();
        Evaluation expResult = new Evaluation(player, 2);
        Evaluation result = instance.min(a, b, player);
        assertEquals(expResult, result);
    }
    
}

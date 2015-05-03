/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.figuro.game.rules;

import com.figuro.common.BoardState;
import java.util.List;
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
public class CheckersRulesTest {
    
    public CheckersRulesTest() {
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
     * Test of getInitialState method, of class CheckersRules.
     */
    @Test
    public void testGetInitialState() {
        System.out.println("getInitialState");
        CheckersRules instance = new CheckersRules();
        BoardState expResult = null;
        BoardState result = instance.getInitialState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValidMove method, of class CheckersRules.
     */
    @Test
    public void testIsValidMove() {
        System.out.println("isValidMove");
        BoardState oldState = null;
        BoardState newState = null;
        int player = 0;
        CheckersRules instance = new CheckersRules();
        boolean expResult = false;
        boolean result = instance.isValidMove(oldState, newState, player);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPossibleMoves method, of class CheckersRules.
     */
    @Test
    public void testGetPossibleMoves() {
        System.out.println("getPossibleMoves");
        BoardState state = null;
        int player = 0;
        CheckersRules instance = new CheckersRules();
        List<BoardState> expResult = null;
        List<BoardState> result = instance.getPossibleMoves(state, player);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isGameOver method, of class CheckersRules.
     */
    @Test
    public void testIsGameOver() {
        System.out.println("isGameOver");
        BoardState state = null;
        int player = 0;
        CheckersRules instance = new CheckersRules();
        boolean expResult = false;
        boolean result = instance.isGameOver(state, player);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinalState method, of class CheckersRules.
     */
    @Test
    public void testGetFinalState() {
        System.out.println("getFinalState");
        BoardState state = null;
        int player = 0;
        CheckersRules instance = new CheckersRules();
        int expResult = 0;
        int result = instance.getFinalState(state, player);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextPlayer method, of class CheckersRules.
     */
    @Test
    public void testGetNextPlayer() {
        System.out.println("getNextPlayer");
        BoardState oldState = null;
        BoardState newState = null;
        int player = 0;
        CheckersRules instance = new CheckersRules();
        int expResult = 0;
        int result = instance.getNextPlayer(oldState, newState, player);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

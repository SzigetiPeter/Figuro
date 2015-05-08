/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.figuro.game.rules;

import com.figuro.common.BoardState;
import com.figuro.common.ICell;
import com.figuro.common.IUnit;
import java.awt.Point;
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
        
        expResult = new ChessBoardGenerator().getInitialBoard();
        int blackPlayerId = 1;
        int whitePlayerId = 2;
        
        ICell cell = null;
        Point point = null;
        IUnit unit = null;
        
    //initialize black
        
        //--1. row
        cell = new Cell();
        point = new Point();
        point.x = 1;
        point.y = 7;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 3;
        point.y = 7;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 5;
        point.y = 7;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 7;
        point.y = 7;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        //--2. row
        cell = new Cell();
        point = new Point();
        point.x = 0;
        point.y = 6;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 2;
        point.y = 6;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 4;
        point.y = 6;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 6;
        point.y = 6;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        //--3. row
        cell = new Cell();
        point = new Point();
        point.x = 1;
        point.y = 5;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 3;
        point.y = 5;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 5;
        point.y = 5;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 7;
        point.y = 5;
        unit = new CheckersUnit(UnitEnum.PEASANT, blackPlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
    //initialize while
        
        //--1. row
        cell = new Cell();
        point = new Point();
        point.x = 0;
        point.y = 0;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 2;
        point.y = 0;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 4;
        point.y = 0;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 6;
        point.y = 0;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        //--2. row
        cell = new Cell();
        point = new Point();
        point.x = 1;
        point.y = 1;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 3;
        point.y = 1;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 5;
        point.y = 1;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 7;
        point.y = 1;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        //--3. row
        cell = new Cell();
        point = new Point();
        point.x = 0;
        point.y = 2;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 2;
        point.y = 2;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 4;
        point.y = 2;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        cell = new Cell();
        point = new Point();
        point.x = 6;
        point.y = 2;
        unit = new CheckersUnit(UnitEnum.PEASANT, whitePlayerId);
        cell.setUnit(unit);
        expResult.set(point, cell);
        
        
        BoardState result = instance.getInitialState();
        assertEquals(expResult, result);
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
    }

    /**
     * Test of isGameOver method, of class CheckersRules.
     */
    @Test
    public void testIsGameOver() {
        System.out.println("isGameOver");
        CheckersRules instance = new CheckersRules();
        BoardState state = instance.getInitialState();
        int player = 1;
        boolean expResult = false;
        boolean result = instance.isGameOver(state, player);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isGameOver method, of class CheckersRules.
     */
    @Test
    public void testIsGameOver2() {
        System.out.println("isGameOver2");
        CheckersRules instance = new CheckersRules();
        ChessBoardGenerator boardGenerator = new ChessBoardGenerator();
        BoardState state = boardGenerator.getInitialBoard();
        int player = 1;
        boolean expResult = true;
        boolean result = instance.isGameOver(state, player);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isGameOver method, of class CheckersRules.
     */
    @Test
    public void testIsGameOver3() {
        System.out.println("isGameOver3");
        CheckersRules instance = new CheckersRules();
        ChessBoardGenerator boardGenerator = new ChessBoardGenerator();
        BoardState state = boardGenerator.getInitialBoard();
        state.set(new Point(5,5), new Cell(new CheckersUnit(UnitEnum.PEASANT, 2)));
        int player = 2;
        boolean expResult = false;
        boolean result = instance.isGameOver(state, player);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFinalState method, of class CheckersRules.
     */
    @Test
    public void testGetFinalState() {
        System.out.println("getFinalState");
        CheckersRules instance = new CheckersRules();
        ChessBoardGenerator boardGenerator = new ChessBoardGenerator();
        BoardState state = boardGenerator.getInitialBoard();
        state.set(new Point(5,5), new Cell(new CheckersUnit(UnitEnum.PEASANT, 2)));
        int player = 1;
        
        int expResult = 2;
        int result = instance.getFinalState(state, player);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNextPlayer method, of class CheckersRules.
     */
    @Test
    public void testGetNextPlayer() {
        System.out.println("getNextPlayer");
        CheckersRules instance = new CheckersRules();
        BoardState oldState = instance.getInitialState();
        BoardState newState = instance.getInitialState();
        
        Point beginPoint = new Point(1, 5);
        Point endPoint = new Point(0, 4);
        ICell cell = newState.get(beginPoint);
        newState.set(beginPoint, new Cell());
        newState.set(endPoint, cell);
        int player = 1;
        
        int expResult = 2;
        int result = instance.getNextPlayer(oldState, newState, player);
        assertEquals(expResult, result);
    }
    
}

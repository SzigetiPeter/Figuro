package tests.figuro.common;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

import com.figuro.common.BoardState;

/**
 * @author Mathe E. Botond
 */
public class BoardStateTest {

	@Test
	public void testLastMove() {
		BoardState board = new BoardState(8, 8);
		board.setLatestMoved(new Point(3, 3));
		Point lastMove = board.getLastMove();
		assertEquals(lastMove.x, 3);
	}

}

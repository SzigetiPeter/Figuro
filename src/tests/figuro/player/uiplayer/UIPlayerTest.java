package tests.figuro.player.uiplayer;

import java.awt.Point;

import javafx.application.Application;
import javafx.stage.Stage;

import org.junit.Test;

import com.figuro.common.BoardState;
import com.figuro.common.IUnit;
import com.figuro.engine.IMoveComplete;
import com.figuro.game.rules.Cell;
import com.figuro.game.rules.CheckersUnit;
import com.figuro.game.rules.UnitEnum;
import com.figuro.player.IPlayer;
import com.figuro.player.uiplayer.UIPlayer;

/**
 *
 * @author SzPeter
 */
public class UIPlayerTest {

    // @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    static boolean stop = false;

    public static class BoardTester extends Application {
        private IPlayer player;
        private BoardState state;

        @Override
        public void start(Stage primaryStage) throws Exception {
            player = new UIPlayer(primaryStage);
            placePips();
            notifyTest();
            moveAndReset();
            primaryStage.show();
        }

        /**
         * Function to test the placement of player figures.
         * */
        public void placePips() {
            BoardState st = new BoardState(8, 8);
            Cell black = new Cell();
            Cell white = new Cell();
            black.setUnit(new CheckersUnit(UnitEnum.PEASANT, 1));
            white.setUnit(new CheckersUnit(UnitEnum.PEASANT, 2));

            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    st.set(new Point(i, j), new Cell());

            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 4; j++) {
                    int m = (i % 2) == 0 ? 0 : 1;
                    st.set(new Point(j * 2 + m, i), black);
                    st.set(new Point(j * 2 + (1 - m), 7 - i), white);
                }
            state = st;
            player.setInitialState(st);
        }

        /**
         * Function to wait for a move event and then reset the layout to the default test layout.
         * */
        public void moveAndReset() {
            System.out.println("InitiaL: \n " + state.toString());
            player.move(new IMoveComplete() {

                @Override
                public void setResult(BoardState result) {
                    System.out.println("Moved: \n " + result.toString());
                    player.wrongMoveResetTo(state);
                    System.out.println("Reset: \n " + state.toString());

                }
            });
        }

        /**
         * Function to test if board change notification is working.
         * */
        public void notifyTest() {
            BoardState newBoardState = new BoardState(this.state);
            Point from = new Point(5, 1);
            Point to = new Point(4, 4);

            newBoardState.set(to, state.get(from));
            newBoardState.set(from, new Cell());
            newBoardState.setLatestMoved(to);
            newBoardState.setLatestMovedFrom(from);
            player.notify(newBoardState);
        }

    }

    @Test
    public void UiPlayerTest() {
        System.out.println("BoardCreationTest");
        Application.launch(BoardTester.class, new String[0]);
    }

}

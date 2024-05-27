package org.iesvdm.tddjava.connect4;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class Connect4TDDSpec {

    /**
     * clase bajo testeo
     */
    private Connect4TDD tested;

    private OutputStream output;

    @BeforeEach
    public void beforeEachTest() {
        output = new ByteArrayOutputStream();

        //Se instancia el juego modificado para acceder a la salida de consola
        tested = new Connect4TDD(new PrintStream(output));
    }

    /*
     * The board is composed by 7 horizontal and 6 vertical empty positions
     */

    @Test
    public void whenTheGameIsStartedTheBoardIsEmtpy() {
        assertThat(tested.getNumberOfDiscs()).isEqualTo(0);
    }

    /*
     * Players introduce discs on the top of the columns.
     * Introduced disc drops down the board if the column is empty.
     * Future discs introduced in the same column will stack over previous ones
     */

    @Test
    public void whenDiscOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> tested.putDiscInColumn(-1));
        assertThrows(RuntimeException.class, () -> tested.putDiscInColumn(Connect4TDD.COLUMNS));
    }

    @Test
    public void whenFirstDiscInsertedInColumnThenPositionIsZero() {
        assertThat(tested.putDiscInColumn(0)).isEqualTo(0);
    }

    @Test
    public void whenSecondDiscInsertedInColumnThenPositionIsOne() {
        int column = 0;
        tested.putDiscInColumn(column);
        assertThat(tested.putDiscInColumn(column)).isEqualTo(1);
    }

    @Test
    public void whenDiscInsertedThenNumberOfDiscsIncreases() {
        int column = 0;
        tested.putDiscInColumn(column);
        assertThat(tested.getNumberOfDiscs()).isEqualTo(1);
    }

    @Test
    public void whenNoMoreRoomInColumnThenRuntimeException() {
        int column = 0;
        for (int row = 0; row < Connect4TDD.ROWS; ++row) {
            tested.putDiscInColumn(column);
        }
        assertThrows(RuntimeException.class, () -> tested.putDiscInColumn(column));
    }

    /*
     * It is a two-person game so there is one colour for each player.
     * One player uses red ('R'), the other one uses green ('G').
     * Players alternate turns, inserting one disc every time
     */

    @Test
    public void whenFirstPlayerPlaysThenDiscColorIsRed() {
        assertThat(tested.getCurrentPlayer()).isEqualTo(Connect4TDD.RED);
    }

    @Test
    public void whenSecondPlayerPlaysThenDiscColorIsGreen() {
        tested.putDiscInColumn(0);
        assertThat(tested.getCurrentPlayer()).isEqualTo(Connect4TDD.GREEN);
    }
    /*
     * We want feedback when either, event or error occur within the game.
     * The output shows the status of the board on every move
     */

    @Test
    public void whenAskedForCurrentPlayerTheOutputNotice() {
        assertThat(output.toString()).contains("Player R turn");
    }

    @Test
    public void whenADiscIsIntroducedTheBoardIsPrinted() {
        tested.putDiscInColumn(0);
        assertThat(output.toString()).contains("| | | | | | | |");
    }

    /*
     * When no more discs can be inserted, the game finishes and it is considered a draw
     */

    @Test
    public void whenTheGameStartsItIsNotFinished() {
        assertThat(tested.isFinished()).isFalse();
    }

    @Test
    public void whenNoDiscCanBeIntroducedTheGamesIsFinished() {
        for (int row = 0; row < Connect4TDD.ROWS; row++) {
            for (int column = 0; column < Connect4TDD.COLUMNS; column++) {
                tested.putDiscInColumn(column);
            }
        }
        assertThat(tested.isFinished()).isTrue();
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight vertical line then that player wins
     */

    @Test
    public void when4VerticalDiscsAreConnectedThenThatPlayerWins() {
        for (int row = 0; row < 3; row++) {
            tested.putDiscInColumn(0); // R
            tested.putDiscInColumn(1); // G
        }
        assertThat(tested.putDiscInColumn(0)).isEqualTo(3); // R
        assertThat(tested.getWinner()).isEqualTo(Connect4TDD.RED);
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight horizontal line then that player wins
     */

    @Test
    public void when4HorizontalDiscsAreConnectedThenThatPlayerWins() {
        int column;
        for (column = 0; column < 3; column++) {
            tested.putDiscInColumn(column); // R
            tested.putDiscInColumn(column); // G
        }
        assertThat(tested.putDiscInColumn(column)).isEqualTo(0); // R
        assertThat(tested.getWinner()).isEqualTo(Connect4TDD.RED);
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight diagonal line then that player wins
     */

    @Test
    public void when4Diagonal1DiscsAreConnectedThenThatPlayerWins() {
        int column;
        int row;
        int[] columns = {0, 1, 1, 2, 2, 3};
        int[] rows = {3, 2, 1, 1, 0, 0};
        for (int i = 0; i < columns.length; i++) {
            column = columns[i];
            row = rows[i];
            tested.putDiscInColumn(column);
            tested.putDiscInColumn(column);
        }
        assertThat(tested.putDiscInColumn(3)).isEqualTo(0);
        assertThat(tested.getWinner()).isEqualTo(Connect4TDD.RED);
    }

    @Test
    public void when4Diagonal2DiscsAreConnectedThenThatPlayerWins() {
        int column;
        int row;
        int[] columns = {3, 2, 2, 1, 1, 0};
        int[] rows = {3, 2, 1, 1, 0, 0};
        for (int i = 0; i < columns.length; i++) {
            column = columns[i];
            row = rows[i];
            tested.putDiscInColumn(column);
            tested.putDiscInColumn(column);
        }
        assertThat(tested.putDiscInColumn(0)).isEqualTo(0);
        assertThat(tested.getWinner()).isEqualTo(Connect4TDD.RED);
    }
}

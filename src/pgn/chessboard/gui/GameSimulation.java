package pgn.chessboard.gui;

import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.figures.Figure;
import pgn.tokenizer.TokenizedGame;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 19.06.14
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class GameSimulation {
    private TokenizedGame game;
    private ChessBoardPanel panel;
    private ChessBoard chessBoard;
    private List<Figure[][]> boards = new LinkedList<>();
    private ListIterator<Figure[][]> iterator = boards.listIterator();

    public GameSimulation(ChessBoardPanel panel, TokenizedGame game) {
        this.panel = panel;
        this.game = game;
        this.chessBoard = new ChessBoard();
        this.drawStartBoard();
    }


    public void drawStartBoard() {
        Object[][] boardTemp = chessBoard.getBoardCopy();
        if(boardTemp instanceof Figure[][]) {
            Figure[][] board = (Figure[][]) boardTemp;
            iterator.add(board);
            iterator.next();
            panel.updateBoard(board);
            panel.repaint();
        }
    }

    public void drawNextBoard() {
        if(iterator.hasNext()) {
            Figure[][] board = iterator.next();
            panel.updateBoard(board);
            panel.repaint();
        }
        else {

        }
    }

    public void drawPrevBoard() {
        if(iterator.hasPrevious()) {
            Figure[][] board = iterator.previous();
            panel.updateBoard(board);
            panel.repaint();
        }
    }

}

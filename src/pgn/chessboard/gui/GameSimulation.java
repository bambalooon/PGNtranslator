package pgn.chessboard.gui;

import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.figures.Figure;
import pgn.tokenizer.TokenizedGame;

import java.io.IOException;

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

    public GameSimulation(ChessBoardPanel panel, TokenizedGame game) {
        this.panel = panel;
        this.game = game;
        this.chessBoard = new ChessBoard();
        this.drawBoard();
    }


    public void drawBoard() {
        Object[][] boardTemp = chessBoard.getBoardCopy();
        if(boardTemp instanceof Figure[][]) {
            Figure[][] board = (Figure[][]) boardTemp;
            panel.updateBoard(board);
            panel.repaint();
        }
    }
}

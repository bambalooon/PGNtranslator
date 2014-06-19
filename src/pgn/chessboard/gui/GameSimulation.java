package pgn.chessboard.gui;

import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.figures.Figure;
import pgn.chessboard.players.ChessPlayer;
import pgn.parser.Parser;
import pgn.tokenizer.TokenizedGame;

import java.io.IOException;
import java.text.ParseException;
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
    private Iterator<TokenizedGame.MovePair> moveIterator;
    private TokenizedGame.MovePair movePair;
    private ChessPlayer currentPlayer = ChessPlayer.WHITE;
    private Parser parser;

    public GameSimulation(ChessBoardPanel panel, TokenizedGame game, Parser parser) {
        this.panel = panel;
        this.game = game;
        this.parser = parser;
        this.chessBoard = new ChessBoard();
        this.drawStartBoard();
    }


    private void drawStartBoard() {
        Figure[][] board = chessBoard.getBoardCopy();
        iterator.add(board);
        if(iterator.hasNext()) {
            iterator.next();
        }
        moveIterator = game.getMoves().iterator();
        panel.updateBoard(board);
        panel.repaint();
    }

    public void drawNextBoard() throws ParseException {
        Figure[][] board;
        if(iterator.hasNext()) {
            board = iterator.next();
            panel.updateBoard(board);
            panel.repaint();
        }
        else {
            if(currentPlayer==ChessPlayer.WHITE) {
                if(moveIterator.hasNext()) {
                    movePair = moveIterator.next();
                    parser.parseMove(movePair.getWhite(), ChessPlayer.WHITE, chessBoard);
                    board = chessBoard.getBoardCopy();
                    iterator.add(board);
                    currentPlayer = ChessPlayer.BLACK;
                    panel.updateBoard(board);
                    panel.repaint();
                }
                //throw error?
            }
            else if(currentPlayer==ChessPlayer.BLACK) {
                if(movePair.getBlack()!=null) {
                    parser.parseMove(movePair.getBlack(), ChessPlayer.BLACK, chessBoard);
                    board = chessBoard.getBoardCopy();
                    iterator.add(board);
                    currentPlayer = ChessPlayer.WHITE;
                    panel.updateBoard(board);
                    panel.repaint();
                }
                //throw
            }
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

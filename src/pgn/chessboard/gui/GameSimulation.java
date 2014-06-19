package pgn.chessboard.gui;

import pgn.application.PGNtranslator;
import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.figures.Figure;
import pgn.chessboard.players.ChessPlayer;
import pgn.parser.GameProgressException;
import pgn.parser.Parser;
import pgn.tokenizer.TokenizedGame;

import javax.swing.*;
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
    private Figure[][] board;

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

    public void drawNextBoard() throws ParseException, GameProgressException {
        Figure[][] board;
        if(iterator.hasNext()) {
            board = iterator.next();
            if(board==this.board && iterator.hasNext()) {
                board = iterator.next();
            }
            else if(board==this.board) {
                makeNextMove();
                return;
            }
            this.board = board;
            panel.updateBoard(board);
            panel.repaint();
        }
        else {
            makeNextMove();
            return;
        }
        if(this.board==null) return;
    }

    private void makeNextMove() throws ParseException, GameProgressException {
        try {
            if(currentPlayer==ChessPlayer.WHITE) {
                if(moveIterator.hasNext()) {
                    currentPlayer = ChessPlayer.BLACK;
                    movePair = moveIterator.next();
                    parser.parseMove(movePair.getWhite(), ChessPlayer.WHITE, chessBoard);
                }
            }
            else if(currentPlayer==ChessPlayer.BLACK) {
                if(movePair.getBlack()!=null) {
                    currentPlayer = ChessPlayer.WHITE;
                    parser.parseMove(movePair.getBlack(), ChessPlayer.BLACK, chessBoard);
                }
            }
            if(!moveIterator.hasNext() && currentPlayer==ChessPlayer.WHITE) {

            }
            else if(movePair.getBlack()==null && currentPlayer==ChessPlayer.BLACK) {

            }
        } catch (GameProgressException e) {
            this.updateBoard();
            if(!moveIterator.hasNext() && currentPlayer==ChessPlayer.WHITE) {

            }
            else if(movePair.getBlack()==null && currentPlayer==ChessPlayer.BLACK) {

            }
            throw e;
        }
        this.updateBoard();
    }

    public void drawPrevBoard() {
        if(iterator.hasPrevious()) {
            Figure[][] board = iterator.previous();
            if(board==this.board && iterator.hasPrevious()) {
                board = iterator.previous();
            }
            this.board = board;
            panel.updateBoard(board);
            panel.repaint();
        }
    }

    public void updateBoard() {
        this.board = chessBoard.getBoardCopy();
        iterator.add(this.board);
        panel.updateBoard(this.board);
        panel.repaint();
    }

}

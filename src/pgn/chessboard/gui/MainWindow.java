package pgn.chessboard.gui;

import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.figures.Figure;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 02.06.14
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public class MainWindow extends JFrame {

    ChessBoardPanel boardPanel;

    public MainWindow(String windowName) {
        super(windowName);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            boardPanel = new ChessBoardPanel();
        } catch (IOException e) {
//            print error
        }
        //controls
        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        //add(controls)
        pack();
    }

    public void drawBoard() throws IOException {
        ChessBoard checkboard = new ChessBoard();
        Object[][] boardTemp = checkboard.getBoardCopy();
        if(boardTemp instanceof Figure[][]) {
            Figure[][] board = (Figure[][]) boardTemp;
            boardPanel.updateBoard(board);
            boardPanel.repaint();
        }
    }
}

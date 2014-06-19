package pgn.chessboard.gui;

import pgn.chessboard.figures.Figure;
import pgn.chessboard.players.ChessPlayer;

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
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class ChessBoardPanel extends JPanel {
    private static class ChessBoardIcons {
        public static final String directory = "chessboard/";
        public static final File board = new File(ChessBoardIcons.directory+"chessboard.png");
        public static BufferedImage bBishop;
        public static BufferedImage wBishop;
        public static BufferedImage bKing;
        public static BufferedImage wKing;
        public static BufferedImage bKnight;
        public static BufferedImage wKnight;
        public static BufferedImage bPawn;
        public static BufferedImage wPawn;
        public static BufferedImage bQueen;
        public static BufferedImage wQueen;
        public static BufferedImage bRook;
        public static BufferedImage wRook;
        public static final int xOffset = 5;
        public static final int yOffset = 5;
        public static final int cellWidth = 74;
        public static final int cellHeight = 74;

        static {
            try {
                bBishop = ImageIO.read(new File(ChessBoardIcons.directory+"b_bishop.png"));
                wBishop = ImageIO.read(new File(ChessBoardIcons.directory+"w_bishop.png"));
                bKing = ImageIO.read(new File(ChessBoardIcons.directory+"b_king.png"));
                wKing = ImageIO.read(new File(ChessBoardIcons.directory+"w_king.png"));
                bKnight = ImageIO.read(new File(ChessBoardIcons.directory+"b_knight.png"));
                wKnight = ImageIO.read(new File(ChessBoardIcons.directory+"w_knight.png"));
                bPawn = ImageIO.read(new File(ChessBoardIcons.directory+"b_pawn.png"));
                wPawn = ImageIO.read(new File(ChessBoardIcons.directory+"w_pawn.png"));
                bQueen = ImageIO.read(new File(ChessBoardIcons.directory+"b_queen.png"));
                wQueen = ImageIO.read(new File(ChessBoardIcons.directory+"w_queen.png"));
                bRook = ImageIO.read(new File(ChessBoardIcons.directory+"b_rook.png"));
                wRook = ImageIO.read(new File(ChessBoardIcons.directory+"w_rook.png"));
            } catch (IOException e) {
                //HUGE error...
            }

        }
    }
    private BufferedImage board;
    private BufferedImage editedBoard;

    public ChessBoardPanel() throws IOException {
        this.board = ImageIO.read(ChessBoardIcons.board);
        this.editedBoard = board;
        this.setPreferredSize(new Dimension(
            board.getWidth(), board.getHeight()
        ));
    }

    public void updateBoard(Figure[][] board) {
        int w = this.board.getWidth();
        int h = this.board.getHeight();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(this.board, 0, 0, null);
        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[y].length; x++) {
                if(board[y][x]!=null) {
                    int xpos = ChessBoardIcons.xOffset+ChessBoardIcons.cellWidth*x;
                    int trans_y = board.length-y-1;
                    int ypos = ChessBoardIcons.yOffset+ChessBoardIcons.cellHeight*trans_y;
                    switch (board[y][x].getClass().getSimpleName()) {
                        case "Pawn":
                            if(board[y][x].getOwner()== ChessPlayer.WHITE) {
                                g2d.drawImage(ChessBoardIcons.wPawn, xpos, ypos, null);
                            } else {
                                g2d.drawImage(ChessBoardIcons.bPawn, xpos, ypos, null);
                            }
                            break;
                        case "Bishop":
                            if(board[y][x].getOwner()== ChessPlayer.WHITE) {
                                g2d.drawImage(ChessBoardIcons.wBishop, xpos, ypos, null);
                            } else {
                                g2d.drawImage(ChessBoardIcons.bBishop, xpos, ypos, null);
                            }
                            break;
                        case "Rook":
                            if(board[y][x].getOwner()== ChessPlayer.WHITE) {
                                g2d.drawImage(ChessBoardIcons.wRook, xpos, ypos, null);
                            } else {
                                g2d.drawImage(ChessBoardIcons.bRook, xpos, ypos, null);
                            }
                            break;
                        case "Knight":
                            if(board[y][x].getOwner()== ChessPlayer.WHITE) {
                                g2d.drawImage(ChessBoardIcons.wKnight, xpos, ypos, null);
                            } else {
                                g2d.drawImage(ChessBoardIcons.bKnight, xpos, ypos, null);
                            }
                            break;
                        case "Queen":
                            if(board[y][x].getOwner()== ChessPlayer.WHITE) {
                                g2d.drawImage(ChessBoardIcons.wQueen, xpos, ypos, null);
                            } else {
                                g2d.drawImage(ChessBoardIcons.bQueen, xpos, ypos, null);
                            }
                            break;
                        case "King":
                            if(board[y][x].getOwner()== ChessPlayer.WHITE) {
                                g2d.drawImage(ChessBoardIcons.wKing, xpos, ypos, null);
                            } else {
                                g2d.drawImage(ChessBoardIcons.bKing, xpos, ypos, null);
                            }
                            break;
                    }
                }
            }
        }
        g2d.dispose();
        editedBoard = image;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(editedBoard, 0, 0, editedBoard.getWidth(), editedBoard.getHeight(), null);
    }
}

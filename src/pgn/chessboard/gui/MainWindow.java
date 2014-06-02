package pgn.chessboard.gui;

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

    JPanel main;
    ImageIcon board;

    public MainWindow(String windowName) {
        super(windowName);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main = new JPanel();
        board = new ImageIcon();
        JLabel label = new JLabel(board);
        main.add(label);
        //controls
        setLayout(new FlowLayout());
        add(main);
        //add(controls)
        pack();
    }

    public void drawBoard() throws IOException {
        BufferedImage board = ImageIO.read(new File("chessboard/chessboard.png"));
    }
}

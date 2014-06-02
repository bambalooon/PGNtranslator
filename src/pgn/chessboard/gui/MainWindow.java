package pgn.chessboard.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 02.06.14
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public class MainWindow extends JFrame {

    public MainWindow(String windowName) {
        super(windowName);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //controls
        setLayout(new FlowLayout());
        //add(controls)
        pack();
    }
}

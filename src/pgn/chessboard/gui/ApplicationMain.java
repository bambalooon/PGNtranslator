package pgn.chessboard.gui;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 02.06.14
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationMain {

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainWindow wnd = new MainWindow("Chess PGN translator");
                wnd.setVisible(true);
            }
        });
    }
}

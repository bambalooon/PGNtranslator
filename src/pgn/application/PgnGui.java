package pgn.application;

import pgn.chessboard.gui.ChessBoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 08.06.14
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public abstract class PgnGui extends JFrame implements ActionListener {
    private static final String PREV = "PREV";
    private static final String NEXT = "NEXT";
    protected PGNtranslator application;
    protected JButton prevWindowBtn, nextWindowBtn;


    public PgnGui(PGNtranslator application) {
        super(PGNtranslator.windowName);
        this.application = application;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.createWindowNavButtons();
        pack();
    }

    protected void createWindowNavButtons() {
        prevWindowBtn = new JButton();
        prevWindowBtn.addActionListener(this);
        prevWindowBtn.setActionCommand(PREV);
        prevWindowBtn.setText("PREV");

        nextWindowBtn = new JButton();
        nextWindowBtn.addActionListener(this);
        nextWindowBtn.setActionCommand(NEXT);
        nextWindowBtn.setText("NEXT");
    }

    public void actionPerformed(ActionEvent e) {
        //zmiana okien
        //app ma jakas liste.. niech on sie tym zajmie.
        switch (e.getActionCommand()) {
            case NEXT:
                application.nextWindow(this);
                break;
            case PREV:
                application.prevWindow(this);
                break;
        }
    }
}

package pgn.chessboard.gui;

import pgn.application.PGNtranslator;
import pgn.application.PgnGui;
import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.figures.Figure;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ChessboardGui extends PgnGui {
    static final private String PLAY = "play";
    static final private String PAUSE = "pause";
    static final private String STOP = "stop";
    static final private String NEXT = "next";
    static final private String PREV = "prev";

    ChessBoardPanel boardPanel;

    private JButton playBtn;
    private JButton pauseBtn;
    private JButton stopBtn;
    private JButton nextBtn;
    private JButton prevBtn;

    public static void main(String... args) {
        ChessboardGui g = new ChessboardGui(null);
        g.setVisible(true);
    }

    public ChessboardGui(PGNtranslator app) {
        super(app);
        try {
            boardPanel = new ChessBoardPanel();
        } catch (IOException e) {
//            print error
        }
        JToolBar playBar = new JToolBar("Play options");
        playBar.add(prevWindowBtn);
        addPlayButtons(playBar);
        //controls
        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(playBar, BorderLayout.PAGE_END);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        JOptionPane.showMessageDialog(
                null,
                "Action: "+e.getActionCommand(),
                "",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    protected void addPlayButtons(JToolBar toolBar) {
        JButton button;

        prevBtn = new JButton();
        prevBtn.addActionListener(this);
        prevBtn.setActionCommand(PREV);
        prevBtn.setText("PREV");
        toolBar.add(prevBtn);

        playBtn = new JButton();
        playBtn.addActionListener(this);
        playBtn.setActionCommand(PLAY);
        playBtn.setText("PLAY");
        toolBar.add(playBtn);

        pauseBtn = new JButton();
        pauseBtn.addActionListener(this);
        pauseBtn.setActionCommand(PAUSE);
        pauseBtn.setText("PAUSE");
        pauseBtn.setVisible(false);
        toolBar.add(pauseBtn);

        stopBtn = new JButton();
        stopBtn.addActionListener(this);
        stopBtn.setActionCommand(STOP);
        stopBtn.setText("STOP");
        toolBar.add(stopBtn);

        nextBtn = new JButton();
        nextBtn.addActionListener(this);
        nextBtn.setActionCommand(NEXT);
        nextBtn.setText("NEXT");
        toolBar.add(nextBtn);


    }

    public ChessBoardPanel getBoardPanel() {
        return boardPanel;
    }
}

package pgn.chessboard.gui;

import pgn.application.PGNtranslator;
import pgn.application.PgnGui;
import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.figures.Figure;
import pgn.chessboard.players.ChessPlayer;
import pgn.parser.GameProgressException;
import pgn.tokenizer.ComboBoxGame;
import pgn.tokenizer.TokenizedGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

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
    static final private String GAME_CHOOSE = "GAME_CHOOSE";

    ChessBoardPanel boardPanel;

    private JButton playBtn;
    private JButton pauseBtn;
    private JButton stopBtn;
    private JButton nextBtn;
    private JButton prevBtn;

    private JComboBox<ComboBoxGame> gameChooser;
    private SimulationPlayer simPlayer;

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
        gameChooser = new JComboBox<>();
        gameChooser.setVisible(false);
        gameChooser.addActionListener(this);
        gameChooser.setActionCommand(GAME_CHOOSE);

        JToolBar playBar = new JToolBar("Play options");
        playBar.add(prevWindowBtn);
        addPlayButtons(playBar);
        //controls
        setLayout(new BorderLayout());
        add(gameChooser, BorderLayout.PAGE_START);
        add(boardPanel, BorderLayout.CENTER);
        add(playBar, BorderLayout.PAGE_END);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        try {
            ComboBoxGame cbgame;
            switch (e.getActionCommand()) {
                case PREV:
                    application.getSimulation().drawPrevBoard();
                    break;
                case NEXT:
                    try {
                        application.getSimulation().drawNextBoard();
                    } catch (GameProgressException ex) {
                        TokenizedGame game = ((ComboBoxGame)gameChooser.getSelectedItem()).getGame();
                        if(ex.isCheckMate()) {
                            String player = ((ex.getPlayer()==ChessPlayer.WHITE) ? game.getWhite() : game.getBlack());
                            JOptionPane.showMessageDialog(this, "Szach mat!\nWygrał "+player, game.getWhite()+" vs. "+game.getBlack(), JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(ex.isEnd()) {
                            String result = ((game.getResult()=="1-0") ? "Wygrał: "+game.getWhite() : ((game.getResult()=="0-1") ? "Wygrał: "+game.getBlack() : "Remis"));
                            JOptionPane.showMessageDialog(this, "Koniec gry!\n"+result, game.getWhite()+" vs. "+game.getBlack(), JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;
                case GAME_CHOOSE:
                    simPlayer.stop();
                    simPlayer = null;
                    playBtn.setVisible(true);
                    pauseBtn.setVisible(false);
                    cbgame = (ComboBoxGame) gameChooser.getSelectedItem();
                    application.createGameSimulation(boardPanel, cbgame.getGame());
                    break;
                case PLAY:
                    simPlayer = new SimulationPlayer(this, application.getSimulation());
                    simPlayer.start();
                    playBtn.setVisible(false);
                    pauseBtn.setVisible(true);
                    break;
                case STOP:
                    simPlayer.stop();
                    cbgame = (ComboBoxGame) gameChooser.getSelectedItem();
                    application.createGameSimulation(boardPanel, cbgame.getGame());
                    simPlayer = null;
                    playBtn.setVisible(true);
                    pauseBtn.setVisible(false);
                    break;
                case PAUSE:
                    simPlayer.stop();
                    playBtn.setVisible(true);
                    pauseBtn.setVisible(false);
                    break;
            }
        } catch (ParseException ex) {

        }
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

    public void updateGameList() {
        gameChooser.setModel(new DefaultComboBoxModel<ComboBoxGame>());
        java.util.List<TokenizedGame> games = application.getParsedGames();
        if(games==null) {
            gameChooser.setVisible(false);
        }
        else {
            gameChooser.setVisible(true);
            for(TokenizedGame game : games) {
                gameChooser.addItem(new ComboBoxGame(game));
            }
        }
        this.pack();
        this.revalidate();
        this.repaint();
    }

    public ChessBoardPanel getBoardPanel() {
        return boardPanel;
    }
}

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
import java.net.URL;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 02.06.14
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public class ChessboardGui extends PgnGui {
    private static class PlayerIcons {
        public static final String directory = "media/";
        public static BufferedImage play;
        public static BufferedImage prev;
        public static BufferedImage pause;
        public static BufferedImage stop;
        public static BufferedImage next;
        static {
            try {
                play = ImageIO.read(new File(PlayerIcons.directory+"Play24.gif"));
                prev = ImageIO.read(new File(PlayerIcons.directory+"StepBack24.gif"));
                next = ImageIO.read(new File(PlayerIcons.directory+"StepForward24.gif"));
                pause = ImageIO.read(new File(PlayerIcons.directory+"Pause24.gif"));
                stop = ImageIO.read(new File(PlayerIcons.directory+"Stop24.gif"));
            } catch (IOException e) {
                //HUGE error...
            }

        }
    }
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
                        TokenizedGame game = application.getSimulation().getGame();
                        if(ex.isCheckMate()) {
                            String player = ((ex.getPlayer()==ChessPlayer.WHITE) ? game.getWhite() : game.getBlack());
                            JOptionPane.showMessageDialog(this, "Szach mat!\nWygrał "+player, game.getWhite()+" vs. "+game.getBlack(), JOptionPane.INFORMATION_MESSAGE);
                            if(simPlayer!=null) {
                                simPlayer.stop();
                            }
                        }
                        else if(ex.isEnd()) {
                            String result = ((game.getResult().equals("1-0")) ? "Wygrał: "+game.getWhite() : ((game.getResult().equals("0-1")) ? "Wygrał: "+game.getBlack() : "Remis"));
                            JOptionPane.showMessageDialog(this, "Koniec gry!\n"+result, game.getWhite()+" vs. "+game.getBlack(), JOptionPane.INFORMATION_MESSAGE);
                            if(simPlayer!=null) {
                                simPlayer.stop();
                            }
                        }
                    }
                    break;
                case GAME_CHOOSE:
                    if(simPlayer!=null) {
                        simPlayer.stop();
                    }
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
                    if(simPlayer!=null) {
                        simPlayer.stop();
                    }
                    application.createGameSimulation(boardPanel, application.getSimulation().getGame());
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


        ImageIcon prevIcon = new ImageIcon(PlayerIcons.prev);
        ImageIcon nextIcon = new ImageIcon(PlayerIcons.next);
        ImageIcon playIcon = new ImageIcon(PlayerIcons.play);
        ImageIcon pauseIcon = new ImageIcon(PlayerIcons.pause);
        ImageIcon stopIcon = new ImageIcon(PlayerIcons.stop);

        prevBtn = new JButton(prevIcon);
        prevBtn.addActionListener(this);
        prevBtn.setActionCommand(PREV);
        prevBtn.setText("");
        toolBar.add(prevBtn);

        playBtn = new JButton(playIcon);
        playBtn.addActionListener(this);
        playBtn.setActionCommand(PLAY);
        playBtn.setText("");
        toolBar.add(playBtn);

        pauseBtn = new JButton(pauseIcon);
        pauseBtn.addActionListener(this);
        pauseBtn.setActionCommand(PAUSE);
        pauseBtn.setText("");
        pauseBtn.setVisible(false);
        toolBar.add(pauseBtn);

        stopBtn = new JButton(stopIcon);
        stopBtn.addActionListener(this);
        stopBtn.setActionCommand(STOP);
        stopBtn.setText("");
        toolBar.add(stopBtn);

        nextBtn = new JButton(nextIcon);
        nextBtn.addActionListener(this);
        nextBtn.setActionCommand(NEXT);
        nextBtn.setText("");
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

package pgn.chessboard.gui;

import pgn.application.PgnGui;
import pgn.chessboard.players.ChessPlayer;
import pgn.parser.GameProgressException;
import pgn.tokenizer.ComboBoxGame;
import pgn.tokenizer.TokenizedGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 19.06.14
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
public class SimulationPlayer implements ActionListener {
    private static final int DELAY = 500;

    private PgnGui gui;
    private GameSimulation simulation;
    private Timer timer = new Timer(SimulationPlayer.DELAY, this);

    public SimulationPlayer(PgnGui gui, GameSimulation simulation) {
        this.gui = gui;
        this.simulation = simulation;
        timer.setInitialDelay((int)(0.3*DELAY));
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        try {
            this.simulation.drawNextBoard();
        } catch (GameProgressException ex) {
            TokenizedGame game = simulation.getGame();
            if(ex.isCheckMate()) {
                String player = ((ex.getPlayer()== ChessPlayer.WHITE) ? game.getWhite() : game.getBlack());
                JOptionPane.showMessageDialog(gui, "Szach mat!\nWygrał "+player, game.getWhite()+" vs. "+game.getBlack(), JOptionPane.INFORMATION_MESSAGE);
                timer.stop();
            }
            else if(ex.isEnd()) {
                String result = ((game.getResult().equals("1-0")) ? "Wygrał: "+game.getWhite() : ((game.getResult().equals("0-1")) ? "Wygrał: "+game.getBlack() : "Remis"));
                JOptionPane.showMessageDialog(gui, "Koniec gry!\n"+result, game.getWhite()+" vs. "+game.getBlack(), JOptionPane.INFORMATION_MESSAGE);
                timer.stop();
            }
        } catch (ParseException ex) {}
    }


}

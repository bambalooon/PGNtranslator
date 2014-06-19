package pgn.parser;

import pgn.application.PGNtranslator;
import pgn.application.PgnGui;
import pgn.tokenizer.ComboBoxGame;
import pgn.tokenizer.TokenizedGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 09.06.14
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */
public class ParserGui extends PgnGui {
    private static final String PARSE = "PARSE";
    private static final String GAME_CHOOSE = "GAME_CHOOSE";
    private static final String ALL_GAMES = "wszystkie";

    protected java.util.List<TokenizedGame> games;

    private JButton parseBtn;
    private JComboBox gameChooser;
    private JLabel result;

    public ParserGui(PGNtranslator application) {
        super(application);
        JPanel main = new JPanel();
        JLabel label = new JLabel();
        label.setText("Możesz teraz rozpocząć parsowanie...");
        parseBtn = new JButton();
        parseBtn.setEnabled(false);
        parseBtn.addActionListener(this);
        parseBtn.setActionCommand(ParserGui.PARSE);
        parseBtn.setText("Uruchom...");

        gameChooser = new JComboBox<>();
        gameChooser.setVisible(false);
        gameChooser.addActionListener(this);
        gameChooser.setActionCommand(ParserGui.GAME_CHOOSE);

        result = new JLabel();

        main.add(label);
        main.add(parseBtn);
        main.add(gameChooser);
        main.add(result);

        JToolBar toolBar = new JToolBar();
        toolBar.add(prevWindowBtn);
        toolBar.add(nextWindowBtn);
        nextWindowBtn.setVisible(false);

        add(main, BorderLayout.CENTER);
        add(toolBar, BorderLayout.PAGE_END);
        pack();
    }

    public void updateGameList(java.util.List<TokenizedGame> games) {
        if(games!=null && !games.isEmpty()) {
            this.games = games;
            parseBtn.setEnabled(true);
            gameChooser.setVisible(true);
            gameChooser.setModel(new DefaultComboBoxModel<ComboBoxGame>());
            gameChooser.addItem(new ComboBoxGame(null) {
                @Override
                public String toString() {
                    return ParserGui.ALL_GAMES;
                }
            });
            for(TokenizedGame game : games) {
                gameChooser.addItem(new ComboBoxGame(game));
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case PARSE:
                application.createParser();
                nextWindowBtn.setVisible(false); //only if parsing successful
                ComboBoxGame selected = (ComboBoxGame) gameChooser.getSelectedItem();
                try {
                    if(selected.toString().equals(ALL_GAMES)) {
                        application.parse(games);
                        application.setParsedGames(games);
                    }
                    else {
                        application.parse(selected.getGame());
                        application.setParsedGame(selected.getGame());
                    }
                    result.setText("Parsowanie zakończone powodzeniem!");
                    nextWindowBtn.setVisible(true);
                } catch (ParserException ex) {
                    JOptionPane.showMessageDialog(this, "Game: "+ex.getGame().getWhite()+" vs "+ex.getGame().getBlack()
                            +", move #"+ex.getMoveNum()+": "+ex.getMove()+" Error: "+ex.getMessage(), "Parser", JOptionPane.ERROR_MESSAGE);
                    nextWindowBtn.setVisible(false);
                    //jakis moze JTextArea do wyswietlania bledu(na czerwono..)
                    nextWindowBtn.setVisible(true);
                    application.setParsedGame(selected.getGame());
                }
                break;
            case GAME_CHOOSE:
                result.setText("");
                break;
        }
    }
}

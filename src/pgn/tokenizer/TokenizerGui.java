package pgn.tokenizer;

import pgn.application.PGNtranslator;
import pgn.application.PgnGui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 09.06.14
 * Time: 00:09
 * To change this template use File | Settings | File Templates.
 */
public class TokenizerGui extends PgnGui {
    public static final String TOKENIZE = "TOKENIZE";
    public static final String CHOOSE_GAME = "CHOOSE_GAME";

    protected java.util.List<TokenizedGame> games;
    private JLabel label;
    private JComboBox<ComboBoxGame> gameChooser;
    private JLabel tokenizedGameInfo;

    public TokenizerGui(PGNtranslator application) {
        super(application);
        JPanel main = new JPanel();
        label = new JLabel();
        label.setText("Możesz teraz rozpocząć konstrukcje tokenów...");
        JButton tokenizeBtn = new JButton();
        tokenizeBtn.addActionListener(this);
        tokenizeBtn.setActionCommand(TokenizerGui.TOKENIZE);
        tokenizeBtn.setText("Uruchom...");
        gameChooser = new JComboBox<>();
        gameChooser.setVisible(false);
        gameChooser.addActionListener(this);
        gameChooser.setActionCommand(TokenizerGui.CHOOSE_GAME);
        tokenizedGameInfo = new JLabel();
        tokenizedGameInfo.setVisible(false);
        main.add(label);
        main.add(tokenizeBtn);
        main.add(gameChooser);
        main.add(tokenizedGameInfo);

        JToolBar toolBar = new JToolBar();
        toolBar.add(prevWindowBtn);
        toolBar.add(nextWindowBtn);
        nextWindowBtn.setVisible(false);

        add(main, BorderLayout.CENTER);
        add(toolBar, BorderLayout.PAGE_END);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        switch (e.getActionCommand()) {
            case TOKENIZE:
                try {
                    games = application.createTokens();
//                    gameChooser.removeAllItems(); ????
                    for(TokenizedGame game : games) {
                        gameChooser.addItem(new ComboBoxGame(game));
                    }
                    gameChooser.setVisible(true);
                    tokenizedGameInfo.setVisible(true);
                    TokenizedGame selected = ((ComboBoxGame) gameChooser.getSelectedItem()).getGame();
                    tokenizedGameInfo.setText(selected.toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage(),
                        "Tokenizacja",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                break;
            case CHOOSE_GAME:
                JComboBox<ComboBoxGame> combo = (JComboBox<ComboBoxGame>) e.getSource();
                TokenizedGame selected = ((ComboBoxGame) combo.getSelectedItem()).getGame();
                tokenizedGameInfo.setText(selected.toString());
                break;
        }

    }
}

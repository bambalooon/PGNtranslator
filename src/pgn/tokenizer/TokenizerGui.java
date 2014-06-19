package pgn.tokenizer;

import pgn.application.PGNtranslator;
import pgn.application.PgnGui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;
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
    private static final int GAME_INFO_WIDTH = PgnGui.WIDTH-50;
    private static final int GAME_INFO_HEIGHT = PgnGui.HEIGHT-140;

    protected java.util.List<TokenizedGame> games;
    private JComboBox<ComboBoxGame> gameChooser;
    private JTextArea tokenizedGameInfo;
    private JScrollPane textScroll;
    
    public TokenizerGui(PGNtranslator application) {
        super(application);
        JPanel main = new JPanel();
        JLabel label = new JLabel();
        label.setText("Możesz teraz rozpocząć konstrukcje tokenów...");
        JButton tokenizeBtn = new JButton();
        tokenizeBtn.addActionListener(this);
        tokenizeBtn.setActionCommand(TokenizerGui.TOKENIZE);
        tokenizeBtn.setText("Uruchom...");
        gameChooser = new JComboBox<>();
        gameChooser.setVisible(false);
        gameChooser.addActionListener(this);
        gameChooser.setActionCommand(TokenizerGui.CHOOSE_GAME);
        tokenizedGameInfo = new JTextArea();
        tokenizedGameInfo.setEditable(false);
        tokenizedGameInfo.setLineWrap(true);
        tokenizedGameInfo.setWrapStyleWord(true);
        textScroll = new JScrollPane(tokenizedGameInfo);
        textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll.setPreferredSize(new Dimension(GAME_INFO_WIDTH, GAME_INFO_HEIGHT));
        textScroll.setVisible(false);
        /*
          tokenizerErrors = new JTextArea();
                    tokenizerErrors.setEditable(false);
                    tokenizerErrors.setLineWrap(true);
                    tokenizerErrors.setWrapStyleWord(true);
   */
           /*                 
         textScroll2 = new JScrollPane(tokenizerErrors);
        textScroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll2.setPreferredSize(new Dimension(GAME_INFO_WIDTH, GAME_INFO_HEIGHT));
        textScroll.setVisible(false);
             */             
        
        main.add(label);
        main.add(tokenizeBtn);
        main.add(gameChooser);
        main.add(textScroll);
        //main.add(textScroll2);
        //main.add(tokenizerErrors);
        

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
                    gameChooser.setModel(new DefaultComboBoxModel<ComboBoxGame>());
                    for(TokenizedGame game : games) {
                        gameChooser.addItem(new ComboBoxGame(game));
                    }
                    gameChooser.setVisible(true);
                    textScroll.setVisible(true);
                    TokenizedGame selected = ((ComboBoxGame) gameChooser.getSelectedItem()).getGame();
                    tokenizedGameInfo.setText(selected.toString());
                    nextWindowBtn.setVisible(true);
                } catch (Exception ex) {
                    gameChooser.setVisible(true);
                    textScroll.setVisible(true);
                    gameChooser.setVisible(false);
                    tokenizedGameInfo.setText(ex.getMessage());
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

package pgn.parser;

import pgn.application.PGNtranslator;
import pgn.application.PgnGui;
import pgn.tokenizer.ComboBoxGame;
import pgn.tokenizer.TokenizedGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final int GAME_INFO_WIDTH = PgnGui.WIDTH-50;
    private static final int GAME_INFO_HEIGHT = PgnGui.HEIGHT-140;
    private static final int RESULT_HEIGHT = 100;

    protected java.util.List<TokenizedGame> games;

    private JButton parseBtn;
    private JComboBox gameChooser;
    private JLabel result;
    private JScrollPane textScroll;
    private JTextPane parsedGameInfo;

    public ParserGui(PGNtranslator application) {
        super(application);
        this.setPreferredSize(null);
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

        parsedGameInfo = new JTextPane();
        parsedGameInfo.setEditable(false);
        parsedGameInfo.setContentType("text/html");

        textScroll = new JScrollPane(parsedGameInfo);
        textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll.setPreferredSize(new Dimension(GAME_INFO_WIDTH, GAME_INFO_HEIGHT));
        textScroll.setVisible(false);

        main.add(label);
        main.add(parseBtn);
        main.add(gameChooser);
        main.add(result);
        main.add(textScroll);

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
                    textScroll.setVisible(false);
                    nextWindowBtn.setVisible(true);
                } catch (ParserException ex) {
                    result.setText(" ");
                    textScroll.setVisible(true);
                    String game = ex.getGame().toHtmlString();
                    Pattern p = Pattern.compile("("+ex.getMoveNum()+"\\.\\ "+ex.getMove()+" : [a-zA-Z0-9\\+\\=\\#]+|[a-zA-Z0-9\\+\\=\\#]+ : "+ex.getMove()+")<br>");
                    Matcher m = p.matcher(game);
                    if(m.find()) {
                        game = game.substring(0, m.start())+"<span style='color: red'>"+game.substring(m.start(), m.end()-1)+"</span>"+game.substring(m.end());
                    }
                    game = "<html>Gra: "+ex.getGame().getWhite()+" vs "+ex.getGame().getBlack()+
                            "<br>Ruch #"+ex.getMoveNum()+": "+ex.getMove()+"<br>Typ błędu:<p style='color: red'>"+ex.getMessage()+"</p>"
                            +game.substring(6);

                    parsedGameInfo.setText(game);
                    nextWindowBtn.setVisible(false);
                }
                break;
            case GAME_CHOOSE:
                result.setText("");
                break;
        }
    }

}

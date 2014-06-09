package pgn.application;

import pgn.chessboard.gui.MainWindow;
import pgn.parser.Parser;
import pgn.parser.ParserGui;
import pgn.tokenizer.TokenizedGame;
import pgn.tokenizer.Tokenizer;
import pgn.tokenizer.TokenizerGui;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 08.06.14
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public enum PGNtranslator {
    INSTANCE;
    public static final String windowName = "Chess PGN translator";
    private List<PgnGui> windows;
    private Tokenizer tokenizer;
    private Parser parser;
    private File pgnFile;
    private List<TokenizedGame> games;

    private ParserGui parserGui;



    public void startGui() {
        windows = new ArrayList<>();


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartGui startGui = new StartGui(PGNtranslator.INSTANCE);
                startGui.setVisible(true);
                TokenizerGui tokenizerGui = new TokenizerGui(PGNtranslator.INSTANCE);
                tokenizerGui.setVisible(false);
                parserGui = new ParserGui(PGNtranslator.INSTANCE);
                parserGui.setVisible(false);
                windows.add(startGui);
                windows.add(tokenizerGui);
                windows.add(parserGui);

//                final MainWindow wnd = new MainWindow("Chess PGN translator");
//                wnd.setVisible(true);
//                try {
//                    wnd.drawBoard();
//                } catch (IOException e) {
//
//                }
            }
        });
    }

    public void nextWindow(PgnGui gui) {
        int index = windows.indexOf(gui);
        if(index!=-1 && index!=(windows.size()-1)) {
            gui.setVisible(false);
            PgnGui newGui = windows.get(index+1);
            newGui.setVisible(true);
        }
    }

    public void prevWindow(PgnGui gui) {
        int index = windows.indexOf(gui);
        if(index!=-1 && index!=0) {
            gui.setVisible(false);
            PgnGui newGui = windows.get(index-1);
            newGui.setVisible(true);
        }
    }

    public File getPgnFile() {
        return pgnFile;
    }

    public void setPgnFile(File pgnFile) {
        this.pgnFile = pgnFile;
    }

    public void createTokenizer() {
        if(pgnFile!=null) {
            tokenizer = new Tokenizer(pgnFile);
        }
    }

    public List<TokenizedGame> createTokens() throws Exception {
        if(tokenizer!=null) {
            games = tokenizer.tokenizeGames();
            parserGui.updateGameList(games);
            return games;
        }
        throw new Exception("Tokenizer not initialized!");
    }

    public void createParser() {
        parser = new Parser();
    }

    public void parse(TokenizedGame game) throws ParseException {
        parser.parse(game);
    }

    public void parse(List<TokenizedGame> games) throws ParseException {
        parser.parse(games);
    }

    public static void main(String... args) {
        PGNtranslator.INSTANCE.startGui();

    }
}

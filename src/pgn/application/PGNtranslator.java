package pgn.application;

import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.gui.ChessBoardPanel;
import pgn.chessboard.gui.ChessboardGui;
import pgn.chessboard.gui.GameSimulation;
import pgn.chessboard.players.ChessPlayer;
import pgn.parser.Parser;
import pgn.parser.ParserException;
import pgn.parser.ParserGui;
import pgn.tokenizer.TokenizedGame;
import pgn.tokenizer.Tokenizer;
import pgn.tokenizer.TokenizerGui;

import javax.swing.*;
import java.io.File;
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

    private List<TokenizedGame> parsedGames;
    private TokenizedGame parsedGame;

    private ParserGui parserGui;
    private ChessboardGui boardGui;

    private GameSimulation simulation;



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
                boardGui = new ChessboardGui(PGNtranslator.INSTANCE);
                boardGui.setVisible(false);
                windows.add(startGui);
                windows.add(tokenizerGui);
                windows.add(parserGui);
                windows.add(boardGui);

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

    public void parse(TokenizedGame game) throws ParserException {
        parser.parse(game);
    }

    public void parse(List<TokenizedGame> games) throws ParserException {
        parser.parse(games);
    }

    public List<TokenizedGame> getParsedGames() {
        return parsedGames;
    }

    public void setParsedGames(List<TokenizedGame> parsedGames) {
        this.parsedGame = null;
        this.simulation = new GameSimulation(boardGui.getBoardPanel(), parsedGames.get(0), parser);
        this.parsedGames = parsedGames;
        this.boardGui.updateGameList();
    }

    public TokenizedGame getParsedGame() {
        return parsedGame;
    }

    public void setParsedGame(TokenizedGame parsedGame) {
        this.parsedGames = null;
        this.simulation = new GameSimulation(boardGui.getBoardPanel(), parsedGame, parser);
        this.parsedGame = parsedGame;
        this.boardGui.updateGameList();
    }

    public static void main(String... args) {
        PGNtranslator.INSTANCE.startGui();

    }

    public GameSimulation getSimulation() {
        return simulation;
    }

    public void createGameSimulation(ChessBoardPanel panel, TokenizedGame game) {
        if(parser==null) createParser();
        this.simulation = new GameSimulation(panel, game, parser);
    }

    public ChessboardGui getBoardGui() {
        return boardGui;
    }
}

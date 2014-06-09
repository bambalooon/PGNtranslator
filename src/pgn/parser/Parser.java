package pgn.parser;

import pgn.chessboard.board.ChessBoard;
import pgn.tokenizer.TokenizedGame;


import javax.swing.*;
import java.text.ParseException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 09.06.14
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    public void parse(TokenizedGame game) throws ParseException {
        ChessBoard chessBoard = new ChessBoard();
        for(TokenizedGame.MovePair movePair : game.getMoves()) {

        }
        JOptionPane.showMessageDialog(
                new JFrame(),
                game.getMoves().get(0).getWhite(),
                "Parser",
                JOptionPane.INFORMATION_MESSAGE
        );

    }

    public void parse(List<TokenizedGame> games) throws ParseException {

    }
}

package pgn.tokenizer;

import pgn.tokenizer.TokenizedGame;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 09.06.14
 * Time: 00:45
 * To change this template use File | Settings | File Templates.
 */
public class ComboBoxGame {

    private TokenizedGame game;
    public ComboBoxGame(TokenizedGame game) {
        this.game = game;
    }

    public TokenizedGame getGame() {
        return game;
    }

    @Override
    public String toString() {
        return game.getWhite()+" vs "+game.getBlack();
    }

}

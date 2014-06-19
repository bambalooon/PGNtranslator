package pgn.parser;

import pgn.tokenizer.TokenizedGame;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 19.06.14
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
public class ParserException extends Exception {
    private TokenizedGame game;
    private int moveNum;
    private String move;
    private String message;

    public ParserException(TokenizedGame game, int moveNum, String move, String message) {
        this.game = game;
        this.moveNum = moveNum;
        this.message = message;
        this.move = move;
    }

    public TokenizedGame getGame() {
        return game;
    }

    public void setGame(TokenizedGame game) {
        this.game = game;
    }

    public int getMoveNum() {
        return moveNum;
    }

    public void setMoveNum(int moveNum) {
        this.moveNum = moveNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}

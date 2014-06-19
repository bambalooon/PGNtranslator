package pgn.parser;

import pgn.chessboard.players.ChessPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 19.06.14
 * Time: 20:59
 * To change this template use File | Settings | File Templates.
 */
public class GameProgressException extends Exception {
    private boolean check = false;
    private boolean checkMate = false;
    private ChessPlayer player;

    public GameProgressException(ChessPlayer player) {
        this.player = player;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
    }

    public ChessPlayer getPlayer() {
        return player;
    }
}

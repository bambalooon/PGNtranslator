package pgn.chessboard.figures;

import pgn.chessboard.board.Board;
import pgn.chessboard.board.ChessMove;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class Bishop extends Figure {

    public void makeMove(Board.Position position) throws IllegalArgumentException {
        return;
    }

    public boolean isMovePossible(ChessMove move) {
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Bishop) {
            return super.equals(object);
        }
        return false;
    }
}

package pgn.chessboard.figures;

import pgn.chessboard.board.Board;
import pgn.chessboard.board.ChessMove;
import pgn.chessboard.players.ChessPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class King extends Figure {

    public King(Board board, ChessPlayer owner, Board.Position position) {
        super(board, owner, position);
    }

    public boolean isMovePossible(ChessMove move) {
        int xAbsDist = Math.abs(move.getTargetPosition().getX().getValue()-this.position.getX().getValue());
        int yAbsDist = Math.abs(move.getTargetPosition().getY().getValue()-this.position.getY().getValue());
        if(xAbsDist==0 && yAbsDist==0) {
            return false;
        }
        if(xAbsDist<=1 && yAbsDist<=1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof King) {
            return super.equals(object);
        }
        return false;
    }
}

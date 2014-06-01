package pgn.chessboard.figures;

import pgn.chessboard.board.Board;
import pgn.chessboard.board.ChessMove;
import pgn.chessboard.players.ChessPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class Knight extends Figure {

    public Knight(Board board, ChessPlayer owner, Board.Position position) {
        super(board, owner, position);
    }

    public boolean isMovePossible(ChessMove move) {
        int xAbsDist = Math.abs(move.getTargetPosition().getX().getValue()-this.position.getX().getValue());
        int yAbsDist = Math.abs(move.getTargetPosition().getY().getValue()-this.position.getY().getValue());
        if((xAbsDist==1 && yAbsDist==2) || (xAbsDist==2 && yAbsDist==1)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Bishop) {
            return super.equals(object);
        }
        return false;
    }
}

package pgn.chessboard.figures;

import pgn.chessboard.board.Board;
import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.board.ChessMove;
import pgn.chessboard.players.ChessPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class Rook extends Figure {

    public Rook(Board board, ChessPlayer owner, Board.Position position) {
        super(board, owner, position);
    }

    public boolean isMovePossible(ChessMove move) {
        int xdist = move.getTargetPosition().getX().getValue()-this.position.getX().getValue();
        int ydist = move.getTargetPosition().getY().getValue()-this.position.getY().getValue();
        if((xdist==0 && ydist==0) || (xdist!=0 && ydist!=0)) {
            return false;
        }
        int xMove = (xdist!=0) ? ((xdist>0) ? 1 : -1) : 0;
        int yMove = (ydist!=0) ? ((ydist>0) ? 1 : -1) : 0;
        int destX = move.getTargetPosition().getX().getValue();
        int destY = move.getTargetPosition().getY().getValue();
        for(int x = this.position.getX().getValue(), y = this.position.getY().getValue() ;
            x!=destX && y!=destY ;x+=xMove, y+=yMove) {

            Figure figure = board.checkPosition(new ChessBoard.ChessPosition(x, y));
            if(figure!=null && (x!=destX || y!=destY)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Rook) {
            return super.equals(object);
        }
        return false;
    }
}

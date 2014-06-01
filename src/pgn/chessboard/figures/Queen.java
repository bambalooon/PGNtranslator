package pgn.chessboard.figures;

import pgn.chessboard.board.Board;
import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.board.ChessMove;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class Queen extends Figure {

    public boolean isMovePossible(ChessMove move) {
        double xdist = move.getTargetPosition().getX().getValue()-this.position.getX().getValue();
        double ydist = move.getTargetPosition().getY().getValue()-this.position.getY().getValue();
        if(xdist==0 && ydist==0) {
            return false;
        }
        int xMove;
        int yMove;
        if(xdist==0 || ydist==0) { //like rook
            xMove = (xdist!=0) ? ((xdist>0) ? 1 : -1) : 0;
            yMove = (ydist!=0) ? ((ydist>0) ? 1 : -1) : 0;
        }
        else { //like bishop
            double div = xdist/ydist; //don't have to check because - it would be rook!
            if(div!=1 && div!=-1) {
                return false;
            }
            xMove = (xdist>0) ? 1 : -1;
            yMove = (ydist>0) ? 1 : -1;
        }
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
        if(object instanceof Queen) {
            return super.equals(object);
        }
        return false;
    }
}

package pgn.chessboard.figures;

import pgn.chessboard.board.Board;
import pgn.chessboard.board.ChessMove;
import pgn.chessboard.players.ChessPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class Pawn extends Figure {

    public Pawn(Board board, ChessPlayer owner, Board.Position position) {
        super(board, owner, position);
    }

    public boolean isMovePossible(ChessMove move) {
        int xdist = move.getTargetPosition().getX().getValue()-this.position.getX().getValue();
        int ydist = move.getTargetPosition().getY().getValue()-this.position.getY().getValue();
        if(move.getType()==ChessMove.MoveType.NORMAL && this.position.getX()==move.getTargetPosition().getX()) { //sa w tej samej kolumnie i to normalny ruch
            switch(move.getPlayer()) {
                case WHITE:
                    switch (ydist) {
                        case 1: return true;
                        case 2:
                            if(this.lastPosition==null) {  //leap through 2 only possible at first move!
                                return true;
                            }
                    }
                    return false;
                case BLACK:
                    switch (ydist) {
                        case -1: return true;
                        case -2:
                            if(this.lastPosition==null) {
                                return true;
                            }
                    }
                    return false;
            }
        }
        else if(move.getType()== ChessMove.MoveType.CAPTURE) {
            if(xdist==1 || xdist==-1) {
                switch (move.getPlayer()) {
                    case WHITE:
                        if(ydist==1) {
                            return true;
                        }
                        return false;
                    case BLACK:
                        if(ydist==-1) {
                            return true;
                        }
                        return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Pawn) {
            return super.equals(object);
        }
        return false;
    }
}

package pgn.chessboard.figures;

import pgn.chessboard.board.*;
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

    @Override
    public Figure makeMove(ChessMove move) {
        if(move.getTargetPosition().getY()==null) {
            int y = this.position.getY().getValue();
            if(this.owner==ChessPlayer.WHITE) {
                y++;
            }
            else {
                y--;
            }
            if(move.getTargetPosition() instanceof ChessBoard.ChessPosition) {
                ChessBoard.ChessPosition pos = (ChessBoard.ChessPosition) move.getTargetPosition();
                PositionFixer.fixPosition(pos, pos.getX().getValue(), y);
            }
        }
        super.makeMove(move);
        //Promotion!
        if(move.isPromoted()) {
            PawnPromotion promotion = move.getPromotion();
            Figure givenFigure = promotion.getPromotion();
            givenFigure.setPosition(this.position);
            givenFigure.setLastPosition(this.lastPosition);
            return givenFigure;
        }
        return null;
    }

    public boolean isMovePossible(ChessMove move) {
        if(move.getType()==ChessMove.MoveType.NORMAL && this.position.getX()==move.getTargetPosition().getX()) { //sa w tej samej kolumnie i to normalny ruch
            int ydist = move.getTargetPosition().getY().getValue()-this.position.getY().getValue();
            if(board.checkPosition(move.getTargetPosition())!=null) {
                return false;
            }
            switch(move.getPlayer()) {
                case WHITE:
                    switch (ydist) {
                        case 1: return true;
                        case 2:
                            if(this.lastPosition==null
                                    && board.checkPosition(new ChessBoard.ChessPosition(position.getX().getValue(), position.getY().getValue()+1))==null) {  //leap through 2 only possible at first move!
                                return true;
                            }
                    }
                    return false;
                case BLACK:
                    switch (ydist) {
                        case -1: return true;
                        case -2:
                            if(this.lastPosition==null
                                    && board.checkPosition(new ChessBoard.ChessPosition(position.getX().getValue(), position.getY().getValue()-1))==null) {
                                return true;
                            }
                    }
                    return false;
            }
        }
        else if(move.getType()== ChessMove.MoveType.CAPTURE) {   //OR EN PASSANTE!
            int xdist = move.getTargetPosition().getX().getValue()-this.position.getX().getValue();
            if(xdist==1 || xdist==-1) {
                if(move.getTargetPosition().getY()!=null) {
                    int ydist = move.getTargetPosition().getY().getValue()-this.position.getY().getValue();
                    switch (move.getPlayer()) {
                        case WHITE:
                            if(ydist!=1) {
                                return false;
                            }
                            break;
                        case BLACK:
                            if(ydist!=-1) {
                                return false;
                            }
                            break;
                    }
                }
                Figure fig = board.checkPosition(move.getTargetPosition());
                if(fig==null) { //el passante! - check if last move was 2-jump
                    Figure jumper = board.checkPosition(
                        new ChessBoard.ChessPosition(
                            move.getTargetPosition().getX(), this.position.getY()
                        )
                    );
                    if(jumper!=null && jumper instanceof Pawn) { //there has to be pawn!!!
                        int distX = jumper.getPosition().getX().getValue()-jumper.getLastPosition().getX().getValue();
                        int distY = jumper.getPosition().getY().getValue()-jumper.getLastPosition().getY().getValue();
                        if(distX==0 && ((jumper.getOwner()==ChessPlayer.WHITE && distY==2) || (jumper.getOwner()==ChessPlayer.BLACK && distY==-2))) {
                            //good el passante move
                            return true;
                        }
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

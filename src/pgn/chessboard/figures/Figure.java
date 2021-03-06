package pgn.chessboard.figures;

import pgn.chessboard.board.Board;
import pgn.chessboard.board.ChessMove;
import pgn.chessboard.players.ChessPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class Figure {
    protected Board board;
    protected ChessPlayer owner;
    protected Board.Position position;
    protected Board.Position lastPosition = null; //for pawns, castling, etc.

    public Figure(Board board, ChessPlayer owner, Board.Position position) {
        this.board = board;
        this.owner = owner;
        this.position = position;
    }

    public abstract boolean isMovePossible(ChessMove move); //to find moving figure

    public Figure makeMove(ChessMove move) {  //returned Figure is promotion!
        lastPosition = this.position;
        position = move.getTargetPosition();
        return null;
    }

    public ChessPlayer getOwner() {
        return owner;
    }

    public Board.Position getPosition() {
        return position;
    }

    public Board.Position getLastPosition() {
        return lastPosition;
    }

    @Override
    public boolean equals(Object object) {
        if(object!=null && object instanceof Figure) {
            Figure figure = (Figure) object;
            if(figure.getOwner()==null || figure.getOwner()!=this.getOwner()) { //if doesn't have owner OR different - false
                return false;
            }
            Board.Column col = this.getPosition().getX();
            Board.Row row = this.getPosition().getY();
            if(col!=null && col!=figure.getPosition().getX()) { //check if same column OR undefined
                return false;
            }
            if(row!=null && row!=figure.getPosition().getY()) { //check if same row OR undefined
                return false;
            }
            return true;
        }
        return false;
    }

    public void setPosition(Board.Position position) {
        this.position = position;
    }

    public void setLastPosition(Board.Position lastPosition) {
        this.lastPosition = lastPosition;
    }
}

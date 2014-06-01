package pgn.chessboard.figures;

import pgn.chessboard.board.Board;
import pgn.chessboard.players.ChessPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class Figure {
    protected ChessPlayer owner;
    protected Board.Position position;


    public ChessPlayer getOwner() {
        return owner;
    }

    public Board.Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Figure) {
            Figure figure = (Figure) object;
            if(figure.getOwner()==null || figure.getOwner()!=this.getOwner()) { //if doesn't have owner OR different - false
                return false;
            }
            Board.Column col = figure.getPosition().getX();
            Board.Row row = figure.getPosition().getY();
            if(col!=null && col!=this.getPosition().getX()) { //check if same column OR undefined
                return false;
            }
            if(row!=null && row!=this.getPosition().getY()) { //check if same row OR undefined
                return false;
            }
            return true;
        }
        return false;
    }
}

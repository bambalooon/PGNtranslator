package pgn.chessboard.figures;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class Pawn extends Figure {

    @Override
    public boolean equals(Object object) {
        if(object instanceof Pawn) {
            return super.equals(object);
        }
        return false;
    }
}

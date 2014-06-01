package pgn.chessboard.figures;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class Rook extends Figure {

    @Override
    public boolean equals(Object object) {
        if(object instanceof Rook) {
            return super.equals(object);
        }
        return false;
    }
}

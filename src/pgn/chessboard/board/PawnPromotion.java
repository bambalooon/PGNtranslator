package pgn.chessboard.board;

import pgn.chessboard.figures.*;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class PawnPromotion {
    private Figure promotion;

    //Figure has to be given board and owner!
    public PawnPromotion(Figure promotion) throws IllegalArgumentException {
        if(promotion instanceof Bishop || promotion instanceof Knight
                || promotion instanceof Queen || promotion instanceof Rook) {
            this.promotion = promotion;
            return;
        }
        throw new IllegalArgumentException("Pawn cannot be promoted to "+promotion.getClass().getName());
    }

    public Figure getPromotion() {
        return promotion;
    }
}

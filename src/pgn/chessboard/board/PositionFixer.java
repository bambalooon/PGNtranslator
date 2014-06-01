package pgn.chessboard.board;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 02.06.14
 * Time: 00:30
 * To change this template use File | Settings | File Templates.
 */
public class PositionFixer {
    public static void fixPosition(ChessBoard.ChessPosition position, int x, int y) {
        position.fixPosition(x, y);
    }
}

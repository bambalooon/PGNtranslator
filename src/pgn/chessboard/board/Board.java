package pgn.chessboard.board;

import pgn.chessboard.figures.Figure;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public interface Board {
    public static interface Column {
        public int getValue();
    }
    public static interface Row {
        public int getValue();
    }
    public static interface Position {
        public Column getX();
        public Row getY();
    }
    Object[][] getBoardCopy();
    Figure checkPosition(Position position);
    void makeMove(Figure figure, ChessMove move) throws IllegalArgumentException; //only user method!!!

}

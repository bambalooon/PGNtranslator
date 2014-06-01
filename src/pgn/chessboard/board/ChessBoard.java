package pgn.chessboard.board;

import pgn.chessboard.figures.Bishop;
import pgn.chessboard.figures.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class ChessBoard implements Board {
    public enum Row implements Board.Row {
        _1(0), _2(1), _3(2), _4(3), _5(4), _6(5), _7(6), _8(7);
        private int value;
        private Row(int nr) {
            value = nr;
        }

        public int getValue() {
            return value;
        }
    };
    public enum Column implements Board.Column {
        a(0), b(1), c(2), d(3), e(4), f(5), g(6), h(7);
        private int value;
        private Column(int nr) {
            value = nr;
        }

        public int getValue() {
            return value;
        }
    };
    public static class ChessPosition implements Position {
        Column x;
        Row y;

        public ChessPosition(int x, int y) {
            this.x = Column.values()[x];
            this.y = Row.values()[y];
        }

        public ChessPosition(Column x, Row y) {
            this.x = x;
            this.y = y;
        }

        public Column getX() {
            return x;
        }

        public Row getY() {
            return y;
        }
    }

    private static final int WIDTH = Column.values().length;
    private static final int HEIGHT = Row.values().length;
    protected Figure[][] board = new Figure[HEIGHT][WIDTH];

    @Override
    public Figure checkPosition(Board.Position position) {
        int x = position.getX().getValue();
        int y = position.getY().getValue();
        return board[y][x];
    }

    @Override
    public Figure findFigure(Figure figure) throws IllegalArgumentException {
        int matched_count = 0;
        Figure matched = null;
        for(Figure[] cols : board) {
            for (Figure fig : cols) {
                if(fig.equals(figure)) {
                    matched = fig;
                    matched_count++;
                }
            }
        }
        if(matched==null) {
            throw new IllegalArgumentException("No figure matched!");
        }
        else if(matched_count>1) {
            throw new IllegalArgumentException("Condition not explicit! Too many matched figures!");
        }
        return matched;
    }
}

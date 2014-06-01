package pgn.chessboard.board;

import pgn.chessboard.figures.Bishop;
import pgn.chessboard.figures.Figure;
import pgn.chessboard.players.ChessPlayer;

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
    public enum Rank implements Board.Row {
        _1(0), _2(1), _3(2), _4(3), _5(4), _6(5), _7(6), _8(7);
        private int value;
        private Rank(int nr) {
            value = nr;
        }

        public int getValue() {
            return value;
        }
    };
    public enum File implements Board.Column {
        a(0), b(1), c(2), d(3), e(4), f(5), g(6), h(7);
        private int value;
        private File(int nr) {
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
            this.x = File.values()[x];
            this.y = Rank.values()[y];
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

    private static final int WIDTH = File.values().length;
    private static final int HEIGHT = Rank.values().length;
    protected Figure[][] board = new Figure[HEIGHT][WIDTH];

    public ChessBoard() {
        initBoard();
    }

    @Override
    public Figure checkPosition(Board.Position position) {
        int x = position.getX().getValue();
        int y = position.getY().getValue();
        return board[y][x];
    }

    @Override
    public void makeMove(Figure figure, ChessMove move) throws IllegalArgumentException {
        figure = findFigure(figure, move);
        if(move.getType()== ChessMove.MoveType.CAPTURE) { //check if it's correct capture
            Figure captured = checkPosition(move.getTargetPosition());
            if(captured==null || captured.getOwner()==figure.getOwner()) {
                throw new IllegalArgumentException("Capturing your own piece or nothing!");
            }
        }
        figure.makeMove(move);
        //move pieces on board!!!
    }

    protected Figure findFigure(Figure figure, ChessMove move) throws IllegalArgumentException {
        boolean matched = false;
        Figure matchedFigure = null;
        for(Figure[] cols : board) {
            for (Figure fig : cols) {
                if(fig.equals(figure) && fig.isMovePossible(move)) {
                    if(matched) {
                        throw new IllegalArgumentException("Condition not explicit! Too many matched figures!");
                    }
                    matchedFigure = fig;
                    matched = true;
                }
            }
        }
        if(matchedFigure==null) {
            throw new IllegalArgumentException("No figure matched!");
        }
        return matchedFigure;
    }

    protected void initBoard() {

    }

}

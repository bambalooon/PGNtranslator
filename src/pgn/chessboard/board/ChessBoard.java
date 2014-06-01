package pgn.chessboard.board;

import pgn.chessboard.figures.*;
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
        Position pos = figure.getPosition();
        Position targetPos = move.getTargetPosition();
        board[pos.getY().getValue()][pos.getX().getValue()] = null;
        figure.makeMove(move);
        board[targetPos.getY().getValue()][targetPos.getX().getValue()] = figure;
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
        //WHITE
        //Rooks
        Rook rook = new Rook(this, ChessPlayer.WHITE, new ChessPosition(File.a, Rank._1));
        board[Rank._1.getValue()][File.a.getValue()] = rook;
        rook = new Rook(this, ChessPlayer.WHITE, new ChessPosition(File.h, Rank._1));
        board[Rank._1.getValue()][File.h.getValue()] = rook;
        //Knights
        Knight knight = new Knight(this, ChessPlayer.WHITE, new ChessPosition(File.b, Rank._1));
        board[Rank._1.getValue()][File.b.getValue()] = knight;
        knight = new Knight(this, ChessPlayer.WHITE, new ChessPosition(File.g, Rank._1));
        board[Rank._1.getValue()][File.g.getValue()] = knight;
        //Bishops
        Bishop bishop = new Bishop(this, ChessPlayer.WHITE, new ChessPosition(File.c, Rank._1));
        board[Rank._1.getValue()][File.c.getValue()] = bishop;
        bishop = new Bishop(this, ChessPlayer.WHITE, new ChessPosition(File.f, Rank._1));
        board[Rank._1.getValue()][File.f.getValue()] = bishop;
        //Queen
        Queen queen = new Queen(this, ChessPlayer.WHITE, new ChessPosition(File.d, Rank._1));
        board[Rank._1.getValue()][File.d.getValue()] = queen;
        //King
        King king = new King(this, ChessPlayer.WHITE, new ChessPosition(File.e, Rank._1));
        board[Rank._1.getValue()][File.e.getValue()] = king;
        //Pawns
        for(File file : File.values()) {
            Pawn pawn = new Pawn(this, ChessPlayer.WHITE, new ChessPosition(file, Rank._2));
            board[Rank._2.getValue()][file.getValue()] = pawn;
        }

        //BLACK
        //Rooks
        rook = new Rook(this, ChessPlayer.BLACK, new ChessPosition(File.a, Rank._8));
        board[Rank._8.getValue()][File.a.getValue()] = rook;
        rook = new Rook(this, ChessPlayer.BLACK, new ChessPosition(File.h, Rank._8));
        board[Rank._8.getValue()][File.h.getValue()] = rook;
        //Knights
        knight = new Knight(this, ChessPlayer.BLACK, new ChessPosition(File.b, Rank._8));
        board[Rank._8.getValue()][File.b.getValue()] = knight;
        knight = new Knight(this, ChessPlayer.BLACK, new ChessPosition(File.g, Rank._8));
        board[Rank._8.getValue()][File.g.getValue()] = knight;
        //Bishops
        bishop = new Bishop(this, ChessPlayer.BLACK, new ChessPosition(File.c, Rank._8));
        board[Rank._8.getValue()][File.c.getValue()] = bishop;
        bishop = new Bishop(this, ChessPlayer.BLACK, new ChessPosition(File.f, Rank._8));
        board[Rank._8.getValue()][File.f.getValue()] = bishop;
        //Queen
        queen = new Queen(this, ChessPlayer.BLACK, new ChessPosition(File.d, Rank._8));
        board[Rank._8.getValue()][File.d.getValue()] = queen;
        //King
        king = new King(this, ChessPlayer.BLACK, new ChessPosition(File.e, Rank._8));
        board[Rank._8.getValue()][File.e.getValue()] = king;
        //Pawns
        for(File file : File.values()) {
            Pawn pawn = new Pawn(this, ChessPlayer.BLACK, new ChessPosition(file, Rank._7));
            board[Rank._7.getValue()][file.getValue()] = pawn;
        }

    }

}

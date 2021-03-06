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

        protected void fixPosition(int x, int y) { //special method for pawn attack
            this.x = File.values()[x];
            this.y = Rank.values()[y];
        }
    }

    private static final int WIDTH = File.values().length;
    private static final int HEIGHT = Rank.values().length;
    protected Figure[][] board = new Figure[HEIGHT][WIDTH];

    public ChessBoard() {
        initBoard();
    }

    @Override
    public Figure[][] getBoardCopy() {
        Figure[][] clone = new Figure[board.length][];
        for(int i=0; i<clone.length; i++) {
            clone[i] = board[i].clone();
        }
        return clone;
    }

    @Override
    public Figure checkPosition(Board.Position position) {
        int x = position.getX().getValue();
        int y = position.getY().getValue();
        return board[y][x];
    }

    @Override
    public void makeMove(Figure figure, ChessMove move) throws IllegalArgumentException {  //for castling figure & position should be null
        if(move.isPromoted() && !(figure instanceof Pawn)) {
            throw new IllegalArgumentException("Tylko pion może być promowany!");
        }
        if(move.getType() == ChessMove.MoveType.KINGSIDECASTLING) {
            Figure king;
            Figure rook;
            switch (move.getPlayer()) {
                case WHITE:
                    king = board[Rank._1.getValue()][File.e.getValue()];
                    Figure f1 = board[Rank._1.getValue()][File.f.getValue()];
                    Figure g1 = board[Rank._1.getValue()][File.g.getValue()];
                    rook = board[Rank._1.getValue()][File.h.getValue()];
                    if(king!=null && king.getLastPosition()==null && rook!=null && rook.getLastPosition()==null && f1==null && g1==null) { //if didn't move then they are king & rook
                        board[Rank._1.getValue()][File.h.getValue()] = null;
                        board[Rank._1.getValue()][File.e.getValue()] = null;
                        board[Rank._1.getValue()][File.g.getValue()] = king;
                        king.makeMove(new ChessMove(null, new ChessPosition(File.g, Rank._1), null));
                        board[Rank._1.getValue()][File.f.getValue()] = rook;
                        rook.makeMove(new ChessMove(null, new ChessPosition(File.f, Rank._1), null));
                    }
                    else {
                        throw new IllegalArgumentException("Roszada niedozwolona!");
                    }
                    return;
                case BLACK:
                    king = board[Rank._8.getValue()][File.e.getValue()];
                    Figure f8 = board[Rank._8.getValue()][File.f.getValue()];
                    Figure g8 = board[Rank._8.getValue()][File.g.getValue()];
                    rook = board[Rank._8.getValue()][File.h.getValue()];
                    if(king!=null && king.getLastPosition()==null && rook!=null && rook.getLastPosition()==null && f8==null && g8==null) { //if didn't move then they are king & rook
                        board[Rank._8.getValue()][File.h.getValue()] = null;
                        board[Rank._8.getValue()][File.e.getValue()] = null;
                        board[Rank._8.getValue()][File.g.getValue()] = king;
                        king.makeMove(new ChessMove(null, new ChessPosition(File.g, Rank._8), null));
                        board[Rank._8.getValue()][File.f.getValue()] = rook;
                        rook.makeMove(new ChessMove(null, new ChessPosition(File.f, Rank._8), null));
                    }
                    else {
                        throw new IllegalArgumentException("Roszada niedozwolona!");
                    }
                    return;
            }
        }
        else if(move.getType() == ChessMove.MoveType.QUEENSIDECASTLING) {
            Figure king;
            Figure rook;
            switch (move.getPlayer()) {
                case WHITE:
                    king = board[Rank._1.getValue()][File.e.getValue()];
                    Figure d1 = board[Rank._1.getValue()][File.d.getValue()];
                    Figure c1 = board[Rank._1.getValue()][File.c.getValue()];
                    Figure b1 = board[Rank._1.getValue()][File.b.getValue()];
                    rook = board[Rank._1.getValue()][File.a.getValue()];
                    if(king!=null && king.getLastPosition()==null && rook!=null && rook.getLastPosition()==null && b1==null && c1==null && d1==null) { //if didn't move then they are king & rook
                        board[Rank._1.getValue()][File.a.getValue()] = null;
                        board[Rank._1.getValue()][File.e.getValue()] = null;
                        board[Rank._1.getValue()][File.c.getValue()] = king;
                        king.makeMove(new ChessMove(null, new ChessPosition(File.c, Rank._1), null));
                        board[Rank._1.getValue()][File.d.getValue()] = rook;
                        rook.makeMove(new ChessMove(null, new ChessPosition(File.d, Rank._1), null));
                    }
                    else {
                        throw new IllegalArgumentException("Roszada niedozwolona!");
                    }
                    return;
                case BLACK:
                    king = board[Rank._8.getValue()][File.e.getValue()];
                    Figure d8 = board[Rank._8.getValue()][File.d.getValue()];
                    Figure c8 = board[Rank._8.getValue()][File.c.getValue()];
                    Figure b8 = board[Rank._8.getValue()][File.b.getValue()];
                    rook = board[Rank._8.getValue()][File.a.getValue()];
                    if(king!=null && king.getLastPosition()==null && rook!=null && rook.getLastPosition()==null && b8==null && c8==null && d8==null) { //if didn't move then they are king & rook
                        board[Rank._8.getValue()][File.a.getValue()] = null;
                        board[Rank._8.getValue()][File.e.getValue()] = null;
                        board[Rank._8.getValue()][File.c.getValue()] = king;
                        king.makeMove(new ChessMove(null, new ChessPosition(File.c, Rank._8), null));
                        board[Rank._8.getValue()][File.d.getValue()] = rook;
                        rook.makeMove(new ChessMove(null, new ChessPosition(File.d, Rank._8), null));
                    }
                    else {
                        throw new IllegalArgumentException("Roszada niedozwolona!");
                    }
                    return;
            }

        }
        else {
            figure = findFigure(figure, move);
            if(move.getType()== ChessMove.MoveType.CAPTURE) { //check if it's correct capture
                Figure captured = checkPosition(move.getTargetPosition());
                if(captured==null) {
                    if(!(figure instanceof Pawn)) {
                        throw new IllegalArgumentException("Atak na puste pole!");
                    }
                }
                else if(captured.getOwner()==figure.getOwner() || captured instanceof King) {
                    throw new IllegalArgumentException("Atak na własną figurę lub króla!");
                }
            }
            Position pos = figure.getPosition();
            Position targetPos = move.getTargetPosition();       //what if it's pawn attach? there is no Rank!!!!!
            Figure promotion = figure.makeMove(move);
            board[pos.getY().getValue()][pos.getX().getValue()] = null;
            if(promotion!=null) {
                board[targetPos.getY().getValue()][targetPos.getX().getValue()] = promotion;
            }
            else {
                board[targetPos.getY().getValue()][targetPos.getX().getValue()] = figure;
            }
            //check if check!!! - wrong move if there is!!
        }
    }

    protected Figure findFigure(Figure figure, ChessMove move) throws IllegalArgumentException {
        Figure matchedFigure = null;
        for(Figure[] cols : board) {
            for (Figure fig : cols) {
                if(figure.equals(fig) && fig.isMovePossible(move)) {
                    if(matchedFigure!=null) {
                        throw new IllegalArgumentException("Ruch niejednoznaczny - wiele pasujących figur!");
                    }
                    matchedFigure = fig;
                }
            }
        }
        if(matchedFigure==null) {
            throw new IllegalArgumentException("Żadna figura nie pasuje!");
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

    protected boolean isChecked(ChessPlayer player) {
        return false;
    }
}

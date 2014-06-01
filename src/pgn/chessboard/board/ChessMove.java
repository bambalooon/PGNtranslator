package pgn.chessboard.board;

import pgn.chessboard.players.ChessPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 01.06.14
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class ChessMove {
    public enum MoveType { NORMAL, CAPTURE, KINGSIDECASTLING, QUEENSIDECASTLING }
    private Board.Position targetPosition;
    private MoveType type;
    private ChessPlayer player;
    private PawnPromotion promotion;

    public ChessMove(ChessPlayer player, Board.Position position, MoveType type) {  //for castling position and figure should be null
        this.player = player;
        this.targetPosition = position;
        this.type = type;
    }

    public ChessMove(ChessPlayer player, Board.Position position, MoveType type, PawnPromotion promotion) {
        this(player, position, type);
        this.promotion = promotion;
    }

    public ChessPlayer getPlayer() {
        return player;
    }

    public Board.Position getTargetPosition() {
        return targetPosition;
    }

    public MoveType getType() {
        return type;
    }
}

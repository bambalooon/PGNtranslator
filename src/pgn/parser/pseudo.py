package pgn.parser;

import pgn.chessboard.board.ChessBoard;
import pgn.chessboard.board.ChessMove;
import pgn.chessboard.board.PawnPromotion;
import pgn.chessboard.figures.*;
import pgn.chessboard.players.ChessPlayer;
import pgn.tokenizer.TokenizedGame;


import javax.swing.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 09.06.14
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    public void parse(TokenizedGame game) throws ParseException, IllegalArgumentException {
        ChessBoard chessBoard := new ChessBoard();
        for(TokenizedGame.MovePair movePair : game.getMoves()) {
            String white := movePair.getWhite();
            String black := movePair.getBlack();
            parseMove(white, ChessPlayer.WHITE, chessBoard);
            if(black!=null) {
                parseMove(black, ChessPlayer.BLACK, chessBoard);
            }
        }
        //save for watching
    }

    public void parse(List<TokenizedGame> games) throws ParseException, IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    protected enum ParseState { FIGURE, SOURCE_TARGET, TARGET, SPECIAL, PROMOTION, END };
    protected char[] figures := new char[]{'B', 'K', 'N', 'P', 'Q', 'R'};

    protected void parseMove(String move, ChessPlayer player, ChessBoard board) throws ParseException, IllegalArgumentException {
        if(move.equals("O-O")) {
            board.makeMove(null, new ChessMove(player, null, ChessMove.MoveType.KINGSIDECASTLING));
            return;
        }
        else if(move.equals("O-O-O")) {
            board.makeMove(null, new ChessMove(player, null, ChessMove.MoveType.QUEENSIDECASTLING));
            return;
        }
        int i := 0;
        int len := move.length();
        ParseState state := ParseState.FIGURE;
        Figure figure := null;
        Figure promotion := null;	

        char sourceRank:=0, sourceFile:=0;
        char targetRank:=0, targetFile:=0;

        char rank:=0, file:=0;
        boolean attack := false;
        boolean check := false;
        boolean checkMate := false;
	
        ParserLoop:
        while(true) {
            char token:=0;
            if(i>=len) {
                state := ParseState.END;
            }
            else {
                token := move.charAt(i);
            }
            if(checkMate && state!=ParseState.END) {
                throw new ParseException("", 0);
            }
            switch (state) {
                case FIGURE:
                    int result := Arrays.binarySearch(figures, token);
                    if(result>=0) {
                        switch (token) {
                            case 'B': figure := new Bishop(board, player, null); break;
                            case 'K': figure := new King(board, player, null); break;
                            case 'N': figure := new Knight(board, player, null); break;
                            case 'Q': figure := new Queen(board, player, null); break;
                            case 'P': figure := new Pawn(board, player, null); break;
                            case 'R': figure := new Rook(board, player, null); break;
                        }
                        i++;
                    }
                    else {
                        figure := new Pawn(board, player, null);
                    }
                    state := ParseState.SOURCE_TARGET;
                    break;
                case SOURCE_TARGET:
                    if(token>='a' && token<='h') { //source or target
                        if(file!=0 || rank!=0) {
                            sourceFile := file;
                            sourceRank := rank;
                            file := 0; rank := 0;
                            state := ParseState.TARGET;
                        } else {
                            file := token;
                            i++;
                        }
                    }
                    else if(token>='1' && token<='8') {
                        if(rank!=0) {
                            sourceFile := file;
                            sourceRank := rank;
                            file := 0; rank := 0;
                            state := ParseState.TARGET;
                        } else {
                            rank := token;
                            i++;
                        }
                    }
                    else if(token=='x') {
                        attack := true;
                        sourceFile := file;
                        sourceRank := rank;
                        file := 0; rank := 0;
                        state := ParseState.TARGET;
                        i++;
                    }
                    else { //=+# end somewhere else!
                        if(file!=0 && (rank!=0 || figure instanceof Pawn)) {
                            targetFile := file;
                            targetRank := rank;
                            file := 0; rank := 0;
                            state := ParseState.SPECIAL;
                        }
                        else {
                            throw new ParseException("", 0); //oznaczyc numer ruchu itp
                        }
                    }
                    break;
                case TARGET:
                    if(token>='a' && token<='h') { //source or target
                        if(file!=0 || rank!=0) {
                            throw new ParseException("", 0); //oznaczyc numer ruchu itp
                        } else {
                            file := token;
                            i++;
                        }
                    }
                    else if(token>='1' && token<='8') {
                        if(rank!=0) {
                            throw new ParseException("", 0); //oznaczyc numer ruchu itp
                        } else {
                            rank := token;
                            i++;
                        }
                    }
                    else {
                        if(file!=0 && (rank!=0 || figure instanceof Pawn)) {
                            targetFile := file;
                            targetRank := rank;
                            file := 0; rank := 0;
                            state := ParseState.SPECIAL;
                        }
                        else {
                            throw new ParseException("", 0); //oznaczyc numer ruchu itp
                        }
                    }
                    break;
                case SPECIAL:
                    switch (token) {
                        case '=':
                            state := ParseState.PROMOTION;
                            i++;
                            break;
                        case '+':
                            check := true;
                            i++;
                            break;
                        case '#':
                            checkMate := true;
                            i++;
                            break;
                    }
                    break;
                case PROMOTION:
                    switch (token) {
                        case 'B': promotion := new Bishop(board, player, null); break;
                        case 'Q': promotion := new Queen(board, player, null); break;
                        case 'R': promotion := new Rook(board, player, null); break;
                        case 'N': promotion := new Knight(board, player, null); break;
                        default: throw new ParseException("", 0);
                    }
                    i++;
                    state := ParseState.SPECIAL;
                    break;
                case END:
                    if(targetFile==0 && targetRank==0) {
                        if(file!=0 && (rank!=0 || figure instanceof Pawn)) {
                            targetFile := file;
                            targetRank := rank;
                            file := 0; rank := 0;
                        }
                        else {
                            throw new ParseException("", 0); //oznaczyc numer ruchu itp
                        }
                    }
                    break ParserLoop;
            }
        }
        ChessBoard.File chessFile := (sourceFile!=0 ? ChessBoard.File.valueOf(Character.toString(sourceFile)) : null);
        ChessBoard.Rank chessRank := (sourceRank!=0 ? ChessBoard.Rank.valueOf("_"+Character.toString(sourceRank)) : null);
        ChessBoard.ChessPosition pos := new ChessBoard.ChessPosition(chessFile, chessRank);
        figure.setPosition(pos);
        chessFile := (targetFile!=0 ? ChessBoard.File.valueOf(Character.toString(targetFile)) : null);
        chessRank := (targetRank!=0 ? ChessBoard.Rank.valueOf("_"+Character.toString(targetRank)) : null);
        ChessBoard.ChessPosition target := new ChessBoard.ChessPosition(chessFile, chessRank);
        ChessMove.MoveType type := (attack ? ChessMove.MoveType.CAPTURE : ChessMove.MoveType.NORMAL);
        ChessMove chessMove;
        if(promotion!=null) {
            chessMove := new ChessMove(player, target, type,
                new PawnPromotion(promotion)
            );
        } else {
            chessMove := new ChessMove(player, target, type);
        }
        board.makeMove(figure, chessMove);
        //check if check & checkMate
    }
}

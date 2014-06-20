package pgn.tokenizer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 20.05.14
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */
public class TokenizedGame {
    private String event;
    private String site;
    private String date;
    private int round;
    private String white;
    private String black;
    private String result;
    private String ECO;
    private String Eco;
    private String whiteELO;
    private String FEN;
    private String SetUp;
    private String EventDate;

    private List<MovePair> moves = new ArrayList<MovePair>();

    public static class MovePair {
        private String white, black;

        public MovePair(String first, String second) {
            this.white = first;
            this.black = second;
        }

        public MovePair(String first) { //no second - end of game!
            this.white = first;
        }

        public String getWhite() {
            return white;
        }

        public String getBlack() {
            return black;
        }
    }

    public void addMovePair(String first, String second) {
        moves.add(new MovePair(first, second));
    }

    public void addMove(String first) {
        moves.add(new MovePair(first));
    }

    public void setOption(String option, String value) throws IllegalArgumentException, ParseException {
        if(value.equals("?")) { //stays null
            return ;
           //throw new IllegalArgumentException("Nierozpoznana wartosc tagu typu " + option); // dlaczego ten wyjątek się nie pojawia w programie
        }
        switch(option.toLowerCase()) {
            case "event":
                event = value; break;
            case "site":
                site = value; break;
            case "date":
                date = value; break;
            case "round":
                round = Integer.parseInt(value); break;
            case "white":
                white = value; break;
            case "black":
                black = value; break;
            case "result":
                result = value; break;
            case "whiteelo":
                whiteELO = value; break;
            case "fen":
                FEN = value; break;
            case "setup":
                SetUp = value; break;
            case "eventdate":
                EventDate = value; break;
            default:
                if(option.equals("ECO")) {
                    ECO = value;
                } else if(option.equals("Eco")) {
                    Eco = value;
                } else {
                    throw new IllegalArgumentException(option+": "+value);
                }
        }
    }

    public String getEvent() {
        return event;
    }

    public String getSite() {
        return site;
    }

    public String getDate() {
        return date;
    }

    public int getRound() {
        return round;
    }

    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }

    public String getResult() {
        return result;
    }

    public String getECO() {
        return ECO;
    }

    public String getEco() {
        return Eco;
    }

    public String getWhiteELO() {
        return whiteELO;
    }

    public String getFEN() {
        return FEN;
    }

    public String getSetUp() {
        return SetUp;
    }

    public String getEventDate() {
        return EventDate;
    }

    public List<MovePair> getMoves() {
        return moves;
    }

    public String toString() {
        String result = "";
        result += ((event!=null) ? "\nEvent: "+event : "");
        result += ((site!=null) ? "\nSite: "+site : "");
        result += ((date!=null) ? "\nDate: "+date : "");
        result += ((round!=0) ? "\nRound: "+round : "");
        result += ((white!=null) ? "\nWhite: "+white : "");
        result += ((black!=null) ? "\nBlack: "+black : "");
        result += ((this.result!=null) ? "\nResult: "+this.result : "");
        result += ((ECO!=null) ? "\nECO: "+ECO : "");
        result += ((Eco!=null) ? "\nEco: "+Eco : "");
        result += ((whiteELO!=null) ? "\nWhiteELO: "+whiteELO : "");
        result += ((FEN!=null) ? "\nFEN: "+FEN : "");
        result += ((SetUp!=null) ? "\nSetUp: "+SetUp : "");
        result += ((EventDate!=null) ? "\nEventDate: "+EventDate : "");

        int i=1;
        for(MovePair move : moves) {
            result += "\n"+i+++". "+move.getWhite()+((move.getBlack()!=null) ? " : "+move.getBlack() : "");
        }
        return result;
    }

    public String toHtmlString() {
        String result = "<html>";
        result += ((event!=null) ? "<br>Event: "+event : "");
        result += ((site!=null) ? "<br>Site: "+site : "");
        result += ((date!=null) ? "<br>Date: "+date : "");
        result += ((round!=0) ? "<br>Round: "+round : "");
        result += ((white!=null) ? "<br>White: "+white : "");
        result += ((black!=null) ? "<br>Black: "+black : "");
        result += ((this.result!=null) ? "<br>Result: "+this.result : "");
        result += ((ECO!=null) ? "<br>ECO: "+ECO : "");
        result += ((Eco!=null) ? "<br>Eco: "+Eco : "");
        result += ((whiteELO!=null) ? "<br>WhiteELO: "+whiteELO : "");
        result += ((FEN!=null) ? "<br>FEN: "+FEN : "");
        result += ((SetUp!=null) ? "<br>SetUp: "+SetUp : "");
        result += ((EventDate!=null) ? "<br>EventDate: "+EventDate : "");

        int i=1;
        for(MovePair move : moves) {
            result += "<br>"+i+++". "+move.getWhite()+((move.getBlack()!=null) ? " : "+move.getBlack() : "");
        }
        return result+"</html>";
    }
}

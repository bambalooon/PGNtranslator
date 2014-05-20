package pgn.tokenizer;

import java.text.ParseException;
import java.util.ArrayList;

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
    private ArrayList<MovePair> moves = new ArrayList<MovePair>();

    public static class MovePair {
        private String first, second;

        public MovePair(String first, String second) {
            this.first = first;
            this.second = second;
        }

        public MovePair(String first) { //no second - end of game!
            this.first = first;
        }

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
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
            return;
        }
        if(option.equalsIgnoreCase("Event")) {
            event = value;
        } else if(option.equalsIgnoreCase("Site")) {
            site = value;
        } else if(option.equalsIgnoreCase("Date")) {
            date = value;
        } else if(option.equalsIgnoreCase("Round")) {
            round = Integer.parseInt(value);
        } else if(option.equalsIgnoreCase("White")) {
            white = value;
        } else if(option.equalsIgnoreCase("Black")) {
            black = value;
        } else if(option.equalsIgnoreCase("Result")) {
            result = value;
        } else if(option.equals("ECO")) {
            ECO = value;
        } else if(option.equals("Eco")) {
            Eco = value;
        } else if(option.equalsIgnoreCase("WhiteELO")) {
            whiteELO = value;
        } else {
            throw new IllegalArgumentException();
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

    public String toString() {
        String result = "";
        result += "\nEvent: "+((event!=null) ? event : "?");
        result += "\nSite: "+((site!=null) ? site : "?");
        result += "\nDate: "+((date!=null) ? date : "?");
        result += "\nRound: "+((round!=0) ? round : "?");
        result += "\nWhite: "+((white!=null) ? white : "?");
        result += "\nBlack: "+((black!=null) ? black : "?");
        result += "\nResult: "+((this.result!=null) ? this.result : "?");
        result += "\nECO: "+((ECO!=null) ? ECO : "?");
        result += "\nEco: "+((Eco!=null) ? Eco : "?");
        result += "\nWhiteELO: "+((whiteELO!=null) ? whiteELO : "?");

        int i=1;
        for(MovePair move : moves) {
            result += "\n"+i+++". "+move.getFirst()+((move.getSecond()!=null) ? " : "+move.getSecond() : "");
        }
        return result;
    }
}

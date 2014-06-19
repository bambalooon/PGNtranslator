package pgn.tokenizer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.file.Files;
        //java.lang.Object
//java.nio.file.Files

/**
 * Created with IntelliJ IDEA.
 * User: BamBalooon
 * Date: 20.05.14
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
public class Tokenizer {
    private File pgnFile = null;
    private String pgnString = null;
    private File lexerOutput = null;

    private enum DataType { OPTIONS, GAME };

    public Tokenizer(File pgn) {
        pgnFile = pgn;
    }

    public Tokenizer(String pgn) {
        pgnString = pgn;
    }

    public List<TokenizedGame> tokenizeGames() throws IOException, ParseException
    {
        
        Scanner scanner;
        if(pgnFile!=null) {
            scanner = new Scanner(pgnFile);
        }
        else {
            scanner = new Scanner(pgnString);
        }
       
        ArrayList<TokenizedGame> games = new ArrayList<TokenizedGame>();

        //Patterns
        String oPattern = "^\\[([a-zA-Z]+)\\s\"([a-zA-Z0-9_\\-\\.,\\s\\?\\(\\)/]*)\"\\]$";
        Pattern optionsPattern = Pattern.compile(oPattern);

        String movePattern =  "((([a-h1-8])?(x)?[a-h][18]=[NBQR]|O-O(-O)?|([a-h1-8])?(x)?([a-h])([2-7])|([KNBQR])?([a-h1-8])?(x)?([a-h])([1-8]))([+#])?)(\\s)*((([a-h1-8])?(x)?[a-h][18]=[NBQR]|O-O(-O)?|([a-h1-8])?(x)?([a-h])([2-7])|([KNBQR])?([a-h1-8])?(x)?([a-h])([1-8]))([+#])?)";


        

        String moveEndPattern = "((([a-h1-8])?(x)?[a-h][18]=[NBQR]|O-O(-O)?|([a-h1-8])?(x)?([a-h])([2-7])|([KNBQR])([a-h1-8])?(x)?([a-h])([1-8]))([+#])?)(\\s)*(1-0|0-1|1/2-1/2)?";
        
        String endPattern = "((([a-h1-8])?(x)?[a-h][18]=[NBQR]|O-O(-O)?|([a-h1-8])?(x)?([a-h])([2-7])|([KNBQR])([a-h1-8])?(x)?([a-h])([1-8]))([+#])?)(\\s)*((([a-h1-8])?(x)?[a-h][18]=[NBQR]|O-O(-O)?|([a-h1-8])?(x)?([a-h])([2-7])|([KNBQR])([a-h1-8])?(x)?([a-h])([1-8]))([+#])?)(\\s)*(1/2|0|1)\\-(1/2|0|1)";
    
//String moveEndPattern = "^([\\da-zA-Z\\-\\+#=]+)\\s+(1/2|0|1)\\-(1/2|0|1)$";
 //String endPattern = "^([\\da-zA-Z\\-\\+#=]+)\\s+([\\da-zA-Z\\-\\+#=]+)\\s+(1/2|0|1)\\-(1/2|0|1)$";
       
        Pattern ePattern = Pattern.compile(endPattern);
        Pattern endMovePattern = Pattern.compile(moveEndPattern);
        Pattern mPattern = Pattern.compile(movePattern);
        String emptyLinePattern = "^$";
        //end patterns

        scanner.useDelimiter("\n");

        Matcher matcher, endMatcher, endNext;

        TokenizedGame game = new TokenizedGame();

        DataType mode = DataType.OPTIONS;

        String line, nextMove;
        while(scanner.hasNext()) {
            switch(mode) {
                case OPTIONS:
                    line = scanner.nextLine().trim();
                    matcher = optionsPattern.matcher(line);
                    if(matcher.matches()) {
                        game.setOption(matcher.group(1), matcher.group(2));
                    } else if(line.matches(emptyLinePattern)) {
                        mode = DataType.GAME;
                        scanner.useDelimiter("([\\d]+\\.|\\[)");
                    }
                    break;
                case GAME:
                   nextMove = scanner.next().trim();
               
                 
                   
                    matcher = mPattern.matcher(nextMove);
                    endMatcher = ePattern.matcher(nextMove);
                    endNext = endMovePattern.matcher(nextMove);
                   
                    if(matcher.matches() || endNext.matches() ) {
                        if(nextMove.matches(moveEndPattern)) { // ostatni ruch
                        /*
                            if(!game.getResult().equals(endNext.group(2))) {
                                //System.out.println("ERROR");  //throw exc
                                 throw  new ParseException("Niepoprawne zakończenie partii: " + nextMove, 0);
                            }
                        */ 
                            game.addMove(endNext.group(1));
                            mode = DataType.OPTIONS;
                            scanner.useDelimiter("\n");
                            games.add(game);
                            game = new TokenizedGame();
                        } else {
                            game.addMovePair(matcher.group(1), matcher.group(18));
                        }
                    } else if(endMatcher.matches()) {
                        /*
                        if(!game.getResult().equals(endMatcher.group(32)+"-"+endMatcher.group(34))) {
                            //System.out.println("ERROR"); //throw exc
                            throw  new ParseException("Niepoprawne zakończenie partii: " + nextMove, 0);
                        }
                        */
                        game.addMovePair(endMatcher.group(1), endMatcher.group(18));
                        mode = DataType.OPTIONS;
                        scanner.useDelimiter("\n");
                        games.add(game);
                        game = new TokenizedGame();
                    }
                    else // nie można było rozpoznać tokenu
                    {
                        int i = 1, j = 0;
                        try
                        {
                            List<String> linie = Files.readAllLines(pgnFile.toPath());
                            
                            for (String linia : linie)
                            {
                                 if(linia.lastIndexOf(nextMove) != -1)
                                 {
                                     j = i;
                                     break;
                                 }
                                 i++;
                            }
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                        
                        throw new ParseException("Błąd w partii pomiędzy " + game.getWhite() + " - " + game.getBlack() +  ", data " + game.getDate() + ".\n Niepoprawny ruch w linii " + j + " : "  + nextMove, 0);
                    }
                    break;
            }

        }

       
        return games;
    }

    public static void main(String... args) throws IOException, ParseException {
        ListIterator<TokenizedGame> games = new Tokenizer(new File("1901-1920.pgn")).tokenizeGames().listIterator();
        while(games.hasNext()) {
            System.out.println(games.next());
        }
    }

}

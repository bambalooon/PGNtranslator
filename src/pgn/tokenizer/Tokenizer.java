package pgn.tokenizer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private enum DataType { OPTIONS, GAME };

    public Tokenizer(File pgn) {
        pgnFile = pgn;
    }

    public Tokenizer(String pgn) {
        pgnString = pgn;
    }

    public Iterator<TokenizedGame> tokenizeGames() throws IOException, ParseException
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

        String movePattern = "^([\\da-zA-Z\\-\\+#=]+)\\s+([\\da-zA-Z\\-\\+#=/]+)$";
        Pattern mPattern = Pattern.compile(movePattern);

        String moveEndPattern = "^([\\da-zA-Z\\-\\+#=]+)\\s+(1/2|0|1)\\-(1/2|0|1)$";

        String endPattern = "^([\\da-zA-Z\\-\\+#=]+)\\s+([\\da-zA-Z\\-\\+#=]+)\\s+(1/2|0|1)\\-(1/2|0|1)$";
        Pattern ePattern = Pattern.compile(endPattern);

        String emptyLinePattern = "^$";
        //end patterns

        scanner.useDelimiter("\n");

        Matcher matcher, endMatcher;

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

                    if(matcher.matches()) {
                        if(nextMove.matches(moveEndPattern)) {
                            if(!game.getResult().equals(matcher.group(2))) {
                                System.out.println("ERROR");  //throw exc
                            }
                            game.addMove(matcher.group(1));
                            mode = DataType.OPTIONS;
                            scanner.useDelimiter("\n");
                            games.add(game);
                            game = new TokenizedGame();
                        } else {
                            game.addMovePair(matcher.group(1), matcher.group(2));
                        }
                    } else if(endMatcher.matches()) {
                        if(!game.getResult().equals(endMatcher.group(3)+"-"+endMatcher.group(4))) {
                            System.out.println("ERROR"); //throw exc
                        }
                        game.addMovePair(endMatcher.group(1), endMatcher.group(2));
                        mode = DataType.OPTIONS;
                        scanner.useDelimiter("\n");
                        games.add(game);
                        game = new TokenizedGame();
                    }
                    break;
            }

        }



        return games.iterator();
    }

    public static void test() throws IOException, ParseException {
        Iterator<TokenizedGame> games = new Tokenizer(new File("1800-1900.pgn")).tokenizeGames();
        while(games.hasNext()) {
            System.out.println(games.next());
        }
    }

}

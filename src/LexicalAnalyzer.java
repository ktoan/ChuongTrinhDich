import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author ToanNK16 on 2023-10-07
 * @project ChuongTrinhDich
 * @birthday 2002/09/02
 */

public class LexicalAnalyzer {
    public static void main(String[] args) {
        DFA dfa = new DFA();
        String filePath = "assets\\input.c";
        StringBuilder allLines = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allLines.append(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Token> tokenList = tokenizeLine(allLines.toString(), dfa);
        for (Token s : tokenList) {
            System.out.println(s);
        }
    }


    /**
     * Tokenizes a given input line using a DFA (Deterministic Finite Automaton).
     *
     * @param line The input line to tokenize.
     * @param dfa  The DFA (Deterministic Finite Automaton) used for tokenization.
     * @return A list of tokens representing the input line.
     */
    static List<Token> tokenizeLine(String line, DFA dfa) {
        List<Token> tokens = new ArrayList<>();
        String lexeme = "";
        int currentState = dfa.getStartState();
        for (int i = 0; i < line.length(); ) {
            char c = line.charAt(i);
            if (!dfa.getTransitions().containsKey(currentState) || !dfa.getTransitions().get(currentState).containsKey(c)) {
                TokenType type;
                if (Constants.identifierEndStates.contains(currentState)) {
                    type = Constants.keywords.contains(lexeme) ? TokenType.Keyword : TokenType.Identifier;
                } else if (Constants.numberEndStates.contains(currentState)) {
                    type = TokenType.Number;
                } else if (Constants.addOpEndStates.contains(currentState)) {
                    type = TokenType.AddOp;
                } else if (Constants.mulOpEndStates.contains(currentState)) {
                    type = TokenType.MulOp;
                } else if (Constants.relOpEndStates.contains(currentState)) {
                    type = TokenType.RelOp;
                } else if (Constants.andEndStates.contains(currentState)) {
                    type = TokenType.And;
                } else if (Constants.orEndStates.contains(currentState)) {
                    type = TokenType.Or;
                } else if (Constants.notEndStates.contains(currentState)) {
                    type = TokenType.Not;
                } else if (Constants.acceptCharsEndStates.contains(currentState)) {
                    type = TokenType.Other;
                } else if (Constants.otherCharsEndStates.contains(currentState)) {
                    type = TokenType.Error;
                } else {
                    if (lexeme.isEmpty()) {
                        if (Character.isWhitespace(c)) {
                            ++i;
                            continue;
                        } else {
                            type = TokenType.Error;
                            lexeme = String.valueOf(c);
                        }
                    } else {
                        type = TokenType.AcceptedDigits;
                    }
                }

                tokens.add(new Token(type, lexeme));

                currentState = 0;
                lexeme = "";

                if (Character.isWhitespace(c) || type == TokenType.Error) {
                    ++i;
                }
            } else {
                currentState = dfa.getTransitions().get(currentState).get(c);
                if (!Character.isWhitespace(c)) {
                    lexeme += c;
                }
                ++i;
            }
        }

        return tokens;
    }
}

enum TokenType {
    Keyword("keyword"), Identifier("identify"), Number("num"), AddOp, MulOp, RelOp, And, Or, Not, Other("Error"), AcceptedDigits, Error("error");
    private final String value;

    TokenType() {
        this.value = this.name().toLowerCase();
    }

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

class Token {
    TokenType type;
    String lexeme;

    Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        if (type == TokenType.Keyword || type == TokenType.Identifier || type == TokenType.Number || type == TokenType.Error || type == TokenType.Other) {
            return type.getValue() + " : " + lexeme;
        } else {
            return lexeme + " : " + lexeme;
        }
    }
}

class DFA {
    private Map<Integer, Map<Character, Integer>> transitions;
    private final int startState;

    DFA() {
        startState = 0;
        initTransitions();
    }

    int getStartState() {
        return startState;
    }

    Map<Integer, Map<Character, Integer>> getTransitions() {
        return transitions;
    }

    private void initTransitions() {
        this.transitions = new HashMap<>();
        List<Character> letters = new ArrayList<>();
        List<Character> digits = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; ++c)
            letters.add(c);

        for (char c = 'A'; c <= 'Z'; ++c)
            letters.add(c);

        for (char c = '0'; c <= '9'; ++c) {
            digits.add(c);

        }

        for (char c : letters)
            transitions.computeIfAbsent(0, k -> new HashMap<>()).put(c, 1);

        transitions.computeIfAbsent(1, k -> new HashMap<>()).put('_', 1);

        for (char c : letters)
            transitions.computeIfAbsent(1, k -> new HashMap<>()).put(c, 1);

        for (char c : digits) {
            transitions.computeIfAbsent(1, k -> new HashMap<>()).put(c, 1);
            transitions.computeIfAbsent(0, k -> new HashMap<>()).put(c, 2);
            transitions.computeIfAbsent(2, k -> new HashMap<>()).put(c, 2);
            transitions.computeIfAbsent(3, k -> new HashMap<>()).put(c, 4);
            transitions.computeIfAbsent(4, k -> new HashMap<>()).put(c, 4);
            transitions.computeIfAbsent(5, k -> new HashMap<>()).put(c, 7);
            transitions.computeIfAbsent(6, k -> new HashMap<>()).put(c, 7);
            transitions.computeIfAbsent(7, k -> new HashMap<>()).put(c, 7);
        }

        transitions.computeIfAbsent(2, k -> new HashMap<>()).put('.', 3);
        transitions.computeIfAbsent(2, k -> new HashMap<>()).put('E', 5);
        transitions.computeIfAbsent(4, k -> new HashMap<>()).put('E', 5);
        transitions.computeIfAbsent(5, k -> new HashMap<>()).put('+', 6);
        transitions.computeIfAbsent(5, k -> new HashMap<>()).put('-', 6);

        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('+', 9);
        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('-', 9);
        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('*', 10);
        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('/', 10);

        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('|', 11);
        transitions.computeIfAbsent(11, k -> new HashMap<>()).put('|', 12);

        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('&', 13);
        transitions.computeIfAbsent(13, k -> new HashMap<>()).put('&', 14);

        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('!', 15);
        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('=', 16);
        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('<', 17);
        transitions.computeIfAbsent(0, k -> new HashMap<>()).put('>', 18);
        transitions.computeIfAbsent(15, k -> new HashMap<>()).put('=', 19);
        transitions.computeIfAbsent(16, k -> new HashMap<>()).put('=', 19);
        transitions.computeIfAbsent(17, k -> new HashMap<>()).put('=', 19);
        transitions.computeIfAbsent(18, k -> new HashMap<>()).put('=', 19);

        for (char c : Constants.acceptChars) {
            transitions.computeIfAbsent(0, k -> new HashMap<>()).put(c, 19);
        }
    }
}

interface Constants {
    Set<String> keywords = new HashSet<>(Arrays.asList("while", "if", "else", "return", "break", "continue", "int", "float", "void", "for"));
    Set<Character> acceptChars = new HashSet<>(Arrays.asList('(', ')', '{', '}', '[', ']'));
    Set<Integer> identifierEndStates = new HashSet<>(List.of(1));
    Set<Integer> numberEndStates = new HashSet<>(Arrays.asList(2, 4, 7));
    Set<Integer> acceptCharsEndStates = new HashSet<>(List.of(8));
    Set<Integer> addOpEndStates = new HashSet<>(List.of(9));
    Set<Integer> mulOpEndStates = new HashSet<>(List.of(10));
    Set<Integer> orEndStates = new HashSet<>(List.of(12));
    Set<Integer> andEndStates = new HashSet<>(List.of(14));
    Set<Integer> notEndStates = new HashSet<>(List.of(15));
    Set<Integer> relOpEndStates = new HashSet<>(Arrays.asList(17, 18, 19));
    Set<Integer> otherCharsEndStates = new HashSet<>(List.of(20));
}
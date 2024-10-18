import java.util.*;
import java.util.regex.*;

public class Main {

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.print("Ingrese la expresión a analizar: ");
    String entrada = scanner.nextLine();
    List<Token> tokens = analizar(entrada);
    System.out.println(" ");
    System.out.println(" === Tokens encontrados === ");
    System.out.println(" ");

    for (Token token : tokens) {
      System.out.println(token);
    }
    System.out.println(" ");
    System.out.println(" ========================= ");
  }

  public static List<Token> analizar(String entrada) {
    List<Token> tokens = new ArrayList<>();

    String patronNumero = "\\d+";
    String patronVariable = "[a-zA-Z]+";
    String patronOperador = "[+\\-*/=]";
    String patronEspacio = "\\s+";

    String patronesTokens = String.format(
        "(?<NUMERO>%s)|(?<VARIABLE>%s)|(?<OPERADOR>%s)|(?<ESPACIO>%s)",
        patronNumero, patronVariable, patronOperador, patronEspacio
    );

    Pattern pattern = Pattern.compile(patronesTokens);
    Matcher matcher = pattern.matcher(entrada);

    while (matcher.find()) {
      if (matcher.group("ESPACIO") != null) {
        continue;
      } else if (matcher.group("NUMERO") != null) {
        tokens.add(new Token("Literal numérico", matcher.group()));
      } else if (matcher.group("VARIABLE") != null) {
        tokens.add(new Token("Variable", matcher.group()));
      } else if (matcher.group("OPERADOR") != null) {
        String operador = matcher.group();
        String tipo = identificarOperador(operador);
        tokens.add(new Token(tipo, operador));
      }
    }

    return tokens;
  }

  private static String identificarOperador(String operador) {
    return switch (operador) {
      case "+" -> "Operador aritmético (suma)";
      case "-" -> "Operador aritmético (resta)";
      case "*" -> "Operador aritmético (multiplicación)";
      case "/" -> "Operador aritmético (división)";
      case "=" -> "Operador de asignación";
      default -> "Operador desconocido";
    };
  }

  public static class Token {
    String tipo;
    String valor;

    public Token(String tipo, String valor) {
      this.tipo = tipo;
      this.valor = valor;
    }

    @Override
    public String toString() {
      return "Token{" +
          "tipo='" + tipo + '\'' +
          ", valor='" + valor + '\'' +
          '}';
    }
  }

}



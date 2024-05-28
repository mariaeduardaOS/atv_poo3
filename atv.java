import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private boolean consoleMode;
    private boolean fileMode;
    private String logFileName;

    public Logger(boolean consoleMode, boolean fileMode, String logFileName) {
        this.consoleMode = consoleMode;
        this.fileMode = fileMode;
        this.logFileName = logFileName;
    }

    public void log(String message, LogLevel level) {
        String formattedMessage = formatMessage(message, level);
        if (consoleMode) {
            printToConsole(formattedMessage, level);
        }
        if (fileMode) {
            writeToFile(formattedMessage);
        }
    }

    private String formatMessage(String message, LogLevel level) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        return "[" + formattedTime + "] [" + level.toString() + "] " + message;
    }

    private void printToConsole(String message, LogLevel level) {
        switch (level) {
            case DEBUG:
                System.out.println("\u001B[32m" + message + "\u001B[0m"); //Verde
                break;
            case AVISO:
                System.out.println("\u001B[33m" + message + "\u001B[0m"); //Amarelo
                break;
            case ERRO:
                System.out.println("\u001B[31m" + message + "\u001B[0m"); //Vermelho
                break;
            default:
                System.out.println(message);
        }
    }

    private void writeToFile(String message) {
        try (FileWriter fileWriter = new FileWriter(logFileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            printWriter.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum LogLevel {
        DEBUG,
        AVISO,
        ERRO
    }

    public static void main(String[] args) {
        Logger logger = new Logger(true, true, "log.txt");
        logger.log("Isso é uma mensagem de Debug", LogLevel.DEBUG);
        logger.log("Isso é uma mensagem de Aviso", LogLevel.AVISO);
        logger.log("Isso é uma mensagem de Erro", LogLevel.ERRO);
    }
}

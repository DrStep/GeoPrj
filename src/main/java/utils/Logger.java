package utils;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 08.10.13
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */

enum LogStatus {
    ERROR, NETWORK_REQUEST, SERVER
}

public class Logger {
    public static void error(String message) {
        Logger.print(LogStatus.ERROR, message);
    }

    public static void network(String message) {
        Logger.print(LogStatus.NETWORK_REQUEST, message);
    }

    public static void server(String message) {
        Logger.print(LogStatus.SERVER, message);
    }

    public static void print(LogStatus logStatus, String message) {
        System.out.println(logStatus + ": " + message);
    }
}

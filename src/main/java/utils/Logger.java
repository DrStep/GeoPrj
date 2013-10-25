package utils;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 08.10.13
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */

enum LogStatus {
    ERROR("ERROR"), NETWORK_REQUEST("NETWORK_REQUEST"), SERVER("SERVER"), USERDATA("USERDATA");
    private final String status;

    LogStatus(String logStatus) {
        status = logStatus;
    }

    public String toString() {
        return status;
    }
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

    public static void userData(String message) {
        Logger.print(LogStatus.USERDATA, new StringBuffer().append(": ").append(message).toString());
    }

    public static void print(LogStatus logStatus, String message) {
        System.out.println(new StringBuilder(logStatus.toString()).append(": ").append(message));
    }
}

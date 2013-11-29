package server.resourceSystem;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 29.11.13
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class ServerData {
    private static int PORT =  8090;
    private static int THREAD_POOL = 1000;

    private int port = -1;
    private int threadPool = -1;

    public int getPort() {
        if (port == -1) {
            return PORT;
        }
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getThreadPool() {
        if (threadPool == -1) {
            return THREAD_POOL;
        }
        return threadPool;
    }

    public void setThreadPool(int threadPool) {
        this.threadPool = threadPool;
    }
}

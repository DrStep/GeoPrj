import server.dbService.DBService;
import server.frontend.FrontendImpl;
import server.msgsystem.MessageSystemImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import server.auth.AccountServiceImpl;
import utils.Logger;
import server.vkauth.AccountVkService;
import server.resourceSystem.*;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 27.09.13
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public final static int PORT = 8090;

    private String generateIndent(int count) {
        StringBuffer indent = new StringBuffer(" ");
        for (int i = 0; i < count; indent.append(" "), i++) {};
        return indent.toString();
    }

    public static void main(String []args) {
        if (args.length != 1) {
            System.out.append("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);
        System.out.append("Starting at port: ").append(portString).append('\n');

        MessageSystemImpl ms = new MessageSystemImpl();

        FrontendImpl frontendImpl = new FrontendImpl(ms);
        AccountVkService vkService = new AccountVkService(ms);
        AccountServiceImpl service = new AccountServiceImpl(ms);

        new Thread(frontendImpl).start();
        new Thread(vkService).start();
        new Thread(service).start();

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontendImpl), "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        //ServerData serverData = SAXP.readServerData("server.xml");
        //System.out.println("PORT:" + serverData.getPort() + " ThreadPool:" + serverData.getThreadPool());
        DBService db = new DBService();
        try {
            //server.start();
           // server.join();
        } catch (Exception e) {
            Logger.server(e.getMessage());
        }
    }
}

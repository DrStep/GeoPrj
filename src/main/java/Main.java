import server.FrontendImpl;
import server.MessageSystem;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import server.innerauth.AccountService;
import utils.Logger;
import server.vk.AccountVkService;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 27.09.13
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public final static int PORT = 8090;

    public static void main(String []args) {
        if (args.length != 1) {
            System.out.append("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);
        System.out.append("Starting at port: ").append(portString).append('\n');

        MessageSystem ms = new MessageSystem();

        FrontendImpl frontendImpl = new FrontendImpl(ms);
        AccountVkService vkService = new AccountVkService(ms);
        AccountService service = new AccountService(ms);

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

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            Logger.server(e.getMessage());
        }
    }
}

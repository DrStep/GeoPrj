package server.resourceSystem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 29.11.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class ServerHandler extends DefaultHandler {
    private static String PORT = "port";
    private static String THREAD_POOL = "thread_pool";

    private boolean mPort = false;
    private boolean mThreadPoll = false;

    private ServerData server;

    public void startDocument() throws SAXException {
        server = new ServerData();
        System.out.println("Start document");
    }

    public void endDocument() throws SAXException {
        System.out.println("End document ");
    }

    public void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(PORT)) {
            mPort = true;
        }

        if (qName.equalsIgnoreCase(THREAD_POOL)) {
            mThreadPoll = true;
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        if (mPort) {
            server.setPort(Integer.parseInt(new String(ch, start, length)));
            mPort = false;
        }

        if (mThreadPoll) {
            server.setThreadPool(Integer.parseInt(new String(ch, start, length)));
            mThreadPoll = false;
        }
    }

    public ServerData getServer() {
        return server;
    }
}

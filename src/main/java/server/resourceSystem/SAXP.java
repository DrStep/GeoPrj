package server.resourceSystem;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 29.11.13
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */
public class SAXP {

    public static ServerData readServerData(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            ServerHandler serverHandler = new ServerHandler();
            saxParser.parse(xmlFile, serverHandler);
            return serverHandler.getServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

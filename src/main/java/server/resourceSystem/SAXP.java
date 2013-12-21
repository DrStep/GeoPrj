package server.resourceSystem;

import org.xml.sax.helpers.DefaultHandler;

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

    public static Object  readServerData(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ResHandler handler = null;

            handler =  new ServerHandler();

            saxParser.parse(xmlFile, handler);
            return handler.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

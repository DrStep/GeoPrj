package server.resourceSystem;

import org.xml.sax.helpers.DefaultHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 21.12.13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class ResHandler extends DefaultHandler {
    public abstract Object getObject();
}

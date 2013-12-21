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
public class ServerHandler extends ResHandler {
    private static String CLASSNAME = "class";
    private String element;
    private Object object;

    public void startDocument() throws SAXException {
        object = ReflectionHelper.createIntance("server.resourceSystem.ServerData");
        System.out.println("Start document");
    }

    public void endDocument() throws SAXException {
        System.out.println("End document ");
    }

    public void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException {
        if(qName != CLASSNAME){
            element = qName;
        }
        else{
            String className = attributes.getValue(0);
            System.out.println("Class name: " + className);
            object = ReflectionHelper.createIntance(className);
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        if(element != null){
            String value = new String(ch, start, length);
            System.out.println(element + " = " + value);
            ReflectionHelper.setFieldValue(object, element, value);
        }
    }

    @Override
    public Object getObject() {
        return object;
    }
}

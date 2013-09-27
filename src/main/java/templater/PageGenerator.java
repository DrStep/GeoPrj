package templater;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 27.09.13
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class PageGenerator {
    private static final String HTML_DIR = "templates";
    private static final Configuration CFG = new Configuration();

    public static String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + "/" + filename);
            template.process(data, stream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return stream.toString();
    }
}

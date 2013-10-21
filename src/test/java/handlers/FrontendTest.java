package handlers;

import org.junit.Test;
import templater.PageGenerator;
import junit.framework.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 19.10.13
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
public class FrontendTest {

    public void setUp() throws Exception {

    }

    @Test
    public void testDoGet() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", "toha");
        String page = PageGenerator.getPage("userform.tml", map);

        Assert.assertTrue(page.contains("name"));
        Assert.assertTrue(page.contains("password"));
        Assert.assertTrue(page.contains("submit"));

    }

    public void testDoPost() throws Exception {

    }
}

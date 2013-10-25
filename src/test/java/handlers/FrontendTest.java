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

       /* map.put("error", "Session can't create");
        String page = PageGenerator.getPage("error.tml", map);
        Assert.assertTrue(page.contains("Session can't create"));*/

        map.put("token", new String("abc").hashCode());
        String page = PageGenerator.getPage("authform.tml", map);
        Assert.assertTrue(page.contains("dasd"));
    }

    public void testDoPost() throws Exception {

    }
}

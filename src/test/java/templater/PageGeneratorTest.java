package templater;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sofia
 * Date: 01.11.13
 * Time: 12:54
 * To change this template use File | Settings | File Templates.
 */
public class PageGeneratorTest {
    @Test
    public void testGetPage() throws Exception {
        String state = "someState";

        Map<String, Object> pageVariables = new HashMap<String, Object>();
        pageVariables.put("userState", state);

        String result = PageGenerator.getPage("userid.tml", pageVariables);

        Assert.assertTrue("Where is my state? State wasn't found", result.contains("someState"));
        //overlay
    }
}

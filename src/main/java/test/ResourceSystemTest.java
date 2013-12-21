package test;

import junit.framework.Assert;
import org.junit.Test;
import server.resourceSystem.ResourceTest;
import server.resourceSystem.SAXP;

/**
 * Created with IntelliJ IDEA.
 * User: Philipp
 * Date: 21.12.13
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class ResourceSystemTest {
    @Test
    public void resourceSystemTest() {
        ResourceTest resourceTest = (ResourceTest) SAXP.readServerData("restest.xml");

        Assert.assertTrue(resourceTest.getId() == 10);
        Assert.assertTrue(resourceTest.getName().equals("dsadsa"));
        Assert.assertTrue(resourceTest.getLat() == 100);
        Assert.assertTrue(resourceTest.getLng() == 180);
        System.out.println(resourceTest.getId() + " " + resourceTest.getName() + " " + resourceTest.getLat() + resourceTest.getLng());
    }
}

package server.innerauth;
import junit.framework.Assert;
import org.junit.Test;
import server.MessageSystem;
import server.UserData;
/**
 * Created with IntelliJ IDEA.
 * User: philipp
 * Date: 01.11.13
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
public class AccountServiceTest {
    private AccountService AcServise;
    private MessageSystem messageSystem;
    @Test
    public void testGetUserData() throws Exception {
       AcServise = new AccountService(messageSystem);
       UserData user = new UserData(1L,"toha", "toha");
       UserData data = new UserData();
       data = AcServise.getUserData(user);
       Assert.assertEquals(user,data);
    }
}

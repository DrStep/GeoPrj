package server.innerauth;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import server.MessageSystem;
import server.UserData;

/**
 * Created with IntelliJ IDEA.
 * User: sofia
 * Date: 02.11.13
 * Time: 0:50
 * To change this template use File | Settings | File Templates.
 */
public class AccountServiceTest
{
    private AccountService accountService;
    private MessageSystem messageSystem;

    @Before
    public void setUp()
    {
        messageSystem = new MessageSystem();
        accountService = new AccountService(messageSystem);
    }


    @Test
    public void testGetUserData() throws Exception
    {

        UserData user = new UserData(2L,"sonya", "sonya");
        UserData checkUser = new UserData("sonya", "sonya");

        Assert.assertEquals("authentication fails",user.getId(),accountService.getUserData(checkUser).getId());
    }
}

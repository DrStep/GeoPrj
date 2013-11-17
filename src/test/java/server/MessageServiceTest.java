package server;

import junit.framework.Assert;
import org.apache.http.util.Asserts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import server.innerauth.AccountService;
import server.innerauth.MsgGetUserData;
import server.innerauth.MsgUpdateUserData;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: sofia
 * Date: 01.11.13
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest  extends Asserts
{
    @Mock
    private Frontend frontend;

    private MessageSystem messageSystem;
    UserData userData;

    @Before
    public void setUp()
    {
        userData = new UserData();
        userData.setName("sofia");
        userData.setPassword("sofia");

        messageSystem = new MessageSystem();

    }

    @Test
    public void MsgGetUserDataTest()
    {
        Msg msg = new MsgGetUserData(frontend.getAddress(), null, "123456", userData );
        Assert.assertEquals( "addresses do not match", msg.getFrom(), frontend.getAddress());
    }

    @Test
    public void MsgUpdateUserData()
    {
        Msg msg = new MsgUpdateUserData(null, frontend.getAddress(), "123456", userData );
        Assert.assertEquals( "addresses do not match", msg.getTo(), frontend.getAddress());
    }

}

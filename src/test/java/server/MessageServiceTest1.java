package server;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import server.innerauth.AccountService;
import server.innerauth.MsgGetUserData;
import server.innerauth.MsgUpdateUserData;

import java.util.Queue;

import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: sofia
 * Date: 01.11.13
 * Time: 23:35
 * To change this template use File | Settings | File Templates.
 */
public class MessageServiceTest1 {

    private Frontend frontend;
    private AccountService accountService;

    private MessageSystem messageSystem;
    UserData userData;
    UserData userData1;

    //Address asAddress;
    //Address fAddress;


    @Before
    public void setUp()
    {
        userData = new UserData();
        userData.setName("sofia");
        userData.setPassword("sofia");

        userData1 = new UserData(2L, "sofia", "sofia");

        //asAddress = new Address();
        //fAddress = new Address();

        messageSystem = new MessageSystem();
        frontend = new Frontend(messageSystem);
        accountService = new AccountService(messageSystem);

        messageSystem.addService(frontend);
        messageSystem.addService(accountService);

        //when(accountService.getUserData(userData)).thenReturn(userData1);
        //when(frontend.getAddress()).thenReturn(fAddress);
        //when(accountService.getAddress()).thenReturn(asAddress);
    }

    @Test
    public void CheckMsgQueueTest()
    {
        System.out.println("Address");
        System.out.println(frontend.getAddress());
        Msg msg = new MsgGetUserData(frontend.getAddress(), accountService.getAddress(), "123456", userData );
        messageSystem.sendMessage(msg);
        Msg msg1 = new MsgGetUserData(frontend.getAddress(), accountService.getAddress(), "987654", userData1 );
        messageSystem.sendMessage(msg1);

        Queue<Msg> messages = messageSystem.getAbonentMessagesQueue(accountService);

        Msg msgGotten = messages.poll();
        Assert.assertEquals(msg, msgGotten);
        msgGotten = messages.poll();
        Assert.assertEquals(msg1,msgGotten);


        msg = new MsgUpdateUserData(accountService.getAddress(), frontend.getAddress(), "123456", userData );
        messageSystem.sendMessage(msg);
        msg1 = new MsgUpdateUserData(accountService.getAddress(), frontend.getAddress(), "987654", userData1 );
        messageSystem.sendMessage(msg1);

        messages = messageSystem.getAbonentMessagesQueue(frontend);

        msgGotten = messages.poll();
        Assert.assertEquals(msg, msgGotten);
        msgGotten = messages.poll();
        Assert.assertEquals(msg1,msgGotten);
    }
}

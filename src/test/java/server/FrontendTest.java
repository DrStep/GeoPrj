package server;

import com.sun.jna.platform.FileUtils;
import javax.servlet.http.Cookie;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import server.innerauth.AccountService;
import server.vk.AccountVkService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 19.10.13
 * Time: 10:43
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class FrontendTest {

    //@Mock
    private HttpServletRequest  request;

    //@Mock
    private HttpServletResponse  response;

    private MessageSystem ms;

    AccountVkService vkService;
    AccountService service;

    private Frontend frontend;
    private HttpSession httpSession;

    private PrintWriter printWriter;

    private String url;
    private String fileName;
    String result;

    @Before
    public void setUp() throws Exception
    {
        result="";
        ms = new MessageSystem();
        frontend = new Frontend(ms);
        service = new AccountService(ms);
        vkService = new AccountVkService(ms);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        httpSession = mock(HttpSession.class);
        //when(httpSession.getId()).thenReturn("123456");

        fileName = "FileTestDoGet.txt";
        //printWriter = new PrintWriter(System.out);
        //printWriter = new PrintWriter(fileName);
        //when(response.getWriter()).thenReturn(printWriter);

        url = "";
        when(request.getPathInfo()).thenReturn(url);
        when(request.getSession()).thenReturn(httpSession);

        //response.setStatus(HttpServletResponse.SC_OK);
    }

    public void readFileToString(String fileName)
    {
        try{
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str = "";
            while ((str = in.readLine()) != null)
            {
                result += str;
                //System.out.println(result);
            }
        }
        catch(IOException e)
        {

        }
    }

    @Test
    public void testDoGet() throws Exception
    {
        //String result = "";

        //Test1
        printWriter = new PrintWriter(fileName);
        when(response.getWriter()).thenReturn(printWriter);
        url = "/auth";
        when(request.getPathInfo()).thenReturn(url);
        frontend.doGet(request, response);
        printWriter.flush();
        readFileToString(fileName);
        System.out.println(result);
        Assert.assertTrue("Result html string doesn't contain the right string", result.contains("Error Session can't create"));
        //printWriter.write("");
        printWriter.close();//по другому не очищается буффер
        result="";

        //Test2
        printWriter = new PrintWriter(fileName);
        when(response.getWriter()).thenReturn(printWriter);
        url = "/auth";
        when(request.getPathInfo()).thenReturn(url);
        when(httpSession.getId()).thenReturn("123456");
        frontend.doGet(request, response);
        printWriter.flush();
        readFileToString(fileName);
        System.out.println(result);
        Assert.assertFalse("Result html string doesn't contain the right string", result.contains("Error Session can't create"));
        //printWriter.write("");
        printWriter.close();//по другому не очищается буффер
        result="";

        //Test3
        //Checks case if Access_Token exist. Redirect user to main page
        printWriter = new PrintWriter(fileName);
        when(response.getWriter()).thenReturn(printWriter);

        url= "/vk-auth";
        when(request.getPathInfo()).thenReturn(url);

        Cookie cookie = mock(Cookie.class);
        when(cookie.getName()).thenReturn("access_token");
        when(cookie.getValue()).thenReturn("qwertyuio");
        Cookie[] cookies = new Cookie[1];
        cookies[0] = cookie;
        when(request.getCookies()).thenReturn(cookies);

        when(httpSession.getId()).thenReturn("123456");

        frontend.doGet(request, response);
        //как здесь проверить что редирект выполнился?     -->   response.sendRedirect("/auth");


        //Test 4
        //case if Get Access_Token By Code
        when(cookie.getName()).thenReturn("wrong_string");
        when(request.getParameter("code")).thenReturn("code");
        frontend.doGet(request, response);
        //как здесь проверить что редирект выполнился?       -->   response.sendRedirect("/auth");

        printWriter.close();

        //Test5
        printWriter = new PrintWriter(fileName);
        when(response.getWriter()).thenReturn(printWriter);

        url= "/authform";
        when(request.getPathInfo()).thenReturn(url);
        frontend.doGet(request, response);
        printWriter.flush();
        readFileToString(fileName);
        System.out.println(result);
        Assert.assertTrue("Result html string doesn't contain the right string", result.contains("<h1>Авторизация</h1>"));
        printWriter.close();
        result="";


        //Test6
        printWriter = new PrintWriter(fileName);
        when(response.getWriter()).thenReturn(printWriter);

        url= "";
        when(request.getPathInfo()).thenReturn(url);
        frontend.doGet(request, response);
        printWriter.flush();
        readFileToString(fileName);
        System.out.println(result);
        Assert.assertTrue("Result html string doesn't contain the right string", result.contains("Permission denied. Please authorized you account."));
        printWriter.close();
        result="";

        /* Set user access_token */

    }

    @Test
    public void testDoPost() throws Exception {

        printWriter = new PrintWriter(fileName);
        when(response.getWriter()).thenReturn(printWriter);


        url= "/checkuser";

        when(httpSession.getId()).thenReturn("123456");
        when(request.getParameter("name")).thenReturn("sonya");
        when(request.getParameter("password")).thenReturn("sonya");

        when(request.getPathInfo()).thenReturn(url);
        frontend.doPost(request, response);

        printWriter.flush();
        readFileToString(fileName);
        System.out.println(result);

        Assert.assertTrue(result.contains("name=\"returnedStatus\""));

        //UserData userData = frontend.getUserDataBySessionId(httpSession.getId());
        //Assert.assertTrue("Gotten user id is wrong", userData.getId().equals(2L));

        printWriter.close();
        result="";
    }
}

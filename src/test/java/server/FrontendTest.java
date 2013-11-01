package server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
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

    @Mock
    private HttpServletRequest  request;

    @Mock
    private HttpServletResponse  response;

    MessageSystem ms;

    @Before
    public void setUp() throws Exception
    {
        ms = new MessageSystem();
        request = mock(HttpServletRequest.class);


        //when(response.setContentType(anyString())).thenReturn();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGet() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

      /* map.put("error", "Session can't create");
        String page = PageGenerator.getPage("error.tml", map);
        Assert.assertTrue(page.contains("Session can't create"));

        map.put("token", new String("abc").hashCode());
        String page = PageGenerator.getPage("authform.tml", map);
        Assert.assertTrue(page.contains("dasd"));*/
        //HttpServletResponse response = new HttpServletResponse();


        //Frontend frontend = new Frontend(ms);
        //frontend.doGet(request, response);

    }

    public void testDoPost() throws Exception {
        FunctionTest functionTest = new FunctionTest();
        functionTest.testLogin("http://localhost:8090/authform", "sonya", "sonya");
    }
}

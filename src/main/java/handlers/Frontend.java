package handlers;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 27.09.13
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class Frontend extends HttpServlet implements Abonent, Runnable {
    public static final String ACCESS_TOKEN = "access_token";
    public static final String PATH_VK_AUTH = "/vk-auth";
    public static final String CODE = "code";
    private MessageSystem ms;
    private Address address;

    // Contain Session_id and userData
    private Map<String, UserData> sessionIdToUserData;

    public Frontend(MessageSystem ms) {
        super();
        this. ms = ms;
        address = new Address();
        ms.addService(this);
        sessionIdToUserData = new HashMap<String, UserData>();
    }

    public void setUserData(String sessionId, UserData userData) {
        if (sessionIdToUserData.containsKey(sessionId)) {
            sessionIdToUserData.get(sessionId).copy(userData);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();

        if (request.getPathInfo().equals(PATH_VK_AUTH)) {
            String accessToken = getCookie(request, ACCESS_TOKEN);

            /* Access_Token exist. Redirect user to main page */
            if (accessToken != null) {
                responseUserPage(response, "Welcome");
                return;
            }

            String sessionId = request.getSession().getId();

            /* Set user access_token */
            if (sessionIdToUserData.containsKey(sessionId)) {
                UserData userData = sessionIdToUserData.get(sessionId);
                if (!userData.isEmpty()) {
                    response.addCookie(new Cookie(ACCESS_TOKEN, userData.getAccessToken()));
                    responseUserPage(response, "Set Token");
                    return;
                }
            }

            String code = request.getParameter(CODE);
            /* Get Access_Token By Code */
            if (code != null) {
                sessionIdToUserData.put(sessionId, new UserData());
                Address from = getAddress();
                Address to = ms.getAccountVkService().getAccountService();

                ms.sendMessage(new MsgGetVkUserData(from, to, sessionId, code));
                responseUserPage(response, "started");
                return;
            }
        }
        responseUserPage(response, "Permission denied. Please authorized you account.");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        response.getWriter().println(PageGenerator.getPage("timer.tml", pageVariables));
        return;
    }

    /* Get cookie by Key*/
    private String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(key)){
                accessToken = cookie.getValue();
                return accessToken;
            }
        }
        return accessToken;
    }

    private void responseUserPage(HttpServletResponse response, String userState) throws IOException {
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        pageVariables.put("userState", userState);
        response.getWriter().println(PageGenerator.getPage("userid.tml", pageVariables));
    }

    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH.mm.ss");
        return dateFormat.format(date);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            ms.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }
}

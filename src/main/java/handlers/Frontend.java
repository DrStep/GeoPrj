package handlers;

import templater.PageGenerator;
import utils.Logger;

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
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 27.09.13
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class Frontend extends HttpServlet implements Abonent, Runnable {
    private static final int TICK_TIME = 20;

    public static final String ACCESS_TOKEN = "access_token";
    public static final String PATH_VK_AUTH = "/vk-auth";
    public static final String CODE = "code";
    private MessageSystem messageSystem;
    private Address address;

    // Contain Session_id and userData
    private Map<String, UserData> sessionIdToUserData;

    public Frontend(MessageSystem messageSystem) {
        super();
        this.messageSystem = messageSystem;
        address = new Address();
        messageSystem.addService(this);
        sessionIdToUserData = new HashMap<String, UserData>();
    }

    public void setUserData(String sessionId, UserData userData) {
        if (sessionIdToUserData.containsKey(sessionId)) {
            sessionIdToUserData.get(sessionId).createUser(userData);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();

        if (request.getPathInfo().equals(PATH_VK_AUTH)) {
            String accessToken = getCookie(request, ACCESS_TOKEN, null);

            String sessionId = request.getSession().getId();
            System.out.println(sessionId);

            /* Access_Token exist. Redirect user to main page */
            if (accessToken != null) {
                if (sessionIdToUserData.containsKey(sessionId)) {
                    UserData userData = sessionIdToUserData.get(sessionId);
                }
                response.sendRedirect("/auth");
                return;
            }

            /* Set user access_token */
            if (sessionIdToUserData.containsKey(sessionId)) {
                UserData userData = sessionIdToUserData.get(sessionId);
                String callback = request.getParameter("callback");
                if (callback != null) {
                    Map<String, String> jsonpVal = new HashMap<String, String>();
                    if (userData.isTokenExist()) {
                        jsonpVal.put("status", "ok");
                        jsonpVal.put("access_token", userData.getAccessToken());
                    } else {
                        jsonpVal.put("status", "wait");
                    }
                    response.getWriter().println(getJSONP(callback, (Map)jsonpVal));
                    return;
                }
            }

            String code = request.getParameter(CODE);
            /* Get Access_Token By Code */
            if (code != null) {
                sessionIdToUserData.put(sessionId, new UserData());
                Address from = getAddress();
                Address to = messageSystem.getAccountVkService().getAccountService();

                messageSystem.sendMessage(new MsgGetVkUserData(from, to, sessionId, code));
                response.sendRedirect("/auth");
                return;
            }
        } else if (request.getPathInfo().equals("/auth")) {
            String sessionId = request.getSession().getId();
            if (sessionId != null) {
                response.getWriter().println(PageGenerator.getPage("auth.tml", pageVariables));
            } else {
                pageVariables.put("error", "Session can't create");
                response.getWriter().println(PageGenerator.getPage("error.tml", pageVariables));
            }
            return;
        } else if (request.getPathInfo().equals("/authform")) {
            pageVariables.put("token", new String("abc").hashCode());
            response.getWriter().println(PageGenerator.getPage("authform.tml", pageVariables));
            return;
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

    public String getJSONP(String callbackFunc, Map<Object, Object> values) {
        if (values == null) {
            return null;
        }

        int size = values.size();
        int i = 1;

        StringBuffer jsonp = new StringBuffer(callbackFunc);
        jsonp.append("({");

        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            jsonp.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");

            if (i != size) {
                jsonp.append(",");
            }

            i++;
        }
        jsonp.append("})");
        return jsonp.toString();
    }

    /* Get cookie by Key*/
    private String getCookie(HttpServletRequest request, String key, String defaultValue) {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(key)){
                accessToken = cookie.getValue();
                return accessToken;
            }
        }
        return defaultValue;
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
            try {
                long startTime = System.currentTimeMillis();
                messageSystem.execForAbonent(this);
                long deltaTime = System.currentTimeMillis() - startTime;
                if (deltaTime < TICK_TIME) {
                    Thread.sleep(TICK_TIME - deltaTime);
                }
            } catch (InterruptedException e) {
                Logger.error(e.getMessage());
            }
        }
    }
}

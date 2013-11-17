package server;

import server.innerauth.MsgGetUserData;
import server.innerauth.MsgUpdateUserData;
import server.vk.MsgGetVkUserData;
import server.vk.VkUserData;
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
    private Map<String, VkUserData> vkSessionIdToUserData;
    private Map<String, UserData> sessionIdToUserData;

    private Map<String, UserData> sessionIdToInvalidUserData;

    public Frontend(MessageSystem messageSystem) {
        super();
        this.messageSystem = messageSystem;
        address = new Address();
        messageSystem.addService(this);
        vkSessionIdToUserData = new HashMap<String, VkUserData>();
        sessionIdToUserData = new HashMap<String, UserData>();
        sessionIdToInvalidUserData = new HashMap<String, UserData>();
    }

    public void setVkUserData(String sessionId, VkUserData vkUserData) {
        if (vkSessionIdToUserData.containsKey(sessionId)) {
            vkSessionIdToUserData.get(sessionId).createUser(vkUserData);
        }
    }

    public void setUserData(String sessionId, UserData userData) {
        if (userData == null) {
            sessionIdToInvalidUserData.put(sessionId, userData);
            System.out.println("Null");
        } else {
            sessionIdToUserData.put(sessionId, userData);
            System.out.println("Ok");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getPathInfo());
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();

        request.getPathInfo();

        if (request.getPathInfo().equals(PATH_VK_AUTH)) {
            String accessToken = getCookie(request, ACCESS_TOKEN, null);
            String sessionId = request.getSession().getId();

            /* Access_Token exist. Redirect user to main page */
            if (accessToken != null) {
                System.out.println("access_token exists");
                if (vkSessionIdToUserData.containsKey(sessionId)) {
                    VkUserData vkUserData = vkSessionIdToUserData.get(sessionId);   //
                }
                response.sendRedirect("/auth");
                return;
            }

            /* Set user access_token */                                         //type type вынести в отдельный блок getuatoken
            if (vkSessionIdToUserData.containsKey(sessionId)) {
                VkUserData vkUserData = vkSessionIdToUserData.get(sessionId);
                String callback = request.getParameter("callback");
                if (callback != null) {
                    Map<String, String> jsonpVal = new HashMap<String, String>();
                    if (vkUserData.isTokenExist()) {
                        jsonpVal.put("status", "ok");
                        jsonpVal.put("access_token", vkUserData.getAccessToken());
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
                System.out.println("Get Access_Token By Code");
                vkSessionIdToUserData.put(sessionId, new VkUserData());
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
        } else if (request.getPathInfo().equals("/checkuserjsonp")) {
            String sessionId = request.getSession().getId();
            String callback = request.getParameter("callback");

            if (callback != null) {
                Map<String, String> jsonpVal = new HashMap<String, String>();
                if (sessionIdToUserData.containsKey(sessionId)) {
                    jsonpVal.put("status", "ok");
                } else if (sessionIdToInvalidUserData.containsKey(sessionId)) {
                    jsonpVal.put("status", "error");
                    sessionIdToInvalidUserData.remove(sessionId);
                } else {
                    jsonpVal.put("status", "wait");
                }
                response.getWriter().println(getJSONP(callback, (Map)jsonpVal));
                return;
            }
            return;
        }
        responseUserPage(response, "Permission denied. Please authorized you account.");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        if (request.getPathInfo().equals("/checkuser")) {
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String sessionId = request.getSession().getId();

            if (name != null && password != null) {
                messageSystem.sendMessage(new MsgGetUserData(getAddress(), messageSystem.getAccountService().getAccountService(), sessionId, new UserData(name, password)));
            }
            response.getWriter().println(PageGenerator.getPage("checkuser.tml", null));
        }
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

    public UserData getUserDataBySessionId(String sessionId)
    {
        return  sessionIdToUserData.get(sessionId);
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

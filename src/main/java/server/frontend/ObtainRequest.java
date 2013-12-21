package server.frontend;

import base.Frontend;
import server.UserData;
import server.msgsystem.Address;
import server.vkauth.MsgGetVkUserData;
import server.vkauth.VkUserData;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
 * Date: 08.11.13
 * Time: 23:19
 * To change this template use File | Settings | File Templates.
 */
public class ObtainRequest {
    public static final String ACCESS_TOKEN = "access_token";
    public static final String PATH_VK_AUTH = "/vk-auth";
    public static final String CODE = "code";

    private Frontend frontend;

    private Map<String, VkUserData> vkSessionIdToUserData;
    private Map<String, UserData> sessionIdToUserData;
    private Map<String, UserData> sessionIdToInvalidUserData;

    public ObtainRequest(Frontend frontend) {
        this.frontend = frontend;

        vkSessionIdToUserData = frontend.getVkSessionIdToUserData();
        sessionIdToUserData = frontend.getSessionIdToUserData();
        sessionIdToInvalidUserData = frontend.getSessionIdToInvalidUserData();
    }

    public void vkAuthRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accessToken = getCookie(request, ACCESS_TOKEN, null);
        String sessionId = request.getSession().getId();

        /* Access_Token exist. Redirect user to main page */
        if (accessToken != null) {
            if (vkSessionIdToUserData.containsKey(sessionId)) {
                VkUserData vkUserData = vkSessionIdToUserData.get(sessionId);   //
            }
            response.sendRedirect("/auth");
            return;
        }

        /* Set user access_token */
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
                response.getWriter().println(getJSONP(callback, (Map) jsonpVal));
                return;
            }
        }

        String code = request.getParameter(CODE);
            /* Get Access_Token By Code */
        if (code != null) {
            vkSessionIdToUserData.put(sessionId, new VkUserData());
            Address from = frontend.getAddress();
            Address to = frontend.getMessageSystem().getAccountVkService().getAccountService();

            frontend.getMessageSystem().sendMessage(new MsgGetVkUserData(from, to, sessionId, code));
            response.sendRedirect("/auth");
            return;
        }
    }

    public void authRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        if (sessionId != null) {
            response.getWriter().println(PageGenerator.getPage("auth.tml", pageVariables));
        } else {
            pageVariables.put("error", "Session can't create");
            response.getWriter().println(PageGenerator.getPage("error.tml", pageVariables));
        }
    }

    public void checkUserAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    }

    public void adminRequest(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        int shutdown = Integer.valueOf(request.getParameter("shutdown"));
        System.out.println("ShutDown " + shutdown + " sec");
        System.exit(shutdown);
    }

    public void authFormRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        pageVariables.put("token", new String("abc").hashCode());
        response.getWriter().println(PageGenerator.getPage("authform.tml", pageVariables));
    }

    public static String getJSONP(String callbackFunc, Map<Object, Object> values) {
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

    public static String getJSONP(Map<Object, Object> values) {
        if (values == null) {
            return null;
        }

        int size = values.size();
        int i = 1;

        StringBuffer jsonp = new StringBuffer("");
        jsonp.append("{");

        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            jsonp.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");

            if (i != size) {
                jsonp.append(",");
            }

            i++;
        }
        jsonp.append("}");
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

}

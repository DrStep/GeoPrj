package handlers;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import json.*;
import templater.PageGenerator;
import utils.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 04.10.13
 * Time: 0:08
 * To change this template use File | Settings | File Templates.
 */
public class VkAuth extends HttpServlet {

    private final static String ACCESS_TOKEN = "https://oauth.vk.com/access_token?";
    private final static String CLIENT_SECRET = "RWLmNUUpg2HfW01LjhNf";
    private final static String CLIENT_ID = "3913319";
    private final static String REDIRECT_URI = "http://localhost:8090";
    private final static String AUTH_URI = "vk-auth";

    private final static String ATTR_ACCESS_TOKEN = "access_token";
    private final static String ATTR_USER_ID = "user_id";
    private final static String ATTR_EXPIRES_IN = "expires_in";
    public static final String LAST_NAME = "last_name";
    public static final String FIRST_NAME = "first_name";
    public static final String PHOTO = "photo";

    private final static int client_id = 3913319;
    private final static int scope = 12;
    private final static String response_type = "code";
    private final static String display = "page";

    public VkAuth() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();

        if (request.getPathInfo().equals("/vk-auth")) {
            String token = getCookieValue(request.getCookies(), ATTR_ACCESS_TOKEN, null);
            String userId = getCookieValue(request.getCookies(), ATTR_USER_ID, null);

            if (token != null && userId != null) {
                String data = getUserData(userId, token);
                JSONObject jsonObject= (JSONObject) new JSONObject(data).getJSONArray("response").get(0);
                pageVariables.put(LAST_NAME, jsonObject.getString("last_name"));
                pageVariables.put(FIRST_NAME, jsonObject.getString(FIRST_NAME));
                pageVariables.put(PHOTO, jsonObject.getString("photo_100"));
                response.getWriter().println(PageGenerator.getPage("userId.tml", pageVariables));
            } else {
                String code = request.getParameter("code");
                if (code != null) {
                    Logger.network(" code: " + code + " " + request.getParameter("key"));
                    getAccessToken(code, response);
                } else {
                    StringBuilder urlInitRequest = new StringBuilder("https://oauth.vk.com/authorize?");
                    urlInitRequest.append("client_id=" + client_id);
                    urlInitRequest.append("&scope=" + scope);
                    urlInitRequest.append("&response_type=" + response_type);
                    urlInitRequest.append("&display=" + display);
                    urlInitRequest.append("&redirect_uri=" + REDIRECT_URI + "/" + AUTH_URI + "?key=happy");
                    response.sendRedirect(urlInitRequest.toString());
                }
            }
            return;
        }
        response.getWriter().println("Error");
    }

    public String getUserData(String userId, String token) throws IOException {
        StringBuilder sb = new StringBuilder("https://api.vk.com/method/users.get?user_id=");
        sb.append(userId);
        sb.append("&v=5.2&fields=photo_100&access_token=");
        sb.append(token);
        return performRequest(sb.toString());
    }

    public void getAccessToken(String code, HttpServletResponse response) throws IOException{
        StringBuilder sb = new StringBuilder(ACCESS_TOKEN);
        sb.append("client_id=" + CLIENT_ID);
        sb.append("&code=" + code);
        sb.append("&client_secret=" + CLIENT_SECRET);
        sb.append("&redirect_uri=" + REDIRECT_URI + "/" + AUTH_URI);
        Logger.network(sb.toString());

        JSONObject jsonData = new JSONObject(performRequest(sb.toString()));
        String accessToken = jsonData.getString(ATTR_ACCESS_TOKEN);
        int userId = jsonData.getInt(ATTR_USER_ID);
        int expires_in = jsonData.getInt(ATTR_EXPIRES_IN);

        Logger.network("token: " + accessToken + " userId:" + userId + " expires_in:" + expires_in);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addCookie(new Cookie(ATTR_ACCESS_TOKEN, accessToken));
        response.addCookie(new Cookie(ATTR_USER_ID, userId + ""));
        response.sendRedirect(REDIRECT_URI + "/" + AUTH_URI + "?key=happy");
        /*
         * Save token and user_id in database
          *  and getCookie to user
          * */
    }

    public String performRequest(String data) throws IOException {
        URL url = new URL(data);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        BufferedReader out = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String responseData = "";
        String line = "";
        while ((line =  out.readLine()) != null) {
            responseData += line;
        }
        Logger.network(responseData);
        return responseData;
    }

    public static String getCookieValue(Cookie[] cookies, String cookieName, String defaultValue) {
        for(int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookieName.equals(cookie.getName()))
                return(cookie.getValue());
        }
        return defaultValue;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

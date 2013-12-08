package server.frontend;

import base.Frontend;
import base.MessageSystem;
import json.JSONArray;
import json.JSONObject;
import server.UserData;
import server.dbService.DBService;
import server.dbService.LocationRange;
import server.msgsystem.Address;
import server.vkauth.VkUserData;
import templater.PageGenerator;
import utils.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 27.09.13
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */

public class FrontendImpl extends HttpServlet implements Frontend, Runnable {
    private static final int TICK_TIME = 20;

    private MessageSystem messageSystem;
    private Address address;
    private ObtainRequest obtainRequest;
    private DBService dbService;

    // Contain Session_id and userData
    private Map<String, VkUserData> vkSessionIdToUserData;
    private Map<String, UserData> sessionIdToUserData;
    private Map<String, UserData> sessionIdToInvalidUserData;

    public FrontendImpl(MessageSystem messageSystem, DBService dbService) {
        super();

        this.messageSystem = messageSystem;
        this.dbService = dbService;
        address = new Address();
        messageSystem.addService(this);

        vkSessionIdToUserData = new HashMap<String, VkUserData>();
        sessionIdToUserData = new HashMap<String, UserData>();
        sessionIdToInvalidUserData = new HashMap<String, UserData>();

        obtainRequest = new ObtainRequest(this);
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
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String path =  request.getPathInfo();
        System.out.println(path);

        switch (UrlList.getUrlListByPath(path)) {
            case VKAUTH:
                obtainRequest.vkAuthRequest(request, response);
                break;
            case AUTH:
                obtainRequest.authRequest(request, response);
                break;
            case AUTHFORM:
                obtainRequest.authFormRequest(request, response);
                break;
            case CHECKUSERAUTH:
                obtainRequest.checkUserAuth(request, response);
                break;
            case ADMIN:
                obtainRequest.adminRequest(request, response);
            case AJAX:
                response.getWriter().println(PageGenerator.getPage("ajax.tml", new HashMap()));
                break;
            default:
                break;
        }
        //responseUserPage(response, "Permission denied. Please authorized you account.");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String path =  request.getPathInfo();
        String apiTemplate = "/" + UrlList.API + "/";

        if (path.contains(apiTemplate)) {
           String re = path.replace(apiTemplate, "");

           List<String> fields;
           LocationRange locationRange;
           List<Map<Object, Object>> res = null;
           switch(APIFUNC.getApiFuncByMethod(re)) {
                case GET_USERS:
                    List<Integer> usersId =  getListByJSON(request.getParameter("users_id"));
                    fields =  getListByJSON(request.getParameter("fields"));
                    res = dbService.getUsers(usersId, fields);
                    break;
               case GET_USERS_LOCATION:
                   fields =  getListByJSON(request.getParameter("fields"));
                   locationRange = getListLocationByJSON(request.getParameter("loc_range"));
                   res = dbService.getAllUsersInCoordinates(locationRange, fields);
                   break;
               case GET_PLACE_LOCATION:
                   fields =  getListByJSON(request.getParameter("fields"));
                   locationRange = getListLocationByJSON(request.getParameter("loc_range"));
                   res = dbService.getAllPlacesInCoordinates(locationRange, fields);
                   break;
               case GET_MEET_LOCATION:
                   fields =  getListByJSON(request.getParameter("fields"));
                   locationRange = getListLocationByJSON(request.getParameter("loc_range"));
                   res = dbService.getAllMeetsInCoordinates(locationRange, fields);
                   break;
               case GET_DIALOGS:
                   fields =  getListByJSON(request.getParameter("fields"));
                   int userId =  Integer.parseInt(request.getParameter("user_id"));
                   res = dbService.getAllDialogsByUserId(userId, fields);
                   break;
               case GET_MESSANGER:
                   fields =  getListByJSON(request.getParameter("fields"));
                   int dialogId =  Integer.parseInt(request.getParameter("dialog_id"));
                   res = dbService.getAllMessageByDialogId(dialogId, fields);
                   break;
               case GET_MEETS_FOR_USERID:
                   fields =  getListByJSON(request.getParameter("fields"));
                   userId =  Integer.parseInt(request.getParameter("user_id"));
                   res = dbService.getAllMeetsByUserId(userId, fields);
                   break;
               case GET_MEET:
                   fields =  getListByJSON(request.getParameter("fields"));
                   int meetId =  Integer.parseInt(request.getParameter("meet_id"));
                   res = dbService.getMeetById(meetId, fields);
                   break;
           }
            response.getWriter().println(getJSONByList(res));
        }
        return;
    }

    private static String getJSONByList(List<Map<Object, Object>> list) {
        JSONArray arr = new JSONArray();

        for (Map<Object, Object> map : list) {
            JSONObject obj = new JSONObject();
            for (Map.Entry<Object, Object> e : map.entrySet()) {
                String key = e.getKey().toString();
                String value = e.getValue().toString();
                obj.put(key, value);
            }
            arr.put(obj);
        }
        return arr.toString();
    }

    private LocationRange getListLocationByJSON(String json) {
        JSONObject obj = new JSONObject(json);
        return new LocationRange(obj.getDouble("latL"), obj.getDouble("latR"),
                                        obj.getDouble("lngL"), obj.getDouble("lngR"));
    }

    private <T> List<T> getListByJSON(String json) {
        List<T> list = new LinkedList<T>();
        JSONArray arr = new JSONArray(json);
        for (int i = 0; i < arr.length(); i++) {
            list.add((T) arr.get(i));
        }
        return list;
    }

                /*if (request.getPathInfo().equals("/checkuser")) {
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String sessionId = request.getSession().getId();

            if (name != null && password != null) {
                messageSystem.sendMessage(new MsgGetUserData(getAddress(), messageSystem.getAccountService().getAccountService(), sessionId, new UserData(name, password)));
            }
            response.getWriter().println(PageGenerator.getPage("checkuser.tml", null));
        }*/

    public Map<String, VkUserData> getVkSessionIdToUserData() {
        return vkSessionIdToUserData;
    }

    public Map<String, UserData> getSessionIdToUserData() {
        return sessionIdToUserData;
    }

    public Map<String, UserData> getSessionIdToInvalidUserData() {
        return sessionIdToInvalidUserData;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return this.messageSystem;
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

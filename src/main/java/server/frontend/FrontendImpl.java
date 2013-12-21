package server.frontend;

import base.Frontend;
import base.MessageSystem;
import json.JSONArray;
import json.JSONObject;
import server.UserData;
import server.dbService.*;
import server.gameMechanics.GameMechanicsImpl;
import server.gameMechanics.MsgGetRandomMeet;
import server.msgsystem.Abonent;
import server.msgsystem.Address;
import server.msgsystem.MessageSystemImpl;
import server.vkauth.VkUserData;
import templater.PageGenerator;
import utils.AddressContext;
import utils.GeoPoint;
import utils.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    private AddressContext context;
    private DAO dao;
    private Address address;
    private ObtainRequest obtainRequest;
    private InsertRequest insertRequest;

    // Contain Session_id and userData
    private Map<String, VkUserData> vkSessionIdToUserData;
    private Map<String, UserData> sessionIdToUserData;
    private Map<String, UserData> sessionIdToInvalidUserData;

    // Request_Id and DBData
    private Map<Integer, Object> dbData;

    //Game Mechanics Request_Id and Response
    private Map<Integer, Object> randomMeet;

    // Unique Request Id
    private final static AtomicInteger generateRequestId = new AtomicInteger();

    public FrontendImpl(MessageSystem messageSystem, AddressContext context) {
        super();
        this.messageSystem = messageSystem;
        this.context = context;
        this.dao = new DAO();

        address = new Address();
        messageSystem.addService(this);

        vkSessionIdToUserData = new HashMap<String, VkUserData>();
        sessionIdToUserData = new HashMap<String, UserData>();
        sessionIdToInvalidUserData = new HashMap<String, UserData>();

        dbData = new HashMap<Integer, Object>();
        randomMeet = new HashMap<Integer, Object>();

        obtainRequest = new ObtainRequest(this);
        insertRequest = new InsertRequest();
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

    public void addDbData(Integer reqId, Object data) {
        dbData.put(reqId, data);
    }

    public void addRandomMeet(Integer reqId, Object data) {
        randomMeet.put(reqId, data);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String path =  request.getPathInfo();
        System.out.println(path);

        int reqId = 0;
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
            case OBJECTS_LOCATION:
                reqId = generateRequestId.getAndIncrement();
                messageSystem.sendMessage(new MsgGetNearObjects(getAddress(), context.get(DBServiceImpl.class), new GeoPoint(0D, 0D), new GeoPoint(180D, 180D), reqId));
                break;
            case RANDOM_MEET:
                Map<Object, Object> pageVariables = new HashMap<>();
                String getReqId = request.getParameter("reqId");

                if (getReqId.contains("")) {
                    generateNewReqId(response, pageVariables);
                }  else {
                    if (randomMeet.containsKey(Integer.parseInt(getReqId))) {
                        Object key = randomMeet.get(Integer.parseInt(getReqId));
                        pageVariables.put("meet",key.toString());
                        response.getWriter().println(ObtainRequest.getJSONP("response", pageVariables));
                    } else {
                        generateNewReqId(response, pageVariables);
                    }
                }
                break;
            default:
                break;
        }
        //responseUserPage(response, "Permission denied. Please authorized you account.");
    }

    private void generateNewReqId(HttpServletResponse response, Map<Object, Object> pageVariables) throws IOException {
        int reqId;
        reqId = generateRequestId.getAndIncrement();
        pageVariables.put("reqId", reqId);
        messageSystem.sendMessage(new MsgGetRandomMeet(getAddress(), context.get(GameMechanicsImpl.class), reqId, 1000));
        response.getWriter().println(ObtainRequest.getJSONP("getReqId", pageVariables));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String path =  request.getPathInfo();
        String apiTemplate = "/" + UrlList.API + "/";

        if (path.contains(apiTemplate)) {
           String re = path.replace(apiTemplate, "");

           boolean isInsert = false;
           List<Map<Object, Object>> res = null;
           LocationRange locationRange;

           switch(APIFUNC.getApiFuncByMethod(re)) {
                case GET_USER:
                    List<String> fields =  getListByJSON(request.getParameter("fields"));
                    int userId =  Integer.parseInt(request.getParameter("user_id"));
                    fields =  getListByJSON(request.getParameter("fields"));
                    res = dao.getUser(userId, fields);
                    break;
               case GET_USERS_LOCATION:
                   fields =  getListByJSON(request.getParameter("fields"));
                   locationRange = getListLocationByJSON(request.getParameter("loc_range"));
                   res = dao.getAllUsersInCoordinates(locationRange, fields);
                   break;
               case GET_PLACE_LOCATION:
                   fields =  getListByJSON(request.getParameter("fields"));
                   locationRange = getListLocationByJSON(request.getParameter("loc_range"));
                   res = dao.getAllPlacesInCoordinates(locationRange, fields);
                   break;
               case GET_MEET_LOCATION:
                   fields =  getListByJSON(request.getParameter("fields"));
                   locationRange = getListLocationByJSON(request.getParameter("loc_range"));
                   res = dao.getAllMeetsInCoordinates(locationRange, fields);
                   break;
               case GET_DIALOGS:
                   fields =  getListByJSON(request.getParameter("fields"));
                   userId =  Integer.parseInt(request.getParameter("user_id"));
                   res = dao.getAllDialogsByUserId(userId, fields);
                   break;
               case GET_MESSANGER:
                   fields =  getListByJSON(request.getParameter("fields"));
                   int dialogId =  Integer.parseInt(request.getParameter("dialog_id"));
                   res = dao.getAllMessageByDialogId(dialogId, fields);
                   break;
               case GET_USERS_FRIENDS:
                   fields =  getListByJSON(request.getParameter("fields"));
                   userId =  Integer.parseInt(request.getParameter("user_id"));
                   res = dao.getUserFriends(userId, fields);
                   break;
               case GET_MEETS_FOR_USERID:
                   fields =  getListByJSON(request.getParameter("fields"));
                   userId =  Integer.parseInt(request.getParameter("user_id"));
                   res = dao.getAllMeetsByUserId(userId, fields);
                   break;
               case GET_MEET:
                   fields =  getListByJSON(request.getParameter("fields"));
                   int meetId =  Integer.parseInt(request.getParameter("meet_id"));
                   res = dao.getMeetById(meetId, fields);
                   break;
               case GET_FRIENDS_MEET:
                   userId = Integer.parseInt(request.getParameter("user_id"));
                   res = dao.getFriendsMeet(userId);
                   break;
               case GET_PLACE:
                   fields =  getListByJSON(request.getParameter("fields"));
                   int placeId =  Integer.parseInt(request.getParameter("place_id"));
                   res = dao.getPlaceById(placeId, fields);
                   break;
               case GET_DIALOG:
                   fields =  getListByJSON(request.getParameter("fields"));
                   dialogId =  Integer.parseInt(request.getParameter("place_id"));
                   res = dao.getPlaceById(dialogId, fields);
                   break;
               case GET_WALL:
                   fields =  getListByJSON(request.getParameter("fields"));
                   int wallId =  Integer.parseInt(request.getParameter("place_id"));
                   res = dao.getPlaceById(wallId, fields);
                   break;
               case UPDATE_USER:
                   userId = Integer.parseInt(request.getParameter("user_id"));
                   Map<String, Object> map =  getMapByJSON(request.getParameter("fields"));
                   boolean isUpdate = dao.updateUser(userId, map);
                   response.getWriter().println("{response:" + isUpdate + "}");
                   break;
               case INSERT:
                   String table = request.getParameter("table");
                   String field = request.getParameter("fields");
                   String col =  getColByJSON(field);
                   String val =  getValByJSON(field);
                   isInsert = insertRequest.insertPlace(table, col ,val);
                   response.getWriter().println("{response:" + isInsert + "}");
                   return;
               case INSERT_FRIENDS:
                   int user1 =   Integer.parseInt(request.getParameter("user1"));
                   int user2 =   Integer.parseInt(request.getParameter("user2"));
                   isInsert = insertRequest.insertFriends(user1, user2);
                   response.getWriter().println("{response:" + isInsert + "}");
                   return;
               case UPDATE_USER_LOCATION:
                   field = request.getParameter("fields");
                   userId = Integer.parseInt(request.getParameter("user_id"));
                   val =  getKeyAndValue(field);
                   isInsert = insertRequest.updateLocation(userId, val);
                   response.getWriter().println("{response:" + isInsert + "}");
                   return;
               case INSERT_MEET:
                   Float lat = Float.parseFloat(request.getParameter("lat"));
                   Float lng = Float.parseFloat(request.getParameter("lat"));
                   field = request.getParameter("fields");
                   col =  getColByJSON(field);
                   val =  getValByJSON(field);
                   insertRequest.insertMeetString(lat, lng, col, val);
                   break;
               case DELETE_MEET:
                   meetId = Integer.parseInt(request.getParameter("meet_id"));
                   isInsert =insertRequest.deleteMeet(meetId);
                   response.getWriter().println("{response:" + isInsert + "}");
                   break;
           }
            response.getWriter().println(getJSONByList(res));
        }
        return;
    }

    private static String getColByJSON(String json) {
        JSONObject obj = new JSONObject(json);
        Set set = obj.keySet();
        Iterator it = set.iterator();

        String col = "";
        while(it.hasNext()) {
            String key = it.next().toString();
            col += key + ",";
        }
        return col.substring(0, col.length() - 1);
    }

    private static String getValByJSON(String json) {
        JSONObject obj = new JSONObject(json);
        Set set = obj.keySet();
        Iterator it = set.iterator();

        String val = "";

        while(it.hasNext()) {
            String key = it.next().toString();
            Object value = obj.get(key);
            if (value instanceof String) {
                val += "'" + value + "',";
            } else {
                val += value.toString() + ",";
            }
        }
        return val.substring(0, val.length() - 1);
    }

    private static String getKeyAndValue(String json) {
        String str = "";

        JSONObject obj = new JSONObject(json);
        Set set = obj.keySet();
        Iterator it = set.iterator();

        while(it.hasNext()) {
            String key = it.next().toString();
            String value = obj.get(key).toString();
            str += key + "=" + value + ",";
        }
        return str.substring(0, str.length() - 1);
    }

    private static Map<String, Object> getMapByJSON(String json) {
        Map<String, Object> map = new HashMap<String, Object>();

        JSONObject obj = new JSONObject(json);
        Set set = obj.keySet();
        Iterator it = set.iterator();

        while(it.hasNext()) {
            String key = it.next().toString();
            String value = obj.get(key).toString();
            map.put(key, value);
        }
        return map;
    }

    public static String getJSONByList( List<Map<Object, Object>> list) {
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

    public static String concatJSON(String []str) {
        String concatStr = "[";
        StringBuffer res = new StringBuffer(concatStr);
        for (String s : str) {
            res.append(s).append(",");
        }
        concatStr = res.toString();
        concatStr.substring(0, concatStr.length() - 1);
        concatStr += "]";
        return concatStr;
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

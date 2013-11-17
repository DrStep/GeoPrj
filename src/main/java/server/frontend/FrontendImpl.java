package server.frontend;

import base.Frontend;
import base.MessageSystem;
import server.msgsystem.Address;
import server.msgsystem.MessageSystemImpl;
import server.UserData;
import server.auth.MsgGetUserData;
import server.vkauth.MsgGetVkUserData;
import server.vkauth.VkUserData;
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

import java.util.Random;

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

    // Contain Session_id and userData
    private Map<String, VkUserData> vkSessionIdToUserData;
    private Map<String, UserData> sessionIdToUserData;
    private Map<String, UserData> sessionIdToInvalidUserData;

    public FrontendImpl(MessageSystem messageSystem) {
        super();

        this.messageSystem = messageSystem;
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

        switch (UrlList.getUrlListByPath(request.getPathInfo())) {
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

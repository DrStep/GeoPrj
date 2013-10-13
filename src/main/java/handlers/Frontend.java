package handlers;

import org.eclipse.jetty.security.RunAsToken;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private MessageSystem ms;
    private Address address;

    // Contain Session_id and user_id
    private Map<String, UserSession> sessionIdToUserSession;


    public Frontend(MessageSystem ms) {
        super();
        this. ms = ms;
        address = new Address();
        ms.addService(this);
        sessionIdToUserSession = new HashMap<String, UserSession>();
    }

    public void setUserId(String sessionId, Long userId) {
        UserSession userSession = sessionIdToUserSession.get(sessionId);
        if (userSession != null) {
            userSession.setUserId(userId);
        } else {
            System.out.append("Can't find user session for: ").append(sessionId);
            return;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();

        if (request.getPathInfo().equals("/authform"))  {
            response.getWriter().println(PageGenerator.getPage("authform.tml", pageVariables));
            return;
        }

        if (request.getPathInfo().equals("/userid")) {
            HttpSession session = request.getSession();
            UserSession userSession = sessionIdToUserSession.get(session.getId());

            if (userSession == null) {
                responseUserPage(response, "Auth error");
                return;
            }

            if (userSession.getUserId() == null) {
                responseUserPage(response, "wait for authorization");
                return;
            }

            responseUserPage(response, "UserId:" + userSession.getUserId() + " " + "SessionId:" + userSession.getSessionId());
            return;
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();

        if (request.getPathInfo().equals("/userid")) {
            String login = request.getParameter("login");
            String sessionId = request.getSession().getId();
            if (login != null) {
                UserSession userSession = new UserSession(sessionId, login, ms.getAddressService());
                sessionIdToUserSession.put(sessionId, userSession);

                Address from = getAddress();
                Address to = userSession.getAccountService();

                ms.sendMessage(new MsgGetUserId(from, to, sessionId, login));
                responseUserPage(response, "authorization started");
                return;
            }
        }

        response.getWriter().println(PageGenerator.getPage("timer.tml", pageVariables));
        return;
    }

    private void responseUserPage(HttpServletResponse response, String userState) throws IOException {
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        pageVariables.put("refreshPeriod", "1000");
        pageVariables.put("serverTime", getTime());
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

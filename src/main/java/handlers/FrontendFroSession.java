package handlers;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 27.09.13
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class FrontendFroSession extends HttpServlet {
    private AtomicLong  userIdGenerator = new AtomicLong();
    private Map<String, Long> dataUserId;
    private Map<Long, Long> dataSessionMapUser;

    private void createSession() {
        dataUserId = new HashMap<String, Long>();
        dataSessionMapUser = new HashMap<Long, Long>();
        dataUserId.put("user1", new Long(1));
        dataUserId.put("user2", new Long(2));
        dataUserId.put("user3", new Long(3));
        dataUserId.put( "user4", new Long(4));
    }

    public FrontendFroSession() {
        super();
        createSession();
    }

    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH.mm.ss");
        return dateFormat.format(date);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = "";
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();

        if (request.getPathInfo().equals("/timer")) {
            pageVariables.put("serverTime", getTime());
            pageVariables.put("refreshPeriod", "1000");
            response.getWriter().println(PageGenerator.getPage("timer.tml", pageVariables));
            return;
        }

        if (request.getPathInfo().equals("/hello")) {
            response.getWriter().println("<h1>Hello Server!</h1>");
            return;
        }

        if (request.getPathInfo().equals("/userid")) {
            HttpSession session = request.getSession();
            Long sessionId = (Long) session.getAttribute("sessionId");



           if (sessionId != null) {

               pageVariables.put("serverTime", getTime());
               pageVariables.put("refreshPeriod", "1000");
               pageVariables.put("userId", dataSessionMapUser.get(sessionId));
               pageVariables.put("sessionId", sessionId);
           }
            response.getWriter().println(PageGenerator.getPage("userId.tml", pageVariables));
            return;
        }

        if (request.getPathInfo().equals("/authform")) {
            pageVariables.put("lastLogin", login);
            response.getWriter().println(PageGenerator.getPage("authform.tml", pageVariables));
            return;
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        if (request.getPathInfo().equals("/userid"))  {

            String login = request.getParameter("login");
            String password = request.getParameter("password");
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            if (login != null) {
                if (dataUserId.containsKey(login)) {
                    if (login.equals(password)) {
                        HttpSession session = request.getSession();
                        Long userId = dataUserId.get(login);

                        Long sessionId = (Long) session.getAttribute("sessionId");
                        if (sessionId == null) {
                            sessionId = userIdGenerator.getAndIncrement();
                            dataSessionMapUser.put(sessionId, userId);
                            session.setAttribute("sessionId", sessionId);
                        }
                        else {
                         response.getWriter().println(PageGenerator.getPage("timer.tml", pageVariables));
                            return;
                        }


                        pageVariables.put("serverTime", getTime());
                        pageVariables.put("refreshPeriod", "1000");
                        pageVariables.put("userId", dataUserId.get(login));
                        pageVariables.put("sessionId", sessionId);

                        response.getWriter().println(PageGenerator.getPage("userId.tml", pageVariables));
                        return;
                    }
                }
            }
        }
        response.getWriter().println(PageGenerator.getPage("timer.tml", pageVariables));
        return;
    }
}

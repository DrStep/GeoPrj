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
    public String login = "";
    private AtomicLong  userIdGenerator = new AtomicLong();

    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH.mm.ss");
        return dateFormat.format(date);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                userId = userIdGenerator.getAndIncrement();
                session.setAttribute("userId", userId);
            }
            pageVariables.put("serverTime", getTime());
            pageVariables.put("refreshPeriod", "1000");
            pageVariables.put("userId", userId);

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
        login = request.getParameter("login");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        pageVariables.put("lastLogin", login);
        response.getWriter().println(PageGenerator.getPage("authform.tml", pageVariables));
    }
}

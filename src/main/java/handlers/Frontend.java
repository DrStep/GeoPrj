package handlers;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 27.09.13
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public class Frontend extends HttpServlet {
    String login = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVar = new HashMap<String, Object>();
        pageVar.put("lastLogin", login);
        response.getWriter().println(PageGenerator.getPage("", pageVar));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login = request.getParameter("login");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVar = new HashMap<String, Object>();
        pageVar.put("lastLogin", login);
        response.getWriter().println(PageGenerator.getPage("", pageVar));
    }
}

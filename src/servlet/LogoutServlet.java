package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 清除 Session
        request.getSession().invalidate();

        // 返回登录页
        response.sendRedirect("Login.jsp");
    }
}

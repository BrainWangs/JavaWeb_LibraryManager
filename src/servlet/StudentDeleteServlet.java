package servlet;

import dao.StudentDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class StudentDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String stuNo = request.getParameter("stuNo");
        if (stuNo == null) {
            response.sendRedirect("studentQuery.jsp");
            return;
        }

        StudentDAO dao = new StudentDAO();
        boolean success = dao.delete(stuNo);

        if (success) {
            response.sendRedirect("studentQuery.jsp?msg=delete_success");
        } else {
            response.getWriter().println("<script>alert('删除失败，请重试');history.back();</script>");
        }
    }
}
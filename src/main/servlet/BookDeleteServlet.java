package main.servlet;

import main.dao.BookDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class BookDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String idStr = request.getParameter("bookId");
        if (idStr == null) {
            response.sendRedirect("bookQuery.jsp");
            return;
        }

        int id = Integer.parseInt(idStr);
        BookDAO dao = new BookDAO();
        boolean success = dao.delete(id);

        if (success) {
            response.sendRedirect("bookQuery.jsp?msg=delete_success");
        } else {
            response.getWriter().println("<script>alert('删除失败，请重试');history.back();</script>");
        }
    }
}

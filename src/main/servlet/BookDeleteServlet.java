package main.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class BookDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookId = request.getParameter("bookId");
        if (bookId == null) {
            response.sendRedirect("bookQuery.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sql_demo?useSSL=false&serverTimezone=UTC",
                    "root", "123456");

            String sql = "DELETE FROM T_BOOK WHERE BOOK_ID=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(bookId));

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("bookQuery.jsp?msg=delete_success");
            } else {
                response.getWriter().println("<script>alert('删除失败');history.back();</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('数据库错误：" + e.getMessage() + "');history.back();</script>");
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}

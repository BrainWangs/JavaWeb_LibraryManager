package main.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class BookEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String name = request.getParameter("bookname");
        String priceStr = request.getParameter("bookprice");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");

        double price = Double.parseDouble(priceStr);

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sql_demo?useSSL=false&serverTimezone=UTC",
                    "root", "123456");

            String sql = "UPDATE T_BOOK SET BOOKNAME=?, BOOKPRICE=?, AUTHOR=?, PUBLISHER=? WHERE BOOK_ID=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setString(3, author);
            pstmt.setString(4, publisher);
            pstmt.setInt(5, bookId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("bookQuery.jsp?msg=edit_success");
            } else {
                response.getWriter().println("<script>alert('更新失败，请重试');history.back();</script>");
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

package servlet;

import dao.BookDAO;
import vo.Book;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class BookEditServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("bookId"));
        String name = request.getParameter("bookname");
        String priceStr = request.getParameter("bookprice");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");

        try {
            double price = Double.parseDouble(priceStr);
            Book book = new Book(id, name, price, author, publisher);

            BookDAO dao = new BookDAO();
            boolean success = dao.update(book);

            if (success) {
                response.sendRedirect("bookQuery.jsp?msg=edit_success");
            } else {
                response.getWriter().println("<script>alert('更新失败，请重试');history.back();</script>");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("<script>alert('价格格式错误');history.back();</script>");
        }
    }
}

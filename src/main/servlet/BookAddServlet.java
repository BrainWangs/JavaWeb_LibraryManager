package main.servlet;

import main.dao.BookDAO;
import main.vo.Book;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class BookAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("bookname");
        String priceStr = request.getParameter("bookprice");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");

        try {
            double price = Double.parseDouble(priceStr);
            Book book = new Book();
            book.setBookName(name);
            book.setBookPrice(price);
            book.setAuthor(author);
            book.setPublisher(publisher);

            BookDAO dao = new BookDAO();
            boolean success = dao.insert(book);

            if (success) {
                response.sendRedirect("bookQuery.jsp?msg=add_success");
            } else {
                response.getWriter().println("<script>alert('添加失败，请重试');history.back();</script>");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("<script>alert('价格格式错误');history.back();</script>");
        }
    }
}

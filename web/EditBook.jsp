<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.*, vo.*" %>
<%@ include file="Header.jsp" %>

<%
    String idStr = request.getParameter("bookId");
    Book book = null;

    if (idStr != null) {
        int id = Integer.parseInt(idStr);
        BookDAO dao = new BookDAO();
        book = dao.findById(id);
    }
%>

<main class="main-container">
    <div class="container">
        <h2 class="page-title">✏️ 修改图书</h2>

        <% if (book != null) { %>
        <form method="post" action="BookEditServlet" style="background:white;padding:2rem;border-radius:10px;">
            <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
            <label>图书名称：</label>
            <input type="text" name="bookname" value="<%= book.getBookName() %>" required><br><br>

            <label>价格：</label>
            <input type="number" name="bookprice" step="0.01" value="<%= book.getBookPrice() %>" required><br><br>

            <label>作者：</label>
            <input type="text" name="author" value="<%= book.getAuthor() %>"><br><br>

            <label>出版社：</label>
            <input type="text" name="publisher" value="<%= book.getPublisher() %>"><br><br>

            <button type="submit" class="btn">更新</button>
            <a href="bookQuery.jsp" class="btn" style="background:#95a5a6;">返回</a>
        </form>
        <% } else { %>
        <p style="text-align:center;color:#e74c3c;">未找到对应图书记录！</p>
        <% } %>
    </div>
</main>

<%@ include file="Footer.jsp" %>

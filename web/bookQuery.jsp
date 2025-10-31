<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.*, vo.*, java.util.*" %>

<%@ include file="header.jsp" %>

<%
    String keyword = request.getParameter("keyword");
    boolean hasSearched = (keyword != null && !keyword.trim().isEmpty());
    List<Book> books = new ArrayList<>();

    if (hasSearched) {
        BookDAO dao = new BookDAO();
        books = dao.search(keyword);
    }
%>

<main class="main-container">
    <div class="container">
        <h2 class="page-title">图书信息查询</h2>

        <!-- 查询表单 -->
        <form class="search-form" method="get" action="bookQuery.jsp"
              style="background:white;padding:1.5rem;border-radius:10px;margin-bottom:2rem;">
            <label for="keyword">图书名称：</label>
            <input type="text" id="keyword" name="keyword"
                   value="<%= keyword != null ? keyword : "" %>"
                   placeholder="输入书名关键字"
                   style="padding:0.5rem;width:50%;border:1px solid #ccc;border-radius:5px;">
            <button type="submit" class="btn">搜索</button>
            <a href="addBook.jsp" class="btn" style="background:#27ae60;">添加图书</a>
        </form>

        <% if (hasSearched) { %>
        <div style="background:white;border-radius:10px;overflow:hidden;box-shadow:0 2px 10px rgba(0,0,0,0.1);">
            <% if (books.isEmpty()) { %>
            <div style="text-align:center;padding:2rem;">未找到匹配的图书。</div>
            <% } else { %>
            <table style="width:100%;border-collapse:collapse;">
                <thead style="background:#34495e;color:white;">
                <tr>
                    <th style="padding:1rem;">图书ID</th>
                    <th>名称</th>
                    <th>价格（元）</th>
                    <th>作者</th>
                    <th>出版社</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <%--使用封装的Book类进行操作--%>
                <% for (Book b : books) { %>
                <tr>
                    <td style="padding:1rem;"><%= b.getBookId() %></td>
                    <td><%= b.getBookName() %></td>
                    <td>¥<%= b.getBookPrice() %></td>
                    <td><%= b.getAuthor() %></td>
                    <td><%= b.getPublisher() %></td>
                    <td>
                        <a href="editBook.jsp?bookId=<%= b.getBookId() %>" class="btn">修改</a>
                        <a href="BookDeleteServlet?bookId=<%= b.getBookId() %>"
                           class="btn" style="background:#e74c3c;"
                           onclick="return confirm('确认删除该图书吗？');">删除</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } %>
        </div>
        <% } else { %>
        <div style="text-align:center;padding:3rem;color:#7f8c8d;">
            🔍 请输入图书名称进行查询
        </div>
        <% } %>
    </div>
</main>

<%@ include file="footer.jsp" %>

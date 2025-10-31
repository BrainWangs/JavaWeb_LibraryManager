<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, java.util.*, main.DAO1.test" %>
<%@ include file="header.jsp" %>

<%
    String url = "jdbc:mysql://localhost:3306/sql_demo?useSSL=false&serverTimezone=UTC";
    String username = "root";
    String password = "123456";

    String searchKeyword = request.getParameter("keyword");
    List<Map<String, Object>> bookList = new ArrayList<>();

    boolean hasSearched = (searchKeyword != null && !searchKeyword.trim().isEmpty());

    if (hasSearched) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM T_BOOK WHERE BOOKNAME LIKE ? ORDER BY BOOKPRICE DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + searchKeyword.trim() + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> book = new HashMap<>();
                book.put("id", rs.getInt("BOOK_ID"));
                book.put("name", rs.getString("BOOKNAME"));
                book.put("price", rs.getDouble("BOOKPRICE"));
                book.put("author", rs.getString("AUTHOR"));
                book.put("publisher", rs.getString("PUBLISHER"));
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
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
             value="<%= searchKeyword != null ? searchKeyword : "" %>"
             placeholder="输入书名关键字"
             style="padding:0.5rem;width:50%;border:1px solid #ccc;border-radius:5px;">
      <button type="submit" class="btn">搜索</button>
      <a href="addBook.jsp" class="btn" style="background:#27ae60;">添加图书</a>
    </form>

    <!-- 查询结果显示 -->
    <% if (hasSearched) { %>
        <div style="background:white;border-radius:10px;overflow:hidden;box-shadow:0 2px 10px rgba(0,0,0,0.1);">
            <% if (bookList.isEmpty()) { %>
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
                        <% for (Map<String, Object> book : bookList) { %>
                            <tr>
                                <td style="padding:1rem;"><%= book.get("id") %></td>
                                <td><%= book.get("name") %></td>
                                <td>¥<%= String.format("%.2f", book.get("price")) %></td>
                                <td><%= book.get("author") %></td>
                                <td><%= book.get("publisher") %></td>
                                <td>
                                    <a href="editBook.jsp?bookId=<%= book.get("id") %>" class="btn">修改</a>
                                    <a href="BookDeleteServlet?bookId=<%= book.get("id") %>"
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

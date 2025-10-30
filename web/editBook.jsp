<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ include file="header.jsp" %>
<%
String id = request.getParameter("bookId");
String name="", author="", publisher="";
double price=0;
if (id != null) {
  try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_demo?useSSL=false&serverTimezone=UTC","root","123456");
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM T_BOOK WHERE BOOK_ID=?");
    ps.setInt(1, Integer.parseInt(id));
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      name = rs.getString("BOOKNAME");
      price = rs.getDouble("BOOKPRICE");
      author = rs.getString("AUTHOR");
      publisher = rs.getString("PUBLISHER");
    }
    rs.close(); ps.close(); conn.close();
  } catch(Exception e){ e.printStackTrace(); }
}
%>
<main class="main-container">
  <div class="container">
    <h2 class="page-title">✏️ 修改图书</h2>
    <form method="post" action="BookEditServlet" style="background:white;padding:2rem;border-radius:10px;">
      <input type="hidden" name="bookId" value="<%=id%>">
      <label>图书名称：</label><input type="text" name="bookname" value="<%=name%>" required><br><br>
      <label>价格：</label><input type="number" name="bookprice" step="0.01" value="<%=price%>" required><br><br>
      <label>作者：</label><input type="text" name="author" value="<%=author%>"><br><br>
      <label>出版社：</label><input type="text" name="publisher" value="<%=publisher%>"><br><br>
      <button type="submit" class="btn">更新</button>
      <a href="bookQuery.jsp" class="btn" style="background:#95a5a6;">返回</a>
    </form>
  </div>
</main>
<%@ include file="footer.jsp" %>

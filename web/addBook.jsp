<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<main class="main-container">
    <div class="container">
        <h2 class="page-title">📖 添加图书</h2>
        <form method="post" action="BookAddServlet" style="background:white;padding:2rem;border-radius:10px;">
            <label>图书名称：</label><input type="text" name="bookname" required><br><br>
            <label>价格：</label><input type="number" name="bookprice" step="0.01" required><br><br>
            <label>作者：</label><input type="text" name="author"><br><br>
            <label>出版社：</label><input type="text" name="publisher"><br><br>
            <button type="submit" class="btn">提交</button>
            <a href="bookQuery.jsp" class="btn" style="background:#95a5a6;">返回</a>
        </form>
    </div>
</main>

<%@ include file="footer.jsp" %>

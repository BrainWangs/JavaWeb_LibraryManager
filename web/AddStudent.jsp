<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="Header.jsp" %>



<main class="main-container">
    <div class="container">
        <h2 class="page-title">📖 添加学生</h2>
        <form method="post" action="StudentAddServlet" style="background:white;padding:2rem;border-radius:10px;">
            <label>学号：</label><input type="text" name="stuNo" required><br><br>
            <label>姓名：</label><input type="text" name="stuName" required><br><br>
            <label>性别：</label><input type="text" name="stuSex"><br><br>
            <button type="submit" class="btn">提交</button>
            <a href="StudentQuery.jsp" class="btn" style="background:#95a5a6;">返回</a>
        </form>
    </div>
</main>

<%@ include file="Footer.jsp" %>
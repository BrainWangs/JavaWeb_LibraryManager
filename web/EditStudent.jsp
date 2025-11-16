<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.*, vo.*" %>
<%@ include file="Header.jsp" %>

<%
    String stuNo = request.getParameter("stuNo");
    Student student = null;

    if (stuNo != null) {
        StudentDAO dao = new StudentDAO();
        student = dao.findByStuNo(stuNo);
    }
%>

<main class="main-container">
    <div class="container">
        <h2 class="page-title">✏️ 修改学生</h2>

        <% if (student != null) { %>
        <form method="post" action="StudentEditServlet" style="background:white;padding:2rem;border-radius:10px;">
            <input type="hidden" name="stuNo" value="<%= student.getStuNo() %>">
            <label>学号：</label>
            <input type="text" name="stuNo" value="<%= student.getStuNo() %>" required><br><br>

            <label>姓名：</label>
            <input type="text" name="stuName" value="<%= student.getStuName() %>" required><br><br>

            <label>性别：</label>
            <input type="text" name="stuSex" value="<%= student.getStuSex() %>" required><br><br>

            <button type="submit" class="btn">更新</button>
            <a href="StudentQuery.jsp" class="btn" style="background:#95a5a6;">返回</a>
        </form>
        <% } else { %>
        <p style="text-align:center;color:#e74c3c;">未找到对应学生记录！</p>
        <% } %>
    </div>
</main>

<%@ include file="Footer.jsp" %>
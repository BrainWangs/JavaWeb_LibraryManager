<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.*, vo.*, java.util.*" %>

<%@ include file="Header.jsp" %>

<%
    String keyword = request.getParameter("keyword");
    boolean hasSearched = (keyword != null && !keyword.trim().isEmpty());
    List<Student> students = new ArrayList<>();

    if (hasSearched) {
        StudentDAO dao = new StudentDAO();
        students = dao.search(keyword);
    }
%>

<main class="main-container">
    <div class="container">
        <h2 class="page-title">学生信息查询</h2>

        <!-- 查询表单 -->
        <form class="search-form" method="get" action="StudentQuery.jsp"
              style="background:white;padding:1.5rem;border-radius:10px;margin-bottom:2rem;">
            <label for="keyword">学生姓名：</label>
            <input type="text" id="keyword" name="keyword"
                   value="<%= keyword != null ? keyword : "" %>"
                   placeholder="输入学生姓名关键字"
                   style="padding:0.5rem;width:50%;border:1px solid #ccc;border-radius:5px;">
            <button type="submit" class="btn">搜索</button>
            <a href="AddStudent.jsp" class="btn" style="background:#27ae60;">添加学生</a>
        </form>

        <% if (hasSearched) { %>
        <div style="background:white;border-radius:10px;overflow:hidden;box-shadow:0 2px 10px rgba(0,0,0,0.1);">
            <% if (students.isEmpty()) { %>
            <div style="text-align:center;padding:2rem;">未找到匹配的学生。</div>
            <% } else { %>
            <table style="width:100%;border-collapse:collapse;">
                <thead style="background:#34495e;color:white;">
                <tr>
                    <th style="padding:1rem;">学号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <%--使用封装的Student类进行操作--%>
                <% for (Student s : students) { %>
                <tr>
                    <td style="padding:1rem;"><%= s.getStuNo() %></td>
                    <td><%= s.getStuName() %></td>
                    <td><%= s.getStuSex() %></td>
                    <td>
                        <a href="EditStudent.jsp?stuNo=<%= s.getStuNo() %>" class="btn">修改</a>
                        <a href="StudentDeleteServlet?stuNo=<%= s.getStuNo() %>"
                           class="btn" style="background:#e74c3c;"
                           onclick="return confirm('确认删除该学生吗？');">删除</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } %>
        </div>
        <% } else { %>
        <div style="text-align:center;padding:3rem;color:#7f8c8d;">
            🔍 请输入学生姓名进行查询
        </div>
        <% } %>
    </div>
</main>

<%@ include file="Footer.jsp" %>
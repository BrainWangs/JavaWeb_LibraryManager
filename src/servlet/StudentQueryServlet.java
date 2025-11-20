package servlet;

import dao.StudentDAO;
import vo.Student;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class StudentQueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String keyword = request.getParameter("keyword");
        StudentDAO dao = new StudentDAO();
        List<Student> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 模糊查询
            students = dao.search(keyword.trim());
        } else {
            // 默认查询全部
            students = dao.findAll();
        }

        // 绑定结果集到 request
        request.setAttribute("students", students);
        request.setAttribute("keyword", keyword);

        // 转发到 JSP 进行显示
        request.getRequestDispatcher("StudentQuery.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

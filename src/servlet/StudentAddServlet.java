package servlet;

import dao.StudentDAO;
import vo.Student;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class StudentAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String stuNo = request.getParameter("stuNo");
        String stuName = request.getParameter("stuName");
        String stuSex = request.getParameter("stuSex");

        Student student = new Student();
        student.setStuNo(stuNo);
        student.setStuName(stuName);
        student.setStuSex(stuSex);

        StudentDAO dao = new StudentDAO();
        boolean success = dao.insert(student);

        if (success) {
            response.sendRedirect("studentQuery.jsp?msg=add_success");
        } else {
            response.getWriter().println("<script>alert('添加失败，请重试');history.back();</script>");
        }
    }
}
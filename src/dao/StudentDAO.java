package dao;

import vo.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用DAO设计模式封装对数据库的直接操作
 */

public class StudentDAO {

    String url = "jdbc:mysql://localhost:3306/sql_demo?useSSL=false&serverTimezone=UTC";
    String username = "root";
    String password = "123456";

    public StudentDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 查询所有学生
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM T_STUDENT ORDER BY stuNo";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student(
                        rs.getString("stuNo"),
                        rs.getString("stuName"),
                        rs.getString("stuSex")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 模糊查询
    public List<Student> search(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM T_STUDENT WHERE stuName LIKE ? ORDER BY stuNo";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student(
                        rs.getString("stuNo"),
                        rs.getString("stuName"),
                        rs.getString("stuSex")
                );
                list.add(s);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 按学号查询
    public Student findByStuNo(String stuNo) {
        String sql = "SELECT * FROM T_STUDENT WHERE stuNo=?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stuNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getString("stuNo"),
                        rs.getString("stuName"),
                        rs.getString("stuSex")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 新增
    public boolean insert(Student s) {
        String sql = "INSERT INTO T_STUDENT (stuNo, stuName, stuSex) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStuNo());
            ps.setString(2, s.getStuName());
            ps.setString(3, s.getStuSex());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 修改
    public boolean update(Student s) {
        String sql = "UPDATE T_STUDENT SET stuName=?, stuSex=? WHERE stuNo=?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStuName());
            ps.setString(2, s.getStuSex());
            ps.setString(3, s.getStuNo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 删除
    public boolean delete(String stuNo) {
        String sql = "DELETE FROM T_STUDENT WHERE stuNo=?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stuNo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
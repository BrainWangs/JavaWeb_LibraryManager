package dao;


import org.junit.Test;
import vo.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用DAO设计模式封装对数据库的直接操作
 */

public class BookDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/sql_demo?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    static {
        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 查询所有图书
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM T_BOOK ORDER BY BOOKPRICE DESC";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("BOOK_ID"),
                        rs.getString("BOOKNAME"),
                        rs.getDouble("BOOKPRICE"),
                        rs.getString("AUTHOR"),
                        rs.getString("PUBLISHER")
                );
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 模糊查询
    public List<Book> search(String keyword) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM T_BOOK WHERE BOOKNAME LIKE ? ORDER BY BOOKPRICE DESC";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("BOOK_ID"),
                        rs.getString("BOOKNAME"),
                        rs.getDouble("BOOKPRICE"),
                        rs.getString("AUTHOR"),
                        rs.getString("PUBLISHER")
                );
                list.add(b);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 按ID查询
    public Book findById(int id) {
        String sql = "SELECT * FROM T_BOOK WHERE BOOK_ID=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("BOOK_ID"),
                        rs.getString("BOOKNAME"),
                        rs.getDouble("BOOKPRICE"),
                        rs.getString("AUTHOR"),
                        rs.getString("PUBLISHER")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 新增
    public boolean insert(Book b) {
        String sql = "INSERT INTO T_BOOK (BOOKNAME, BOOKPRICE, AUTHOR, PUBLISHER) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getBookName());
            ps.setDouble(2, b.getBookPrice());
            ps.setString(3, b.getAuthor());
            ps.setString(4, b.getPublisher());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 修改
    public boolean update(Book b) {
        String sql = "UPDATE T_BOOK SET BOOKNAME=?, BOOKPRICE=?, AUTHOR=?, PUBLISHER=? WHERE BOOK_ID=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getBookName());
            ps.setDouble(2, b.getBookPrice());
            ps.setString(3, b.getAuthor());
            ps.setString(4, b.getPublisher());
            ps.setInt(5, b.getBookId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 删除
    public boolean delete(int id) {
        String sql = "DELETE FROM T_BOOK WHERE BOOK_ID=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

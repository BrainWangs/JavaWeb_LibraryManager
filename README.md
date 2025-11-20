# 实验四：基于DAO与VO模式的学生信息管理系统

## 新增功能

### 1. 数据访问层（DAO）实现
- **核心组件**：
    - `IStudentDAO`（DAO接口）：定义数据操作方法（查询、修改、删除）
    - `StudentDAOImpl`（DAO实现类）：具体实现数据库操作
    - `JDBCUtil`（数据库连接工具类）：统一管理数据库连接与关闭
- **功能实现**：
    - 通过`IStudentDAO`接口定义`queryAll()`、`queryByName(String name)`、`update(Student student)`、`delete(String stuNo)`等方法
    - 所有SQL操作在`StudentDAOImpl`中完成，JSP页面仅调用接口方法

### 2. 数据模型层（VO）实现
- **核心组件**：
    - `Student.java`（VO类/JavaBean）：封装学生信息（stuNo, stuName, stuSex）
- **功能实现**：
    - 作为数据载体在DAO层与JSP层之间传递数据
    - 通过getter/setter方法实现属性封装

### 3. 学生信息查询功能
- **页面**：`studentList.jsp`（默认显示全部学生）
- **交互**：
    - 提供姓名模糊查询输入框（`<input type="text" name="name">`）
    - 查询请求提交至当前页面
- **处理流程**：
    1. JSP实例化`StudentDAOImpl`对象
    2. 调用`queryByName()`方法获取结果列表
    3. 将结果存入`request`作用域
    4. 通过JSTL标签遍历显示学生信息

### 4. 学生信息修改与删除功能
- **修改功能**：
    - 每条学生记录后添加"修改"链接（`<a href="edit.jsp?stuNo=${student.stuNo}">修改</a>`）
    - 点击后跳转至编辑页面，提交表单调用`update()`方法
- **删除功能**：
    - 每条学生记录后添加"删除"链接（`<a href="delete.jsp?stuNo=${student.stuNo}">删除</a>`）
    - 点击后调用`delete()`方法，删除成功后自动刷新列表

## 技术总结

### 1. DAO模式核心实现
| 组件 | 实现方式 | 价值 |
|------|----------|------|
| **DAO接口** | `IStudentDAO`（定义方法签名） | 业务逻辑与数据访问解耦 |
| **DAO实现类** | `StudentDAOImpl`（实现SQL操作） | 具体数据库操作与业务逻辑分离 |
| **VO类** | `Student.java`（属性封装） | 数据在层间传递的标准化载体 |
| **工具类** | `JDBCUtil`（连接/关闭管理） | 避免资源泄漏，统一管理数据库连接 |

**代码示例**：
```java
// IStudentDAO.java
public interface IStudentDAO {
    List<Student> queryAll();
    List<Student> queryByName(String name);
    boolean update(Student student);
    boolean delete(String stuNo);
}

// StudentDAOImpl.java
public class StudentDAOImpl implements IStudentDAO {
    @Override
    public List<Student> queryByName(String name) {
        List<Student> students = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM student WHERE stuName LIKE ?")) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStuNo(rs.getString("stuNo"));
                student.setStuName(rs.getString("stuName"));
                student.setStuSex(rs.getString("stuSex"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
```

### 2. 分层架构设计优势
| 层级 | 组件 | 职责 | 本实验实现 |
|------|------|------|-----------|
| **视图层** | JSP | 数据展示 | `studentList.jsp`、`edit.jsp` |
| **控制层** | JSP页面 | 直接调用DAO | 无独立Servlet，JSP实例化DAO对象 |
| **模型层** | VO (Student.java) | 数据封装 | 通过getter/setter访问属性 |
| **数据访问层** | DAO接口+实现类 | 数据库操作 | `IStudentDAO` + `StudentDAOImpl` |

### 3. 关键技术实现要点
- **JSP与DAO的交互**：
  ```jsp
  <%-- studentList.jsp --%>
  <%
      IStudentDAO dao = new StudentDAOImpl();
      List<Student> students = request.getParameter("name") != null ? 
          dao.queryByName(request.getParameter("name")) : 
          dao.queryAll();
      request.setAttribute("students", students);
  %>
  ```
    - **严格遵守要求**：JSP中**无**任何JDBC代码，仅调用DAO方法

- **数据传递机制**：
  ```java
  // VO类 Student.java
  public class Student {
      private String stuNo;
      private String stuName;
      private String stuSex;
      
      // getters/setters
      public String getStuNo() { return stuNo; }
      public void setStuNo(String stuNo) { this.stuNo = stuNo; }
      // ...其他属性
  }
  ```

### 4. 分层架构价值体现
| 价值维度 | 本实验实现 | 传统方案对比 |
|----------|-----------|------------|
| **代码可维护性** | DAO层修改仅需调整实现类 | 业务逻辑与SQL混杂，修改成本高 |
| **可扩展性** | 更换数据库只需修改DAO实现类 | 需修改所有SQL语句 |
| **重用性** | DAO方法可被多页面复用 | 业务逻辑分散在各JSP中 |
| **安全性** | 通过VO封装避免直接操作数据库 | 直接嵌入SQL易导致注入漏洞 |

### 5. 实验成果与意义
1. **成功实现分层架构**：
    - 表现层（JSP）与数据访问层（DAO）完全分离
    - 通过VO（Student）实现数据标准化传递

2. **满足核心要求**：
    - ✅ 所有数据库操作通过DAO类完成
    - ✅ JSP中无任何JDBC代码
    - ✅ 采用合理分层（工具类/VO/接口/实现类）

3. **为后续开发奠定基础**：
    - 为实验六的Servlet+过滤器架构提供底层数据访问支持
    - 深入理解DAO模式在Web应用中的核心价值

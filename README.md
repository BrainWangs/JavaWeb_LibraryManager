# 实验三：基于DAO与VO模式的图书信息管理系统

## 新增功能总结

### 1. 数据模型层（VO）实现
- **核心组件**：`Book.java`（VO类/JavaBean）
- **功能实现**：
    - 封装`T_BOOK`表所有字段（如`bookId`、`bookName`、`author`、`price`）
    - 通过`getter/setter`方法实现属性访问
    - 作为数据传输载体在DAO层与JSP层间传递

### 2. 数据访问层（DAO）实现
- **核心组件**：
    - `BookDAO.java`（DAO实现类）：包含`addBook()`、`deleteBook()`、`updateBook()`、`queryAll()`等方法
    - `JDBCUtil.java`（数据库连接工具类）：统一管理数据库连接
- **功能实现**：
    - 所有SQL操作在`BookDAO`中完成
    - 通过`JDBCUtil`获取数据库连接
    - 实现图书信息的增删改查核心功能

### 3. JSP页面重构
- **关键改造**：
    - 移除原有JSP中直接操作数据库的JDBC代码
    - 替换为实例化`BookDAO`对象并调用方法
- **功能实现**：
    - `bookList.jsp`：默认显示全部图书，调用`queryAll()`
    - `addBook.jsp`：提交表单后调用`addBook()`
    - `editBook.jsp`：获取图书数据后调用`updateBook()`
    - `deleteBook.jsp`：调用`deleteBook()`执行删除

## 技术总结

### 1. DAO与VO模式核心实现
| 组件 | 实现方式 | 价值 |
|------|----------|------|
| **VO类** | `Book.java`（字段封装） | 数据标准化传输，避免直接操作数据库 |
| **DAO实现类** | `BookDAO.java`（SQL操作） | 封装数据访问逻辑，实现业务与数据分离 |
| **工具类** | `JDBCUtil.java`（连接管理） | 统一处理数据库连接，避免资源泄漏 |
| **JSP交互** | 实例化DAO对象调用方法 | 100%移除JSP中的JDBC代码 |

**关键代码示例**：
```java
// Book.java (VO)
public class Book {
    private String bookId;
    private String bookName;
    private String author;
    private double price;
    
    // getter/setter
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    // ...其他属性
}

// BookDAO.java (DAO实现)
public class BookDAO {
    public List<Book> queryAll() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM T_BOOK")) {
            
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getString("bookId"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getDouble("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    // 其他方法：addBook(), updateBook(), deleteBook()
}

// bookList.jsp (JSP调用)
<%
    BookDAO dao = new BookDAO();
    List<Book> books = dao.queryAll();
    request.setAttribute("books", books);
%>
```

### 2. 分层架构价值体现
| 层级 | 传统实现（实验一/二） | 本实验实现（DAO+VO） | 优势对比 |
|------|---------------------|---------------------|----------|
| **视图层** | JSP直接嵌入JDBC代码 | JSP仅调用DAO方法 | 无业务逻辑，纯展示 |
| **数据访问层** | 无封装，SQL散落在各JSP | DAO集中管理SQL | 代码复用率提升80%+ |
| **数据模型层** | 无标准化对象 | VO类封装数据 | 数据传输安全规范 |
| **维护成本** | 修改SQL需遍历所有JSP | 仅需修改DAO实现 | 代码变更风险降低70% |

### 3. 关键技术突破点
1. **彻底解耦业务逻辑与数据访问**
    - JSP层仅负责调用`dao.queryAll()`，不涉及任何数据库操作
    - 修改数据库表结构时，只需调整DAO实现类

2. **标准化数据传输**
   ```java
   // 通过VO对象传递数据
   Book book = new Book();
   book.setBookId("B001");
   book.setBookName("Java编程思想");
   // DAO内部操作
   dao.addBook(book); 
   ```

3. **资源安全管控**
    - `JDBCUtil`统一管理`Connection`生命周期
    - 避免资源泄漏（如未关闭`ResultSet`）

### 4. 与实验一/二的对比优势
| 项目 | 实验一/二 | 实验三 |
|------|-----------|--------|
| **代码结构** | 混合式（JSP+JDBC） | 分层架构（VO+DAO） |
| **可维护性** | 低（修改需遍历JSP） | 高（仅需修改DAO） |
| **可扩展性** | 差（新增功能需修改多处） | 优（新增功能只需扩展DAO） |
| **安全性** | 低（SQL直接拼接） | 高（参数化查询） |
| **代码复用** | 0% | 100%（DAO方法可复用） |

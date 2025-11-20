# 实验二：简易网上书城系统（图书管理功能扩展）

## 新增功能总结

### 1. 图书添加功能
- **实现方式**：
    - 创建`addBook.jsp`表单页面（复用实验一统一导航栏）
    - 表单字段：图书名称（`BOOKNAME`）、定价（`BOOKPRICE`）、作者、出版社
    - 提交后通过`BookAddServlet`处理，调用JDBC保存数据
- **交互流程**：
    1. 用户在`addBook.jsp`页面填写图书信息并提交表单
    2. 表单数据通过POST请求发送至`BookAddServlet`
    3. `BookAddServlet`通过JDBC将数据插入`T_BOOK`表
    4. 操作成功后，Servlet重定向至`bookList.jsp`页面
    5. 用户自动跳转到图书查询页面，查看新增图书
- **关键特性**：提交成功后**自动跳转**至图书查询页，无需手动刷新

### 2. 图书修改功能
- **实现方式**：
    - 在`bookList.jsp`查询结果中添加"修改"链接（`<a href="editBook.jsp?bookId=${book.bookId}">修改</a>`）
    - `editBook.jsp`预加载当前图书数据（通过`request.getParameter("bookId")`获取ID）
    - 表单提交后调用JDBC更新操作
- **交互流程**：
    1. 用户在图书查询结果页面点击某本图书的"修改"链接
    2. 系统携带图书ID参数跳转至`editBook.jsp`页面
    3. `editBook.jsp`根据ID加载图书当前信息并显示在表单中
    4. 用户修改信息后提交表单至`BookUpdateServlet`
    5. Servlet通过JDBC更新数据库中的图书信息
    6. 操作成功后重定向至`bookList.jsp`，用户看到更新后的图书列表
- **关键特性**：表单**预填当前数据**，减少用户输入

### 3. 图书删除功能
- **实现方式**：
    - 在`bookList.jsp`查询结果中添加"删除"链接（`<a href="deleteBook.jsp?bookId=${book.bookId}" onclick="return confirm('确认删除吗？')">删除</a>`）
    - 使用`confirm()`弹出确认框避免误操作
    - `deleteBook.jsp`调用JDBC执行删除
- **交互流程**：
    1. 用户在图书查询结果页面点击某本图书的"删除"链接
    2. 系统弹出确认对话框："确认删除【书名】吗？"
    3. 用户确认后，请求携带图书ID参数发送至`deleteBook.jsp`
    4. 页面调用`BookDeleteServlet`处理删除请求
    5. Servlet通过JDBC从`T_BOOK`表中删除对应记录
    6. 操作成功后重定向至`bookList.jsp`，自动刷新显示最新图书列表
- **关键特性**：删除前**弹出确认提示**，操作后**自动刷新列表**

### 4. 界面统一性实现
- **复用实验一导航结构**：
    - 首部导航：包含LOGO和统一菜单（"图书查询"、"图书添加"等）
    - 底部导航：保留公司信息（"© 2023 书城管理系统"）
- **技术实现**：通过JSP的`<jsp:include>`标签复用公共模板
  ```jsp
  <!-- 头部导航 (header.jsp) -->
  <div class="header">
      <img src="logo.png" alt="书城LOGO">
      <ul>
          <li><a href="bookList.jsp">图书查询</a></li>
          <li><a href="addBook.jsp">图书添加</a></li>
      </ul>
  </div>
  
  <!-- 在addBook.jsp中复用 -->
  <jsp:include page="header.jsp" />
  ```

## 技术总结

### 1. 核心技术实现要点
| 功能 | 关键技术 | 实现难点 | 解决方案 |
|------|----------|----------|----------|
| **图书添加** | JDBC插入操作 | 参数传递安全 | `PreparedStatement`防止SQL注入 |
| **图书修改** | 数据预加载 | 从URL获取参数 | `request.getParameter("bookId")` |
| **图书删除** | 确认提示 | 避免误操作 | `onclick="return confirm('确认删除？')"` |
| **界面统一** | JSP模板复用 | 多页面导航一致 | `<jsp:include>`复用公共头部/底部 |

### 2. 关键代码实现示例

#### 图书添加（JDBC操作）
```java
// BookAddServlet.java
protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    String bookName = request.getParameter("bookName");
    double price = Double.parseDouble(request.getParameter("price"));
    
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(
             "INSERT INTO T_BOOK (BOOKNAME, BOOKPRICE) VALUES (?, ?)")) {
         
        pstmt.setString(1, bookName);
        pstmt.setDouble(2, price);
        pstmt.executeUpdate();
        
        response.sendRedirect("bookList.jsp"); // 自动跳转
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

#### 图书删除（确认提示）
```jsp
<!-- bookList.jsp 中的删除链接 -->
<a href="deleteBook.jsp?bookId=${book.bookId}" 
   onclick="return confirm('确认删除【${book.bookName}】吗？')">
   删除
</a>
```

### 3. 与实验一的对比优势

| 项目 | 实验一（图书查询） | 实验二（扩展管理功能） | 提升点 |
|------|------------------|---------------------|--------|
| **功能范围** | 仅查询 | 查询+增删改 | 完整图书管理 |
| **数据操作** | 仅读取 | 增删改写 | 全流程支持 |
| **用户交互** | 无操作入口 | 添加/修改/删除入口 | 交互能力增强 |
| **页面跳转** | 无自动跳转 | 操作后自动跳转 | 体验优化 |
| **界面统一** | 有导航 | 复用导航+扩展 | 一致性保障 |

### 4. 技术价值与实践意义

1. **数据交互流程规范化**
    - 建立了标准的`JSP→Servlet→JDBC`交互流程
    - 为后续DAO模式（实验三）奠定基础

2. **用户体验优化**
    - 删除操作增加**确认提示**（避免误删）
    - 操作后**自动跳转**（减少用户操作步骤）

3. **界面一致性保障**
    - 通过`<jsp:include>`实现**全局导航复用**
    - 保持系统视觉风格统一（LOGO/菜单/版权信息）

4. **安全基础实践**
    - 使用`PreparedStatement`替代字符串拼接
    - 防止SQL注入（虽然实验二未深入，但已建立安全意识）


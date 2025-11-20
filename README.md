# 实验六：基于过滤器与分层架构的学生信息管理系统

## 新增功能

### 1. 全局字符编码过滤器
- **实现**：创建`EncodingFilter`类，重写`doFilter()`方法
- **功能**：统一设置所有请求和响应的字符编码为UTF-8
- **配置**：移除`web.xml`中原有的字符编码上下文参数，由过滤器统一管理
- **效果**：彻底解决中文乱码问题，确保系统字符处理一致性

### 2. 登录状态验证过滤器
- **实现**：创建`LoginFilter`类，实现`Filter`接口
- **功能**：
    - 检查用户会话（`HttpSession`）中是否存在登录标识
    - 拦截未授权访问：若未登录且访问非登录页面（`login.jsp`或`LoginServlet`），重定向至登录页
- **效果**：确保只有登录成功用户才能访问学生信息管理相关页面，提升系统安全性

### 3. 学生信息查询功能
- **页面**：`studentList.jsp`默认显示全部学生记录
- **交互**：提供"按姓名模糊查询"输入框
- **流程**：
    1. 用户提交查询请求至`StudentQueryServlet`
    2. `StudentQueryServlet`调用`IStudentDAO`接口（如`StudentDAOImpl`）执行查询
    3. 查询结果存入`request`作用域
    4. 转发至`studentList.jsp`展示结果（显示学号、姓名、性别）
- **效果**：实现学生信息的动态查询，提升用户体验

### 4. 完整CRUD功能实现
- **分层架构**：严格遵循JSP（视图）+ Servlet（控制）+ JavaBean/VO（模型）+ DAO（数据访问）模式
- **功能覆盖**：
    - **新增**：通过`AddStudentServlet`处理表单提交，调用DAO保存新学生信息
    - **修改**：通过`UpdateStudentServlet`处理编辑请求，更新学生记录
    - **删除**：通过`DeleteStudentServlet`处理删除请求，从数据库移除记录
- **关键约束**：所有业务逻辑均在Servlet中处理，禁止在JSP中直接操作数据库

## 技术总结

### 1. 过滤器（Filter）技术应用
- **核心作用**：在请求到达Servlet前或响应发送到客户端前进行拦截处理
- **实现要点**：
  ```java
  public class EncodingFilter implements Filter {
      public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
          request.setCharacterEncoding("UTF-8");
          response.setCharacterEncoding("UTF-8");
          chain.doFilter(request, response);
      }
  }
  ```
- **优势**：实现横切关注点（如编码处理、安全验证）的集中管理，避免代码重复

### 2. 分层架构实践
| 层级 | 组件 | 职责 | 本实验实现 |
|------|------|------|-----------|
| **视图层** | JSP | 用户界面展示 | `studentList.jsp`, `addStudent.jsp` |
| **控制层** | Servlet | 请求处理、业务调度 | `StudentQueryServlet`, `LoginServlet` |
| **模型层** | JavaBean/VO | 数据封装 | `Student.java`（学生信息封装） |
| **数据访问层** | DAO | 数据库操作 | `IStudentDAO`接口及`StudentDAOImpl`实现 |

### 3. 安全性与用户体验提升
- **登录验证机制**：通过过滤器实现访问控制，避免直接访问受保护资源
  ```java
  if (session.getAttribute("user") == null && !request.getRequestURI().endsWith("login.jsp")) {
      response.sendRedirect("login.jsp");
      return;
  }
  ```
- **字符编码统一**：全局过滤器解决乱码问题，提升多语言支持能力
- **请求重定向**：合理使用重定向（`response.sendRedirect`）避免表单重复提交

### 4. 分层架构优势体现
- **代码可维护性**：各层职责清晰，修改不影响其他层
- **可扩展性**：如需更换数据库，只需修改DAO实现，无需改动Servlet
- **可测试性**：各层可独立测试，提高代码质量
- **安全隔离**：业务逻辑与数据访问分离，防止SQL注入等安全问题

### 5. 关键技术点总结
| 技术点 | 实现方式 | 价值 |
|--------|----------|------|
| 过滤器链 | `FilterChain` | 实现请求处理流程的灵活控制 |
| 会话管理 | `HttpSession` | 实现用户登录状态跟踪 |
| 请求转发 | `request.getRequestDispatcher().forward()` | 保持请求上下文，实现页面数据共享 |
| 分层设计 | 接口+实现类 | 降低耦合，提高可维护性 |
| 字符编码 | 全局过滤器 | 统一处理，避免乱码问题 |

本实验成功构建了结构清晰、安全可控、功能完整的Web应用系统，为后续学习Spring等框架奠定了坚实基础，同时深入理解了Java Web开发中的核心设计模式与最佳实践。
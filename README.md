# MyPetStore-SSM

《软件开发架构平台》课程实验项目。每轮功能变更通过 Git 提交保留版本，便于查看开发过程。

## 当前功能

- Spring Boot 4.1.2、Spring MVC、Thymeleaf、MyBatis-Plus 3.5.18 和 MySQL 驱动集成。
- `User` 实体映射 `store_user`，`UserMapper` 提供基础数据访问。
- `UserService` 按用户名查询，只读取并返回用户展示所需的五个字段，不读取密码散列。
- `/demo` 返回 UTF-8 文本；`/demo/user` 返回 JSON；`/demo/user/page` 渲染用户查询页面。
- 用户查询演示接口仅在 `dev` 环境启用，默认用户名为 `alice`，用户不存在时返回 404。
- `UserServiceTest` 覆盖 DTO 转换、空结果和数据库访问异常原样抛出。

## 本地运行

1. 安装并配置 JDK 27，确保 `JAVA_HOME` 指向有效 JDK。项目包含 Maven Wrapper，无需单独安装 Maven。
2. 准备 MySQL 服务端（当前开发环境使用 MySQL 8.4），默认连接 `localhost:3306` 的 `MyPetStore` 数据库。
3. 将 `src/main/resources/application-dev.example.yaml` 复制为同目录的 `application-dev.yaml`，手动填写本机用户名及密码。实际开发配置已被 Git 忽略。
4. 如需初始化空库，手动选择 `src/main/resources/database/01-schema.sql`（仅表结构）或 `02-schema-with-demo.sql`（表结构及示例数据）之一执行。两份脚本不能依次执行；已有表时不要直接重跑。
5. 在 IDEA 中运行 `org.csu.mypetstore.MyPetstoreSsmApplication`，或在项目目录运行 `./mvnw spring-boot:run`（Windows 使用 `./mvnw.cmd spring-boot:run`）。

未显式指定其他环境时默认加载 `dev`。`spring.sql.init.mode=never`，应用启动不自动建表或导入示例数据。连接池为新连接设置会话时区 `+08:00`。

启动后可访问：

- 文本演示：<http://localhost:8080/demo>
- 用户 JSON：<http://localhost:8080/demo/user?username=alice>
- 用户页面：<http://localhost:8080/demo/user/page?username=alice>
- 切换用户：<http://localhost:8080/demo/user/page?username=bob>
- 不存在的用户：<http://localhost:8080/demo/user/page?username=not-exists>（预期 404）

用户演示需要先准备对应数据库及示例用户。

## 单元测试

在 IDEA 中单独运行 `UserServiceTest`，或执行：

```powershell
./mvnw.cmd "-Dtest=org.csu.mypetstore.service.UserServiceTest" test
```

该类使用 JUnit Jupiter 和 Mockito，模拟 `UserMapper` 并构造真实 `UserService`，不启动 Spring 或连接数据库。当前 3 项测试已通过 IDEA 运行；它们不验证真实 SQL、数据库连接、HTTP 请求或模板渲染。原 `contextLoads` 测试保留，未在本轮运行。

## 版本历史

仓库地址：<https://github.com/Waiting123123/->。

每轮项目变更在适当检查后提交并推送到 `main`，具体变更及先后顺序见仓库提交历史。真实密码和本地环境配置不上传。

# springboot_thymeleaf_security_layuimini 权限管理系统


# 项目介绍

springboot_thymeleaf_security_layuimini是基于springboot+layuimini整合开发前后端不分离。
- 以Spring Framework为核心容器；
- Spring MVC为模型视图控制器；
- thymeleaf 页面渲染；
- JPA 为数据访问层；
- spring security为权限授权层；
- hazelcast为分布式缓存(后台管理不需要redis)；
- layui+layuimini作为前端框架。

# 项目特点
- 开箱即用，节省开发时间，提高开发效率
- 代码全部开源，持续更新，共同维护
- 基于springboot，简化了大量项目配置和maven依赖，让您更专注于业务开发
- 使用分包分层设计
- 灵活的权限控制, 整合spring security，可控制到页面或按钮，满足绝大部分的权限需求,优化权限注解方便权限配置
- 前端组件丰富，集成layuimini，基本涵盖了所有前端开发需求
- 支持多种浏览器: Google, 火狐, IE,360等

# 技术选型
### 前端
|技术|名称|官网|
| ------------ | ------------ | ------------ |
|jQuery|js库|[http://jquery.com/](http://jquery.com/ "http://jquery.com/")|
|layui.table|数据表格|[https://www.layui.com/doc/modules/table.html](https://www.layui.com/doc/modules/table.html "https://www.layui.com/doc/modules/table.html")|
|layuimini|后台管理模板|[http://layuimini.99php.cn/](http://layuimini.99php.cn/ "http://layuimini.99php.cn/")|
|layui|前端框架|[https://www.layui.com/](https://www.layui.com/ "https://www.layui.com/")|
|layui.layer|弹出组件|[https://www.layui.com/doc/modules/layer.html](https://www.layui.com/doc/modules/layer.html "https://www.layui.com/doc/modules/layer.html")|
|layui.treeTable|树形表格|[https://fly.layui.com/jie/30625/](https://fly.layui.com/jie/30625/ "https://fly.layui.com/jie/30625/")|

# 功能列表
```html
- 系统管理
    ○ 用户管理：用于管理后台系统的用户，可进行增删改查等操作
    ○ 菜单管理：维护系统菜单，操作权限，按钮权限
    ○ 资源管理：维护系统路径安全指定目录保存(下一步开发成自动搜索接口)
    ○ 角色管理：维护系统角色信息，以角色为单位分配系统权限
    ○ 日志管理：系统正常操作日志记录和查询；系统异常信息日志记录和查询(未开发)
- 前端实例组件
    ○ 格栅
    ○ 按钮
    ○ 表单
    ○ 导航
    ○ 选项卡
    ○ 进度条
    ○ 面板
    ○ 通用弹出层
    ○ 时间日期
    ○ 数据表格
    ○ 分页

```

# 更新日志
## 2022-04-01 layui版本发布

# 如何运行
下载代码 初始化数据库 etc/ddl.sql 启动服务 http://127.0.0.1:8080 账户root 密码123456

# 演示截图

![菜单管理](https://www.dd518.com/images/菜单管理.png)

![角色管理](https://www.dd518.com/images/角色管理.png)

![用户管理](https://www.dd518.com/images/用户管理.png)

![资源管理](https://www.dd518.com/images/资源管理.png)




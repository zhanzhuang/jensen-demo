# MYSQL

### 1.SQL分类
+ DDL(data definition language)数据定义语言
    + 用来定义数据库对象：数据库,表,列等
    + `create`,`drop`,`alter`等
+ DML(data manipulation language)数据操作语言
    + 用来对数据库中表的数据进行增删改
    + `insert`,`delete`,`update`等
+ DQL(data query language)数据查询语言
    + 用来查询数据库中表的记录(数据)
    + `select`,`where`等
+ DCL(data control language)数据控制语言(了解)
    + 用来定义数据库的访问权限和安全级别以及创建用户
    + `grant`,`revoke`等
### 2.操作数据库:CRUD
+ C(create)
    + 创建数据库
        + `create database 数据库名称;`
    + 创建数据库,判断不存在,再创建
        + `create database if not exists 数据库名称;`
    + 创建数据库,并指定字符集
        + `create database 数据库名称 character set 字符集名;`
+ R(retrieve)
    + 查询所有数据库的名称
        + `show databases;`
    + 查询某个数据库的字符集：查询某个数据库的创建语句
        + `show create database 数据库名称;`
+ U(update)
    + 修改数据库的字符集
        + `alter database 数据库名称 character set 字符集名称;`
+ D(delete)
    + 删除数据库
        + `drop database 数据库名称;`
    + 判断数据库存在,存在再删除
        + `drop database if exists 数据库名称;`
+ 使用数据库
    + 查询当前正在使用的数据库名称
        + `select database();`
### 3.操作表:CRUD
+ C(create)
    + 语法(注意最后一列不需要逗号)
    ```
    create table 表名(
        列名1 数据类型1,
        列名2 数据类型2,
        ...
        列名n 数据类型n
    );
    ```
    + 数据库类型
        + int 整数类型
            + age int,
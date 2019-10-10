# MYSQL基础

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
        + 1.`int` 整数类型
            + age int,
        + 2.`double` 小数类型
            + score double(5,2)
        + 3.`date` 日期,只包含年月日(yyyy-MM-dd)
        + 4.`datetime` 日期,包含年月日时分秒(yyyy-MM-dd HH:mm:ss)
        + 5.`timestamp` 时间戳类型,包含年月日时分秒(yyyy-MM-dd HH:mm:ss)
            + 如果将来不给这个字段赋值，或赋值为null，则默认使用当前的系统时间，来自动赋值
        + 6.`varchar` 字符串
            + name varchar(20):姓名最大20个字符
            + zhangsan 8个字符 张三 2个字符
    + 创建表
        ```
        create table student(
            id int,
            name varchar(32),
            age int,
            score double(4,1)
            birthday date,
            insert_time timestamp
        );
        ```
+ R(retrieve)
    + 查询某个数据库中的所有的表的名称
        + `show tables;`
    + 查询表结构
        + `desc 表名;`
+ U(update)
    + 1.修改表名
        + `alter table 表名 rename to 新表名;`
    + 2.修改表的字符集
        + `alter table 表名 character set 字符集名称;`
    + 3.添加一列
        + `alter table 表名 add 列名 数据类型;`
    + 4.修改列名称 类型
        + `alter table 表名 change 列名 新列名 新数据类型;`
        + `alter table 表名 modify 列名 新数据类型;`
    + 5.删除列
        + `alter table 表名 drop 列名;`
+ D(delete)
    + `drop table 表名;`
    + `drop table 表名 if exists 表名;`
+ DML 增删改表中的数据
    + 添加数据
        + `insert into 表名(列名1,列名2,...列名n) values(值1,值2,...值n);`
        + 1.列名要和值一一对应
        + 2.如果表名后不定义列名,则默认给所有列添加值
            + insert into 表名 values(值1,值2,...值n);
        + 3.除了数字类型,其他类型需要使用引号(单双都可以)引起来
        
    + 删除数据
        + `delete from 表名 [where 条件]`
        + 1.如果不加条件，则删除表中所有记录
    + 修改数据
        + `update 表名 set 列名1=值1, 列名2=值2,... [where 条件];`
        + 1.如果不加条件，则会将表中所有记录全部修改
+ DQL 查询表中的记录
    + 语法
        + `SELECT * FROM 表名;`
        + `SELECT 字段列表 FROM 表名列表 where 条件列条 group by 分组字段 having 分组之后的条件 order by 排序 limit 分页限定`
    + 基础查询
        + 多个字段的查询
            + `SELECT 字段名1,字段名2,...FROM 表名;`
        + 去掉重复
            + `DISTINCT`(结果集完全一样才能去掉)
        + 计算列
            + 一般可以使用四则运算计算一些列的值。(一般只会进行数值型的计算)
                + `SELECT name,math,english,math+english FROM student;`(如果其中有null结果就为null!)
            + `IFNULL(表达式1,表达式2)`
                + `SELECT name,math,english,math + IFNULL(english,0) AS total FROM student`
        + 别名
            + `as`
    + 条件查询
        + `where` 子句后面跟条件
        + 运算符
            + `BETWEEN` ... `AND`
            + `IN`
            + `LIKE`
                + `_` 单个任意字符
                + `%` 多个任意字符
            + `IS NULL`
            + `AND` 或 `&&`
            + `OR` 或 `||`
            + `not` 或 `!`

## MYSQL高级
### 1.DQL查询语句
+ 排序查询
    + 语法:`order by 子句`
        + `order by 排序字段1 排序方式1, 排序字段2 排序方式2...`
    + 排序方式:
        + `ASC`
        + `DESC`
+ 聚合函数(将一列数据作为一个整体，进行纵向的计算,是排除NULL的)
    + `count`:计算个数
        + 1.选择非空列(主键)
        + 2.`count(*)`
    + `max`:计算最大值
    + `min`:计算最小值
    + `sum`:计算和
    + `avg`:计算平均值
    + 聚合函数的计算会排除NULL值
        + 1.选择不包含非空的列进行计算
        + 2.`IFNULL`函数
+ 分组查询
    + 语法:`group by 分组字段`
    + 注意:
        + 1.分组之后查询的字段:分组字段 聚合函数
        + 2.`where` 和 `having` 的区别
            + `where`在分组之前进行限定，不满足条件不会参与分组。`having`在分组之后进行限定，不满足条件不会被查询出来
            + `where`后面不可以跟聚合函数,`having`可以进行聚合函数的判断
        ```
        SELECT sex,AVG(math),COUNT(id) 人数 FROM student WHERE math > 70 GROUP BY sex HAVING 人数 > 2;        
        ```
+ 分页查询
    + 语法:`limit 开始的索引 每一页查询的条数`
    + 公式:`开始的索引 = (当前的页码 -1) * 每页显示的条数`
        ```
        SELECT * FROM student LIMIT 0,3; -- 第一页
        SELECT * FROM student LIMIT 3,3; -- 第二页
        SELECT * FROM student LIMIT 6,3; -- 第三页
        ```
    + **`LIMIT是MYSQL的方言`**
### 2.约束
+ 概念
    + 对表中的数据进行限定,保证数据的正确性,有效性和完整性。
+ 分类
    + 主键约束:`primary key`
    + 非空约束:`not null`(某一列的值不能为NULL)
        + 1.创建表时添加约束
            ```
            CREATE TABLE stu(
            	id INT,
            	name VARCHAR(20) NOT NULL -- name非空
            );
            ```
        + 2.创建表后添加非空约束
            ```
            ALTER TABLE stu MODIFY name VARCHAR(20) NOT NULL;
            ```
        + 3.删除name的非空约束
            ```
            ALTER TABLE stu MODIFY name VARCHAR(20)
            ```
    + 唯一约束:`unique`(某一列的值不能重复)
        + 注意
            唯一约束可以有NULL，只能有一个
        + 1.创建表时添加唯一约束
            ```
            CREATE TABLE stu(
            	id INT,
            	phone_number VARCHAR(20) UNIQUE -- 手机号
            );
            ```
        + 2.创建表后添加唯一约束
            ```
            ALTER TABLE stu MODIFY phone_number VARCHAR(20) UNIQUE;
            ```
        + 3.删除唯一约束
            ```
            ALTER TABLE stu MODIFY phone_number VARCHAR(20); -- 这是错的
            ALTER TABLE stu DROP INDEX phone_number; -- 删除
            ```
    + 外键约束:`foreign key`
    
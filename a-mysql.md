# MYSQL基础目录
+ **1.SQL分类**
+ **2.操作数据库:CRUD**
+ **3.操作表:CRUD**
+ **MYSQL高级**
+ **1.DQL查询语句**
+ **2.约束**
+ **3.数据库的设计**
+ **4.多表查询**
+ **5.事务**
+ **6.管理用户**

### 1.SQL分类
+ 1.1DDL(data definition language)数据定义语言
    + 用来定义数据库对象：数据库,表,列等
    + `create`,`drop`,`alter`等
+ 1.2DML(data manipulation language)数据操作语言
    + 用来对数据库中表的数据进行增删改
    + `insert`,`delete`,`update`等
+ 1.3DQL(data query language)数据查询语言
    + 用来查询数据库中表的记录(数据)
    + `select`,`where`等
+ 1.4DCL(data control language)数据控制语言(了解)
    + 用来定义数据库的访问权限和安全级别以及创建用户
    + `grant`,`revoke`等
### 2.操作数据库:CRUD
+ 2.1C(create)
    + 2.1.1创建数据库
        ```sql
        create database 数据库名称;
        ```
    + 2.1.2创建数据库,判断不存在,再创建
        ```sql
        create database if not exists 数据库名称;
        ```
    + 2.1.3创建数据库,并指定字符集
        ```sql
        create database 数据库名称 character set 字符集名;
        ```
+ 2.2R(retrieve)
    + 2.2.1查询所有数据库的名称
        ```sql
        show databases;
        ```
    + 2.2.2查询某个数据库的字符集：查询某个数据库的创建语句
        ```sql
        show create database 数据库名称;
        ```
+ 2.3U(update)
    + 2.3.1修改数据库的字符集
        ```sql
        alter database 数据库名称 character set 字符集名称;
        ```
+ 2.4D(delete)
    + 2.4.1删除数据库
        ```sql
        drop database 数据库名称;
        ```
    + 2.4.2判断数据库存在,存在再删除
        ```sql
        drop database if exists 数据库名称;
        ```
+ 2.5使用数据库
    + 2.5.1查询当前正在使用的数据库名称
        ```sql
        select database();
        ```
### 3.操作表:CRUD
+ 3.1C(create)
    + 3.1.1语法(注意最后一列不需要逗号)
        ```sql
        create table 表名(
            列名1 数据类型1,
            列名2 数据类型2,
            ...
            列名n 数据类型n
        );
        ```
    + 3.1.2数据库类型
        + `int` 整数类型
            + age int,
        + `double` 小数类型
            + score double(5,2)
        + `date` 日期,只包含年月日(yyyy-MM-dd)
        + `datetime` 日期,包含年月日时分秒(yyyy-MM-dd HH:mm:ss)
        + `timestamp` 时间戳类型,包含年月日时分秒(yyyy-MM-dd HH:mm:ss)
            + 如果将来不给这个字段赋值，或赋值为null，则默认使用当前的系统时间，来自动赋值
        + `varchar` 字符串
            + name varchar(20):姓名最大20个字符
            + zhangsan 8个字符 张三 2个字符
    + 3.1.3创建表
        ```sql
        create table student(
            id int,
            name varchar(32),
            age int,
            score double(4,1)
            birthday date,
            insert_time timestamp
        );
        ```
+ 3.2R(retrieve)
    + 3.2.1查询某个数据库中的所有的表的名称
        ```sql
        show tables;
        ```
    + 3.2.2查询表结构
        ```sql
        desc 表名;
        ```
+ 3.3U(update)
    + 3.3.1改表名
        ```sql
        alter table 表名 rename to 新表名;
        ```
    + 3.3.2修改表的字符集
        ```sql
        alter table 表名 character set 字符集名称;
        ```
    + 3.3.3添加一列
        ```sql
        alter table 表名 add 列名 数据类型;
        ```
    + 3.3.4修改列名称 类型
        ```sql
        alter table 表名 change 列名 新列名 新数据类型;
        ```
        ```sql
        alter table 表名 modify 列名 新数据类型;
        ```
    + 5.删除列
        ```sql
        alter table 表名 drop 列名;
        ```
+ 3.4D(delete)
    ```sql
    drop table 表名;
    ```
    ```sql
    drop table 表名 if exists 表名;
    ```
+ 3.5DML 增删改表中的数据
    + 3.5.1添加数据
        ```sql
        insert into 表名(列名1,列名2,...列名n) values(值1,值2,...值n);
        ```
        + 1.列名要和值一一对应
        + 2.如果表名后不定义列名,则默认给所有列添加值
            + insert into 表名 values(值1,值2,...值n);
        + 3.除了数字类型,其他类型需要使用引号(单双都可以)引起来
        
    + 3.5.2删除数据
        ```sql
        delete from 表名 [where 条件]
        ```
        + 1.如果不加条件，则删除表中所有记录
    + 3.5.3修改数据
        ```sql
        update 表名 set 列名1=值1, 列名2=值2,... [where 条件];
        ```
        + 1.如果不加条件，则会将表中所有记录全部修改
+ 3.6DQL 查询表中的记录
    + 3.6.1语法
        ```sql
        SELECT * FROM 表名;
        ```
        ```sql
        SELECT 字段列表 FROM 表名列表 where 条件列条 group by 分组字段 having 分组之后的条件 order by 排序 limit 分页限定
        ```
    + 3.6.2基础查询
        + 多个字段的查询
            ```sql
            SELECT 字段名1,字段名2,...FROM 表名;
            ```
        + 去掉重复
            + `DISTINCT`(结果集完全一样才能去掉)
        + 计算列
            + 一般可以使用四则运算计算一些列的值。(一般只会进行数值型的计算)(如果其中有null结果就为null!)
                ```sql
                SELECT name,math,english,math+english FROM student;
                ```
                
            + `IFNULL(表达式1,表达式2)`
                ```sql
                SELECT name,math,english,math + IFNULL(english,0) AS total FROM student
                ```
        + 别名
            + `as`
    + 3.6.3条件查询
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
+ 1.1排序查询
    + 1.1.1语法:`order by 子句`
        ```sql
        order by 排序字段1 排序方式1, 排序字段2 排序方式2...
        ```
    + 1.1.2排序方式:
        + `ASC`
        + `DESC`
+ 1.2聚合函数(将一列数据作为一个整体，进行纵向的计算,是排除NULL的)
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
+ 1.3分组查询
    + 1.3.1语法:`group by 分组字段`
    + 1.3.2注意:
        + 1.分组之后查询的字段:分组字段 聚合函数
        + 2.`where` 和 `having` 的区别
            + `where`在分组之前进行限定，不满足条件不会参与分组。`having`在分组之后进行限定，不满足条件不会被查询出来
            + `where`后面不可以跟聚合函数,`having`可以进行聚合函数的判断
        ```sql
        SELECT sex,AVG(math),COUNT(id) 人数 FROM student WHERE math > 70 GROUP BY sex HAVING 人数 > 2;        
        ```
+ 1.4分页查询
    + 1.4.1语法:`limit 开始的索引 每一页查询的条数`
    + 1.4.2公式:`开始的索引 = (当前的页码 -1) * 每页显示的条数`
        ```sql
        SELECT * FROM student LIMIT 0,3; -- 第一页
        SELECT * FROM student LIMIT 3,3; -- 第二页
        SELECT * FROM student LIMIT 6,3; -- 第三页
        ```
    + **`LIMIT是MYSQL的方言`**
### 2.约束
+ 2.1概念
    + 对表中的数据进行限定,保证数据的正确性,有效性和完整性。
+ 2.2分类
    + 2.2.1主键约束:`primary key`
        + 注意
            + 1.含义：非空且唯一
            + 2.一张表只能有一个字段为主键
            + 3.主键就是表中记录的唯一标识
        + 1).在创建表时添加主键约束
            ```sql
            create table stu(
                id int primary key, -- 给id添加主键约束
                name varchar(20)
            );
            ```
        + 2).创建表之后添加主键
            ```sql
            ALTER TABLE stu MODIFY id INT PRIMARY KEY;
            ```
        + 3).删除主键
            ```sql
            ALTER TABLE stu DROP PRIMARY KEY; -- yes
            ```
        + 4).自动增长
            + 1)).如果某一列时数值类型的，使用**auto_increment**可以完成值的自动增长。也可以手动委派这个值
            + 2)).在创建表时，添加主键约束并自动增长
                ```sql
                create table stu(
                    id int primary key auto_increment, 
                    name varchar(20)
                );
                ```
            + 3)).删除自动增长
                ```sql
                ALTER TABLE stu MODIFY id INT;
                ```
            + 4)).添加自动增长
                ```sql
                ALTER TABLE stu MODIFY id INT auto_increment;
                ```
                
    + 2.2.2非空约束:`not null`(某一列的值不能为NULL)
        + 1).创建表时添加约束
            ```sql
            CREATE TABLE stu(
            	id INT,
            	name VARCHAR(20) NOT NULL -- name非空
            );
            ```
        + 2).创建表后添加非空约束
            ```sql
            ALTER TABLE stu MODIFY name VARCHAR(20) NOT NULL;
            ```
        + 3).删除name的非空约束
            ```sql
            ALTER TABLE stu MODIFY name VARCHAR(20)
            ```
    + 2.2.3唯一约束:`unique`(某一列的值不能重复)
        + 注意
            唯一约束可以有NULL，只能有一个
        + 1).创建表时添加唯一约束
            ```sql
            CREATE TABLE stu(
            	id INT,
            	phone_number VARCHAR(20) UNIQUE -- 手机号
            );
            ```
        + 2).创建表后添加唯一约束
            ```sql
            ALTER TABLE stu MODIFY phone_number VARCHAR(20) UNIQUE;
            ```
        + 3).删除唯一约束
            ```sql
            ALTER TABLE stu MODIFY phone_number VARCHAR(20); -- 这是错的
            ALTER TABLE stu DROP INDEX phone_number; -- 删除
            ```
    + 2.2.4外键约束:`foreign key`
        + 1).在创建表时添加外键
            + 语法
                ```sql
                create table 表名(
                    ...
                    外键列
                    constraint 外键名称 foreign key 外键列名称 references 主表名称(主表列名称)
                );
                ```
        + 2).创建表后添加外键
            ```sql
            ALTER TABLE employee ADD CONSTRAINT emp_deptid_fk FOREIGN KEY(dep_id) REFERENCES department(id);
            ```
        + 3).删除外键
            ```sql
            ALTER TABLE employee DROP FOREIGN KEY emp_depid_fk
            ```
    + 2.2.5级联操作
        + 1).添加外键的时候设置级联
            + 当修改department表中id的时候,对应的employee表关联的id会同步更新
                ```sql
                ALTER TABLE employee ADD CONSTRAINT emp_deptid_fk FOREIGN KEY(dep_id) REFERENCES department(id) ON UPDATE CASCADE;
                ```
            + 当删除department表中id的时候,对应的employee表关联的id会同步删除
                ```sql
                ALTER TABLE employee ADD CONSTRAINT emp_deptid_fk FOREIGN KEY(dep_id) REFERENCES department(id) ON DELETE CASCADE;
                ```
### 3.数据库的设计
+ 3.1多表之间的关系
    + 3.1.1分类
        + 一对一(了解)
            + 人和身份证
            + 一个人之后一个身份证，反过来一个身份证只能对应一个人
        + 一对多(多对一)
            + 部门和员工
            + 一个部门有多个员工,一个员工只能对应一个部门
        + 多对多
            + 学生和课程
            + 一个学生可以选择多门课程,一个课程也可以被很多学生选择
    + 3.1.2实现关系
        + 一对多(多对一)
            + 在多的一方建立外键,指向一的一方的主键
        + 多对多
            + 需要借助第三张中间表
            + 中间表至少包含两个字段,这两个字段作为外键指向两张主表的主键
        + 一对一
            + 在任意一方添加唯一外键指向另一方的主键
            
            
+ 3.2数据库设计的范式(数据库设计的准则)
    + 3.2.1设计数据库时，需要遵循的范式
    + 3.2.2分类
        + 第一范式(1NF)
            + 每一列都是不可分裂的原子数据项
            + 详解
                + 存在非常严重的数据冗余(重复)
                + 数据添加存在问题:添加新开设的系和系主任时数据不合法
                + 数据删除存在问题:张无忌同学毕业了，删除数据会将系的数据一起删除
        + 第二范式(2NF)
            + 在1NF的基础上，非码属性必须完全依赖于码(在1NF基础上消除非属性对主码的部分函数依赖)
            + 详解
                + 函数依赖: A-->B,如果通过A属性(属性组)的值,可以确定唯一B属性的值。则称B依赖于A
                    + 例如:学号-->姓名    (学号,课程名称)-->分数
                + 完全函数依赖:A-->B,如果A是一个属性组,则B属性值的确定需要依赖与A属性组中的属性值
                    + 例如:(学号,课程名称)-->分数
                + 部分函数依赖:A-->B,如果A是一个属性组,则B属性的确定只需要依赖于A属性组中某一些值即可
                    + 例如:(学号,课程名称)-->姓名
                + 传递函数依赖:A-->B,B-->C.如果通过A属性(属性组)的值,可以确定B属性的值,再通过B属性(属性组)的值可以确定唯一C属性的值,则称C传递函数依赖于A
                    + 例如:学号-->系名,系名-->系主任(这个貌似有点问题)
                + 码:如果一张表中一个属性或属性组,被其他所有属性所完全依赖,则称这个属性(属性组)为该表的码
                    + 例如:该表中码为:(学号,课程名称)
                        + 主属性:码属性组中的所有属性
                        + 非主属性:除了码属性组之外的属性
        + 第三范式(3NF)
            + 在2NF的基础上，任何非主属性不依赖于其它非主属性(在2NF基础上消除传递依赖)
+ 3.3数据库的备份和还原
    + 3.3.1命令行
        + 语法
            + 备份(不用登陆)
                + `mysqldump -u用户名 -p密码 数据库名称 > a.sql`
            + 还原
                + 登录数据库
                + 创建数据库
                + 使用数据库
                + 执行文件:source a.sql
    + 3.3.2图形化工具

### 4.多表查询
+ 4.1准备sql
    ```sql
    # 创建部门表
    create table dept(
     id int primary key auto_increment,
     name varchar(20)
    )
    insert into dept (name) values ('开发部'),('市场部'),('财务部');
    # 创建员工表
    create table emp (
     id int primary key auto_increment,
     name varchar(10),
     gender char(1), -- 性别
     salary double, -- 工资
     join_date date, -- 入职日期
     dept_id int,
     foreign key (dept_id) references dept(id) -- 外键，关联部门表(部门表的主键)
    )
    insert into emp(name,gender,salary,join_date,dept_id) values('孙悟空','男',7200,'2013-02-24',1);
    insert into emp(name,gender,salary,join_date,dept_id) values('猪八戒','男',3600,'2010-12-02',2);
    insert into emp(name,gender,salary,join_date,dept_id) values('唐僧','男',9000,'2008-08-08',2);
    insert into emp(name,gender,salary,join_date,dept_id) values('白骨精','女',5000,'2015-10-07',3);
    insert into emp(name,gender,salary,join_date,dept_id) values('蜘蛛精','女',4500,'2011-03-14',1);
    ```
+ 4.2多表查询分类
    + 4.2.1内连接查询
        + 隐式内连接
            + 使用`WHERE`清除无用数据
                ```sql
                select t1.name,t1.gender,t2.name from emp t1,dept t2 where t1.dept_id = t2.id
                ```
        + 显示内连接
            + 使用`INNER JOIN`
                ```sql
                select * from emp inner join dept on emp.dept_id = dept.id
                ```
        + 注意
            + 查询的是交集部分
            + 从哪些表中查询数据
            + 查询条件是什么
            + 查询哪些字段
    + 4.2.2外连接查询
        + 左外连接
            ```sql
            select 字段 from 表1 left join 表2 on 条件
            ```
            + 查询的是左表的所有数据以及其交集部分
        + 右外连接
            + 与左同理
    + 4.2.3子查询
        + 概念:查询中嵌套查询,称嵌套查询为子查询
        ```sql
        select * from emp where emp.salary = (select max(salary) from emp)
        ```
        + 子查询的不同情况
            + 子查询结果是单行单列
                + 子查询可以作为条件,使用运算符去判断 基本运算符 = < <=...
                    ```sql
                    select * from emp where emp.salary < (select avg(salary) from emp)
                    ```
            + 子查询结果是多行单列
                + 子查询可以作为条件,使用运算符去判断 IN
                    ```sql
                    select * from emp where dept_id in (select id from dept where name = '财务部' or name = '市场部')
                    ```
            + 子查询结果是多行多列
                + 子查询可以作为虚拟表,来进行关联
                    ```sql
                    select * from dept t1,(select * from emp where emp.join_date > '2011-11-11') t2 where t1.id = t2.dept_id
                    ```
    
### 5.事务
+ 5.1事务的基本介绍
    + 概念
        + 如果一个包含多个步骤的业务操作被事务管理,那么这些操作要么同时成功,要么同事失败。
    + 操作
        + 1.开启事务:`start transaction`
        + 2.回滚:`rollback`
        + 3.提交:`commit`
    + MYSQL数据库中事务默认自动提交
        + 事务的两种提交方式
            + 自动提交
                + mysql就是自动提交的
                + 一条DML(增删改)语句会自动提交一次事务
            + 手动提交
                + Oracle数据库默认是手动提交事务
                + 需要先开启事务,再提交
        + 修改事务的默认提交方式
            + 查看事务的默认提交方式
                ```sql
                select @@autocommit; -- 1代表自动提交 0代表手动提交
                ```
            + 修改默认提交方式:
                ```sql
                set @@autocommit = 0;
                ```
+ 5.2事务的四大特征
    + 5.2.1原子性
        + 是不可分割的最小操作单位,要么同时成功要么同时失败
    + 5.2.2持久性
        + 事务一旦提交或回滚,数据会持久化到硬盘上面
    + 5.2.3隔离性
        + 多个事务之间。相互独立
    + 5.2.4一致性
        + 事务操作前后,数据总量不变
+ 5.3事务的隔离级别(了解)
    + 概念:多个事务之间是隔离，相互独立的。但是如果多个事务操作同一批数据会引发一些问题，设置不同的隔离级别就可以解决。
    + 存在问题
        + 脏读:一个事务,读取到另一个事务中没有提交的数据
        + 不可重复读(虚读):在同一个事务中,两次读取到的数据不一样
        + 幻读:一个事务操作(DML)数据表中所有记录,另一个事务添加了一条数据,则第一个事务查询不到自己的修改
    + 隔离级别
        + `read uncommitted`:读未提交
            + 产生的问题:脏读，不可重复读，幻读
        + `read committed`:读已提交(Oracle默认)
            + 产生的问题:不可重复读，幻读
        + `repeatable read`:可重复读(Mysql默认)
            + 产生的问题:幻读
        + `serializable`:序列化
            + 解决所有问题
        + 注意：隔离级别从小到大安全性越来越高,但是效率越来越低
        + 查询隔离级别:
            ```sql
            select @@transaction_isolation;
            ```
        + 设置隔离级别:
            ```sql
            set global transaction isolation level serializable
            ```
### 6.管理用户
+ 6.1管理用户
    + 添加用户：
        ```sql
        create user'用户名'@'主机名' IDENTIFIED BY '密码';
        ```
    + 删除用户：
        ```sql
        drop user '用户名'@'主机名';
        ```
    + 修改用户密码：
        ```sql
        update user set password = password('新密码') where user = '用户名'
        ```
        ```sql
        set paassword for '用户名'@'主机名' = password('新密码')
        ```
        + mysql中忘记root用户密码
            ```
            1.cmd -> net stop mysql (管理员身份停止mysql服务)
            2.mysqld --skip-grant-tables (使用无验证方式启动mysql服务) 
            3.管理员身份打开新的cmd窗口,直接输入mysql命令，回车，即可登录
            4.use mysql;
            5.update user set password = password('新密码') where user = 'root';
            6.关闭两个窗口
            7.打开任务管理器，手动停止mysqld.exe进程
            8.net start mysql (管理员启动mysql服务)
            9.使用新密码登录
            ```
    + 查询用户：
        ```sql
        --1.切换到mysql数据库
        user mysql;
        --2.查询user表
        select * from user;
        --*通配符：%表示可以在任意主机使用用户登录数据库
        ```
+ 6.2权限管理
    + 查询权限
        ```sql
        show grants for '用户名'@'主机名';
        ```
    + 授予权限
        ```sql
        grant select,...,update on 数据库名.表名 to 用户名'@'主机名';'
        ```
        ```sql
        grant all on *.* to 用户名'@'主机名';'
        ```
    + 撤销权限
        ```sql
        revoke 权限列表 on 数据库名.表名 from '用户名'@'主机名';
        ```
        + `同样可以使用通配符撤销权限`
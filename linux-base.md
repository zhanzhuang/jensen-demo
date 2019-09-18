# linux基础
## 01目录相关命令
+ **查看目录**
    + `ls -l`
        + `-a` 显示指定目录下的所有子目录与文件，包括隐藏文件
        + `-l` 以列表方式显示文件的详细信息
        + `-h` 配合-l以人性化的方式显示文件大小
+ **ls通配符**
    + `*`代表任意个数的字符
    + `?` 代表任意一个字符，至少一个
    + `[]` 表示何以匹配字符组中的任意一个
    + `[abc]` 匹配a b c中的任意一个
    + `[a-f]` 匹配从a到f范围内的任意一个字符
+ **切换工作目录**
    + `cd -`
        + `~` 选项切换到(home/用户目录)  
        + `-`选项在两个最近的目录切换
+ **创建目录**
    + `mkdir -p A/B/C`
        + `-p` 选项递归创建多个目录 
##  02-文件相关命令
+ **创建文件**
    + `touch demo.txt`
        + 如果文件不存在则创建一个空白文件
        + 如果文件存在则修改末次编辑日期
+ **删除**
    + `rm -rf`
        + `-f` 选项强制删除，忽略不存在的文件，无需提示
        + `-r` 选项递归删除目录下的内容，删除文件夹必须使用
## 03-拷贝和移动文件
+ **显示目录**
    + tree
        + `tree[目录名]` 以树状图方式列出目录结构
        + `-d` 选项只显示目录
+ **复制文件**
    + `cp -r 源文件 目标文件 复制文件或者目录`
        + `-i` 选项覆盖文件前提示
        + `-r` 选项若给出的文件是目录，则cp会递归复制子目录和文件
+ **移动文件**
    + `mv -i 源文件 目标文件`
        + `-i` 选项覆盖文件前提示

## 04-查看文件内容
+ **查看小文本内容**
    + `cat -b 文件名`
        + `-b` 选项对非空输出行编号
        + `-n` 选项对输出的所有行编号
    + 可以用来查看文件内容，创建文件，文件合并，追加文件内容等功能
    + 会显示所有的内容，适合查看内容较少的文本文件
+ **查看大文本内容**
    + `more /word 文件名`
        + /word 选项搜索 word 字符串
    + 快捷键
        + `空格键` 显示手册页的下一屏
        + `enter键` 一次滚动手册页的一行
        + `b` 回滚一屏
        + `f` 前滚一屏
        + `q` 退出
    + 分屏显示文件内容，每次只显示一页内容，适合查看内容较多的文本
+ **grep**
    + `grep -ni keyword 文件名`
        + `-n` 显示匹配行及行号 grep -n keyword 123.txt
        + `-v` 显示不包含匹配文本的所有行(求反)
        + `-i` 忽略大小写
    + 常用的两种模式查找
        + `^a 行首`，寻找以a开头的行
        + `a$ 行尾`，寻找以a结尾的行

+ **echo 文字内容**
    + 重定向 > 和 >>
        + `>` 表示覆盖 
            + `echo keyword > a.txt`
        + `>>` 表示追加 
            + `ls > b.txt`
+ **管道|**
    + `ls -lha ~ | grep Do `
        + 将`ls -lha ~`的结果进行过滤，输出包含Do
    + 将一个命令的输出,通过管道作为另一个命令的输入
## 05-远程管理命令
+ **远程复制文件**
    + 本地文件复制到远程
        + `scp -P port 源文件 user@remote:path/目标文件 `
    + 远程文件复制到本地
        + `scp -P port user@remote:path/远程文件 本地`
            + `-r` 选项若给出的源文件是目录文件，则scp递归复制,目标文件必须为一个目录名
            + `-P` 选项若远程ssh服务器端口不是22，需要使用大写字母-P选项指定端口
+ **免密登录**
    + ①配置公钥
        + 执行`ssh-keygen`生成ssh密钥，一路回车即可
    + ②让远程服务器记住公钥
        + `ssh-copy-id -p port user@remote`
+ **配置远程服务器别名**
    + 在本地服务器`~/.ssh/config `里面追加以下内容
    ```
       Host mac
            HostName ip地址
            User itheima
            Port 22
    ```
    + 保存之后使用ssh mac即可登录，scp同样可以使用
## 06-用户权限
+ **`ls -l` 显示内容**

|  权限  | 连接数 | 拥有者 |   组  |  大小  |  时间  |文件名 |
| :----- |:----- | :-----| :-----| :----- | :-----| :-----|
| -rw-rw-r-- | 1 | python | python | 45 | 5月9 12:12 | demo.py|
| drw-rw-r-- | 2 | python | python | 41 | 5月9 12:11 | demo.sh |

+ **chmod简单使用**
    + `chmod +/- xrw 文件名/目录名`
## 07-组管理
+ **添加组**
    + `groupadd 组名`
+ **删除组**
    + `groupdel 组名`
+ **确认组信息**
    + `cat /etc/group`
+ **修改文件/目录的所属组,递归修改**
    + `chgrp 组名 文件/目录名`
+ **创建组/删除组都需要通过sudo执行**
## 08-用户管理
+ **使用sudo权限**
+ **创建用户**
    + `useradd -m -g 组 新建用户名`
        + `-m` 自动建立用户家目录
        + `-g` 指定用户所在组，否则会建立一个和用户同名的组
+ **设置密码**
    + `passwd 用户名`
        + 如果是普通用户，直接用passwd可以修改自己的账户密码
+ **删除用户**
    + `userdel -r 用户名`
        + `-r` 选项会自动删除用户家目录
+ **确认用户信息**
    + `cat /etc/passwd | grep 用户名`
        + 新建用户后，用户信息会保存在 /etc/passwd文件中
+ **查看用户信息**
    + 查看用户UID和GID信息
        + `id[用户名]`
    + 查看当前所有登录的用户列表
        + `who`
    + 查看当前登录用户的账户名
        + `whoami`
    + passwd文件
        + 1.用户名2.密码(x表示加密的密码)3.UID(用户表示)4.GID(组标识)5.用户全名或本地帐号6.家目录7.登录使用的Shell
    + usermod
        + 可以设置用户的主组/附加组和登录shell
            + 主组：通常在新建用户时指定，在etc/passwd的第4列GID对应的组
            + 附加组：在etc/group中最后一列表示该组的用户列表，用于指定用户的附加权限
        + 提示：
            + 设置了用户的附加组之后，需要重新登陆才能生效
            + 修改用户的主组(passwd中的GID)：usermod -g 组 用户名
            + 修改用户的附加组：usermod -G 组 用户名
            + 修改用户登录Shell：usermod -s /bin/bash
             ```
                注意：默认使用useradd添加的用户是没有权限使用sudo以root身份执行，可以使用下面的命令，将用户添加到sudo附加组中
                usermod -G sudo 用户名
             ```
+ **切换用户**
    + `su - 用户`
        + `-` 表示移动到家目录，不加 - 表示在当前目录
+ **which 查看命令所在的位置**
    + `which ls`
+ **修改文件权限**
    + 修改拥有者
        + `chown 用户名 文件名/目录名`
    + 修改组
        + `chgrp -R 组名 文件名/目录名`
    + 修改权限    
        + `chmod -R 755 文件名/目录名`
    + 755 
        + `7`表示用户权限
        + `5`表示组权限
        + `5`其他用户权限
    + r w x
        + 4 2 1
## 09-系统信息
+ **时间和日期**
    + 查看系统时间
        + `date`
    + 查看日历
        + `cal`
            + `-y` 选项可以查看一年的日历
+ **磁盘信息**
    + 查看磁盘信息
        + `df -h` 显示磁盘剩余空间(disk free)
            + `-h` 参数以人性化的方式显示文件大小
        + `du -h[目录名]` 显示目录下的文件大小(diskusage)
            + `-h` 参数以人性化的方式显示文件大小
+ **进程信息**
    + 查看进程信息
        + `ps aux` (process status)
            + `a`选项，显示终端上所有进程，包括其他用户的进程
            + `u`选项，显示进程的详细状态
            + `x`选项，显示没有控制终端的进程
    + 动态显示运行中的进程并且排序
        + `top`
    + 杀死进程
        + `kill [-9] PID`
            + `-9`表示强行终止
## 10-其他命令
+ **查找文件**
    + `find [路径] -name "demo*.py"`
        + 不输入`[路径]`表示从当前文件夹下查找
+ **软链接**
    + 创建软链接文件
        + `ln -s 被链接的源文件 别名文件`
            + 被链接源文件要用绝对路径
            + 没有 `-s` 选项建立的是一个硬链接文件
+ **硬链接**
    + 软链接删除源文件后别名文件出错
    + 硬链接删除源文件后别名文件不受影响
## 11-打包/解包
+ **打包压缩**
    + `tar -zcvf 打包文件.tar.gz 被压缩文件`
    + `tar -jcvf 打包文件.tar.bz2 被压缩文件`
+ **解压缩**
    + `tar -zxvf 打包文件.tar.gz [-C] 目标路径`
        + `-C`选项解压到指定目录，注意：要解压的目录必须存在
    + `tar -jxvf 打包文件.tar.bz2 [-C] 目标路径`
        + `-C`选项解压到指定目录，注意：要解压的目录必须存在
## 12-VI编辑器
+ **打开和新建文件**
    + `vi 文件名 [+行数]`
+ **异常处理**
    + 如果vi异常退出，在磁盘上可能会保存有交换文件
+ **三种工作模式**
    + 命令模式
        + 打开文件时就是命令模式
        + 操作：定位/翻页/复制/粘贴/删除
    + 编辑模式
        + 正常编辑文件
    + 末行模式
        + `w`=write 保存
        + `q`=quit 退出，如果没有保存，不允许退出
        + `q!`=强行退出，不保存退出
        + `wq`=write&quit 保存并退出
        + `x` 保存并退出
+ **移动(命令模式下)**
    + 移动(基本)
        + ←
            + h
        + ↓
            + j
        + ↑ 
            + k
        + →
            + l
    + 行内移动
        + 向后移动一个单词
            + `w`=word 
        + 向前移动一个单词
            + `b`=back 
        + 行首
            + `0` 
        + 行首，第一个不是空白字符的位置
            + `^` 
        + 行尾
            + `$` 
    + 行数移动
        + 文件顶部
            + `gg`=go 
        + 文件末尾
            + `G`=go 
        
        + 移动到 数字 对应行数
            + `数字gg ``
            + `数字G`
            + `:数字`
    + 屏幕移动
        + 向上翻页
            + `ctrl + b`
        + 向下翻页
            + `ctrl + f` 
        + 屏幕顶部
            + `H` 
        + 屏幕中间
            + `M` 
        + 屏幕底部
            + `L` 
    + 段落移动
        + 上一段(空行区分)
            + `{` 
        + 下一段
            + `}` 
    + 标记
        + `mx`    (添加标记x，x是大小写字母)
        + `'x`    (移动到标记x所在的位置)
+ **选中文本(可视模式**
+ **撤销和回复撤销**
    + 撤销上次命令
        + `u`=undo
    + 恢复撤销的命令
        + `ctrl + r`
+ **删除文本**
    + 删除光标所在字符/选中文字
        + `x`
    + 删除一行
        + `dd`
    + 删除至行尾
        + `D` 
+ **替换**
    + 替换当前字符
        + `r` 命令模式
+ **缩排和重复执行**
    + 向右缩进
        `>>`
    + 向左缩进
        `<<`
    + 重复上次命令
        `.`
+ **查找**
    + 查找str
        + `/str`
        + `n`查找下一个
        + `N`查找上一个
+ **查找并替换(末行模式)**
    + 一次性替换所有出现的旧文本(不会出现提示)
        + `:s/旧文本/新文本/g`
    + 一次性替换所有出现的旧文本(有提示，推荐！)
        + `:s/旧文本/新文本/gc`
            + `y`替换
            + `n`不替换
            + `a`替换所有
            + `q`退出替换
            + `l`最有一个，并把光标移动到行首
+ **进入编辑模式**
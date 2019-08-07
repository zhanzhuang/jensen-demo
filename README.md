# jensen-demo
+ my code repository
## git基本配置命令
## 实用git生成ssh密钥：
+ ssh-keygen -t rsa -C "email@wxample.com"
## 设置全局用户名和邮箱：
+ git config --global user.name "yourname"
+ git config --global user.email "email@example.com"
## 初始化仓库
+ git init
## 添加文件到暂存区
+ git add filename   #添加指定文件到暂存区
+ git add .          #添加工作区所有文件到暂存区
+ git add -i         #交互方式添加文件到暂存区
+ git add -u         #将工作区中已经变动的文件添加到暂存区，当新增加的文件不会被添加

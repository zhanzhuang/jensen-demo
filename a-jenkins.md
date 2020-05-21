# 持续集成和容器管理
+ **[DackerMaven插件](#DackerMaven插件)**
+ **[Jenkins](#Jenkins)**
    + **[什么是持续集成](#什么是持续集成)**
    + **[Jenkins简介](#Jenkins简介)**
    + **[Jenkins在centOS下的安装与卸载](#Jenkins在centOS下的安装与卸载)**
        + **[安装](#安装)**
        + **[卸载](#卸载)**
    + **[Jenkins插件安装](#Jenkins插件安装)**
    + **[Jenkins全局工具配置](#Jenkins全局工具配置)**
        + **[JDK配置](#JDK配置)**
        + **[Maven配置](#Maven配置)**
    
+ **[代码上传至Git服务器](#代码上传至Git服务器)**

## DackerMaven插件

## Jenkins
### 什么是持续集成
+ 持续集成Continuous integration，简称CI
+ 随着软件开发复杂度不断的提高，团队开发成员间如何更好地协同工作以确保软件开发的质量已经慢慢成为开发过程中不可回避的问题。
尤其是近些年来，敏捷开发在软件工程领域越来越红火，如何能在不断变化的需求中快速适应和确保软件的质量也显得尤为重要。
+ 持续集成是针对这一类问题的一种软件开发实践。它倡导团队开发成员必须经常集成他们的工作，甚至每天都可能发生多次集成。
而每次集成都是通过自动化的构建来验证，包括自动编译/发布和测试，从而尽快的发现集成错误，让团队能够更快的开发内聚的软件。
+ 持续集成的特点
    + 自动化周期性的集成测试过程，从检出代码/编译构建/运行测试/结果记录/测试统计等都是自动完成的，无需人工干预。
    + 需要有专门的集成服务器来执行集成构建
    + 需要有代码托管工具支持
+ 持续集成的作用
    + 保证团队卡覅人员提交代码的质量，减轻了软件发布时的压力
    + 持续集成中的任何一个环节都是自动完成的，无需太多人工干预，有利于减少重复过程以节省时间/费用和工作量
### Jenkins简介
+ Jenkins原名Hudson，2011年改为现在的名字，它是一个开源的实现持续集成的软件工具。官方网站：http://jenkins-ci.org
+ jenkins能实时监控集成中存在的错误，提供详细的日志文件和提醒功能，还能用图标的方式展示构建的趋势和稳定性
+ 特点
    + 易安装：仅仅一个 java -jar jenkins.war，从官网下载该文件后，直接运行，无需额
      外的安装，更无需安装数据库
    + 易配置：提供友好的GUI配置界面
    + 变更支持：Jenkins能从代码仓库（Subversion/CVS）中获取并产生代码更新列表并
      输出到编译输出信息中
    + 支持永久链接：用户是通过web来访问Jenkins的，而这些web页面的链接地址都是
      永久链接地址，因此，你可以在各种文档中直接使用该链接
    + 集成E-Mail/RSS/IM：当完成一次集成时，可通过这些工具实时告诉你集成结果（据
      我所知，构建一次集成需要花费一定时间，有了这个功能，你就可以在等待结果过程
      中，干别的事情）
    + JUnit/TestNG测试报告：也就是用以图表等形式提供详细的测试报表功能
    + 支持分布式构建：Jenkins可以把集成构建等工作分发到多台计算机中完成
    + 文件指纹信息：Jenkins会保存哪次集成构建产生了哪些jars文件，哪一次集成构建使
      用了哪个版本的jars文件等构建记录
    + 支持第三方插件：使得 Jenkins 变得越来越强大
### Jenkins在centOS下的安装与启动与卸载
#### 安装
+ 基于centOS7.5
+ Jenkins是基于Java的，请先安装JDK
+ 1.下载jenkins安装包
    + 查看jenkins版本：https://pkg.jenkins.io/redhat-stable/
    + 通过wget方式下载
        + `wget https://pkg.jenkins.io/redhat/jenkins-2.214-1.1.noarch.rpm`
    + 或者将`jenkins-2.214-1.1.noarch.rpm`上传至服务器
+ 2.安装jenkins
    + `rpm -ivh jenkins-2.214-1.1.noarch.rpm`
+ 3.配置jenkins
    + `vi /etc/sysconfig/jenkins`
    + 修改用户和端口(因为默认的8080端口和tomcat冲突)
        ```properties
        JENKINS_USER="root"
        JENKINS_PORT="8888"
        ```
    + 在jenkins的配置文件中添加java的路径
        + `vim /etc/init.d/jenkins`
            ```properties
            # 在candidates下面加
            /usr/local/java/jdk1.8.0_231/bin/java
            ```
    + 要执行一条这个命令
        + `sudo yum install libgcc.i686 --setopt=protected_multilib=false`
        + 在debian下安装lib32gcc1包，问题解决。问题原因是，一般os安装的是64位的，所以缺省是装libgcc这个包。但java一般还是会用32位的包，因此就会存在上面的问题（系统提供的libgcc_s.so.1是64位的，不是java启动需要的32位的），安装一个32位的就好了。
+ 4.刷新服务
    + `systemctl daemon-reload`
+ 5.启动服务
    + `systemctl start jenkins`
+ 6.查看进程
    + `ps -ef | grep jenkins`
+ 7.访问连接 http://122.51.187.33:8888
    + 执行`cat /var/lib/jenkins/secrets/initialAdminPassword`获取初始密码串
+ 8.选择左侧推荐插件安装
+ 9.设置用户
#### 卸载
+ `yum -y remove jenkins`
+ `rm -rf /var/cache/jenkins`
+ `rm -rf /var/lib/jenkins/`
+ `rm -rf /var/log/jenkins/`
### Jenkins插件安装
+ 以安装maven插件为例
+ 1.点击左侧Manage Jenkins
+ 2.选择Manage Plugins
+ 3.切换到可选插件，在过滤条件中输入maven
+ 4.找到Maven Integration，勾选前面的安装选项后点击直接安装
### Jenkins全局工具配置
#### JDK配置
+ Manage Jenkins -> Global Tool Configuration ->
+ 点击新增jdk，取消勾选自动安装，配置别名和java_home。
#### Maven配置
+ Manage Jenkins -> Global Tool Configuration ->
+ 点击新增maven，取消勾选自动安装，配置别名和maven_home

## 代码上传至Git服务器
### Gogs搭建与配置
+ Gogs是一款极易搭建的自主Git服务
+ Gogs的目标是打造一个最简单/最快速和最轻松的方式搭建自主服务。使用Go语言开发使得Gogs能够通过独立的二进制分发，
并且支持Go语言支持的所有平台，包括Linux Mac OS X Windows和ARM平台
+ 地址:http://gitee.com/Unknown/gogs
+ 1.下载镜像
    + `docker pull gogs/gogs`
+ 2.创建容器
    + `docker run -di --name=gogs -p 10022:22 -p 3000:3000 -v /var/gogsdata:/datagogs/gogs`
    

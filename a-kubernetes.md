# kubernetes目录
+ **[kubernetes介绍](#kubernetes介绍)**
    + **[kubernetes是什么](#kubernetes是什么)**
    + **[kubernetes能做什么](#kubernetes能做什么)**
    + **[为什么要用kubernetes](#为什么要用kubernetes)**
+ **[Kubernetes快速入门](#Kubernetes快速入门)**
    + **[环境准备](#环境准备)**
    + **[配置](#配置)**
## kubernetes介绍
### kubernetes是什么
+ Kubernetes(K8S)是Google在2014年发布的一个开源项目，用于自动化容器化应用程序的部署、扩展和管理。
  Kubernetes通常结合docker容器工作，并且整合多个运行着docker容器的主机集群
+ 官网地址:https://Kubernetes.io
+ 中文社区: https://www.kubernetes.org.cn/docs
+ Kubernetes的目标是让部署容器化的应用简单并且高效，Kubernetes一个核心特点就是能够自主的管理容器来保证云平台中的容器按照用户的期望运行。以下是Kubernetes相关特性
###### 自动包装
根据资源需求和其他约束自动放置容器，同时不会牺牲可用性，混合关键和最大努力的工作负载，以提高资源利用率并节省更多资源
###### 横向缩放
使用简单的命令或 UI，或者根据 CPU 的使用情况自动调整应用程序副本数
###### 自动部署和回滚
Kubernetes 逐渐部署对应用程序或其配置的更改，同时监视应用程序运行状况，以确保它不会同时终止所有实例。 如果出现问题，Kubernetes会为您恢复更改，利用日益增长的部署解决方案的生态系统
###### 存储编排
自动安装您所选择的存储系统，无论是本地存储，如公有云提供商 GCP 或 AWS, 还是网络存储系统 NFS,iSCSI, Gluster, Ceph, Cinder, 或 Flocker
###### 自我修复
重新启动失败的容器，在节点不可用时，替换和重新编排节点上的容器，终止不对用户定义的健康检查做出响应的容器，并且不会在客户端准备投放之前将其通告给客户端
###### 服务发现和负载均衡
不需要修改您的应用程序来使用不熟悉的服务发现机制，Kubernetes 为容器提供了自己的 IP 地址和一组容器的单个 DNS 名称，并可以在它们之间进行负载均衡
###### 密钥和配置管理
部署和更新密钥和应用程序配置，不会重新编译您的镜像，不会在堆栈配置中暴露密钥(secrets)
###### 批处理
除了服务之外，Kubernetes还可以管理您的批处理和 CI 工作负载，如果需要，替换出现故障的容器
### kubernetes能做什么
Kubernetes是一个全新的基于容器技术的芬不是架构领先方案。
Kubernetes是一个开放的开发平台(无侵入性，现有系统很容器迁移到Kubernetes上)。
Kubernetes是一个完备的芬不是系统支撑平台(完善的集群管理能力)。
使用Kubernetes可以在物理或虚拟机上Kubernetes集群上运行容器化应用，Kubernetes能够提供一个以容器为中心的基础架构，满足在生产环境中
运行应用的一些常见需求，如：
+ 多个进程协同工作
+ 存储系统挂载
+ Distributing secrets
+ 应用健康检测
+ 应用实例的复制
+ Pod自动伸缩/扩展
+ Naming and discovering
+ 负载均衡
+ 滚动更新
+ 资源监控
+ 日志访问
+ 调度应用程序
+ 提供认证和授权
### 为什么要用kubernetes
使用Kubernetes最直接的感受就是我们可以轻装上阵的开发复杂的系统了；其次Kubernetes是在全面拥抱微服务
架构（微服务的核心就是将一个巨大的单体应用拆分成很多小的互相连接的微服务，一个微服务后面可能是多个实
例副本在支撑，副本数量可以随着系统负荷的变化而动态调整）；最后Kubernetes系统架构具备超强的横向扩展
能力
## Kubernetes快速入门
### 环境准备
+ 关闭centos防火墙
    + `systemctl disable firewalld`
    + `systemctl stop firewalld`
+ 安装kubernetes软件和etcd
    + 安装前要更新yum，如果已经安装了docker要卸载掉，因为安装kubernetes会安装docker
    + `yum update`
    + `yum install -y kubernetes etcd`
+ 设置docker的镜像
    + ustc是老牌的linux镜像服务提供者了，还在遥远的ubuntu 5.04版本的时候就在用。ustc的docker镜像加速器速度 很快。ustc docker mirror的优势之一就是不需要注册，是真正的公共服务。
    + https://lug.ustc.edu.cn/wiki/mirrors/help/docker
    + `vi /etc/docker/daemon.json`
    + ```json
      {
      "registry-mirrors": ["https://docker.mirrors.ustc.edu.cn"]
      }
      ```
+ 设置etcd和docker开机自启
    + `systemctl enabled etcd.service`
    + `systemctl enabled docker.service`
+ 启动服务
    + `systemctl start etcd`
    + `systemctl start docker`
    + ```text
      如果docker启动失败，请参考(vi /etc/sysconfig/selinux 把selinux后面的改为disabled，重启
      一波 机器，再重启docker就可以了)
      ```
    + `systemctl start kube-apiserver`
    + `systemctl start kube-controller-manager`
    + `systemctl start kube-scheduler`
    + `systemctl start kube-proxy`
### 配置
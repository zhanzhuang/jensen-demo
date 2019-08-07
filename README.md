# jensen-demo
+ my code repository
## git基本配置命令
### 实用git生成ssh密钥：
+ ssh-keygen -t rsa -C "email@wxample.com"
### 设置全局用户名和邮箱：
+ git config --global user.name "yourname"
+ git config --global user.email "email@example.com"
### 初始化仓库
+ git init
### 添加文件到暂存区
+ git add filename   #添加指定文件到暂存区
+ git add .          #添加工作区所有文件到暂存区
+ git add -i         #交互方式添加文件到暂存区
+ git add -u         #将工作区中已经变动的文件添加到暂存区，当新增加的文件不会被添加
### 提交文件到仓库
+ git commit -m "描述信息"    #提交更新
+ git commit -am "描述信息"     #如果工作目录中仅是已跟踪的文件被修改或被删除，使用此命令提交
### 分支的创建，删除，合并，切换，查看
+ git branch      #查看git仓库中已有的分支
+ git branch 新分支名 [分支起点]    #创建分支，如果没有分支起点的话，则默认在当前分支的最新提交上创建分支
+ git checkout 分支名    #切换分支
+ git checkout -b 新分支名    #创建同时切换到新分支
+ git merge 要被合并的分支名    #合并分支
+ git branch -d 要删除的分支名     #删除指定分支(如果分支没有被合并过，该命令会执行失败)
+ git branch -D 要删除的分支名     #删除指定分支，不管有没有被合并过
合并分支过程中如果发生冲突则需要自己手动解决冲突，然后再提交。有冲突时，git会显示哪个文件有冲突，并在冲突的文件中加上特殊的标识符号，解决完冲突后，要手动去掉这些被添加的标示不好。如果冲突比较复杂，最好使用其他工具来协助，通过 git mergetool来启动。冲突一般是在不同的分支上对同一文件的同一位置内容进行了改动，并已提交到仓库中，这样在合并的时候就会发生冲突。
### 标签的添加，删除，查看
+ git tag   #查看标签
+ git tag 标签名     #创建简单的标签
+ git tag -a 标签名 -m "附加信息"    #创建附加信息的标签
+ git show 标签名      #通过标签查看信息
+ git tag -d 标签名      #删除标签
标签可以在需要的地方，为某个提交对象创建别名，这样以后我们就可以通过标签来查看一些信息，创建分支等。
### 查看工作目录状态
+ git status
在git命令执行后，要养成通过git status查看git状态的习惯，以便及时了解文件变化的情况。通过git status可以知道文件的状态。
### 查看提交历史
+ git log
+ git log -p      #显示每次提交文件变化
通过git log可以查看当前分支的所有提交历史，知道每次提交的commit对象的ID以及提交时附加的描述信息等。要显示更多的信息，需要使用其支持的选项，如git log -p可以将每次提交的文件变化也显示出来。
### 查看指定的提交对象
+ git show commit_id    #查看指定的某次提交内容
+ git show --all      #显示所有的提交历史内容
+ git shortlog -s -n    #显示总的提交次数
通过git log可以显示整个提交历史，而通过git show commit_id则可以查看指定的某次提交内容，当然git show -all也可以显示出提交历史，另外还可以格式化显示内容。
### note:commit_id可以是commit对象对应的ID，也可以是HEAD，分支名，tag等。
### 查看工作区，暂存区，仓库之间的差异
+ git diff    #比较工作区与暂存区的差异
+ git diff HEAD     #比较工作区与仓库中最近一次的提交间的差异
+ git diff --cached   #比较暂存区与仓库中最近一次提交的差异
+ git blame filename    #可以列出该文件每次被修改的时间和内容
### 版本回退，撤销操作
+ git reflog        #显示提交历史的简介
+ git checkout -- filename    #丢弃工作区的修改
+ git reset --hard HEAD^    #回退到上一个版本
+ git reset --hard commit_id    #回退到指定版本
+ git checkout -- filename     #恢复工作区被杀出的指定文件(文件之前被提交到仓库中)
+ git checkout -f   #恢复工作区中所有被删除的文件(文件之前被提交到仓库中)
+ git ls-files -d   #列出工作区被删除的文件(文件之前被提交到仓库中)
有时候，由于我们的误操作，产生了一些错误，我们发现后希望能够及时纠正这些因为误操作而产生的结果，将工作目录恢复到某个正常状态。
++ 撤销修改，但还没有添加到暂存区：git checkout -- filename  修改的文件会被恢复到上一次提交的状态，修改内容会丢失。
++ 版本回退：先通过git reflog找到某个版本的commit_id，然后用git reset --hard commit_id将工作目录的文件恢复到指定的版本。
++ 恢复工作区中被删除的文件(文件之前被提交到仓库中)：git checkout -- filename 或 git checkout -f
### 备份工作区
+ git stash   #将工作区文件保存在git内部栈中
+ git stash list    #列出git内部栈中保存的工作区文件列表
+ git stash applu stash_id    #恢复工作区到指定的内部栈状态
+ git stash pop     #恢复工作区到上一个内部栈状态
+ git stash clear     #清空git内部栈
如果正在一个develop分支上开发新功能，但这时master突然发现了bug，需要及时修复，而develop分支的工作还没有完成，且不希望之前的工作就这样提交到仓库中时，可以使用git stash来暂时保存这些状态到git内部栈中，并用当前分支上一次的提交内容来恢复工作目录，然后切换到master分支进行bug修复，等修复完毕并提交到仓库上后，再使用git stash apply [stash@{0}]或者git stash pop将工作目录恢复到之前的状态，继续之前的工作。
同时也可以多次使用git stash将未提交的代码压入到git栈中，但当多次使用git stash命令后，git栈里充满了未提交的代码，这时候到底要用哪个版本来恢复工作目录呢？git使用git stash apply stash@{1}就可以用版本号为stash@{1}的内容来恢复工作目录。
当git栈中所有的内容都被恢复后，可以使用git stash clear来将栈清空。
### 远程仓库的克隆，添加，查看
+ git remote    #显示已添加的远程仓库名
+ git remote -v     #显示已添加的远程仓库名和地址
+ git remote add 远程仓库名 远程仓库地址   #在本地添加远程仓库
+ git remote rm 远程仓库名   #删除本地添加的远程仓库
+ git remote rename 原名 新名   #重命名远程仓库
+ git clone 远程仓库地址 [克隆到指定的文件夹]    #克隆远程仓库到本地
+ git fetch 远程仓库名   #从远程仓库抓取最新数据到本地但不育本地分支进行合并
+ git pull 远程仓库名 本地要合并的分支名    #从远程仓库抓取最新数据并自动与本地分支进行合并
+ git push 远程仓库名 本地分支名    #将本地仓库推送到远程仓库中
+ git remote show 远程仓库名   #查看远程仓库信息
+ git remote show   #查看所有远程仓库
+ git push 远程仓库名 标签名  #将标签推送到远程仓库(git默认不推送标签)
### 协同流程
++ fork远程项目
++ 把fork的项目clone到本地
++ 执行以下命令，将别人的库添加为远端库
+ git remote add 远端仓库名 远端的分支
++ 运行以下命令，拉去合并到本地
+ git pull 远端仓库名 远端分支名
++ 编辑内容
++ commit之后push到自己的库
++ 登陆github，在你的首页可以看到一个pull request按钮，点击它，填写一些说明信息，提交即可。
在本地编辑内容前必须执行pull操作同步别人的远端(这样避免冲突)
### 实用技巧
当手头工作没有完成时，先把工作现场git stash一下，然后去修复bug，修复后，再使用git stash pop，回到工作现场。

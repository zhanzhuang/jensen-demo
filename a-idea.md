# IDEA技巧
+ **[mac快捷键](#mac快捷键)**
    + **[mac基本](#mac基本)**
    + **[mac查找](#mac查找)**
    + **[mac跳转](#mac跳转)**
    + **[mac替换](#mac替换)**
    + **[mac生成代码](#mac生成代码)**
    + **[macDEBUG](#macDEBUG)**
+ **[windows快捷键](#windows快捷键)**
    + **[windows基本](#windows基本)**
    + **[windows查找](#windows查找)**
    + **[windows跳转](#windows跳转)**
    + **[windows替换](#windows替换)**
    + **[windows生成代码](#windows生成代码)**
    + **[windowsDEBUG](#windowsDEBUG)**
+ **[设置](#设置)**
    + **[取消代码重复检查](#取消代码重复检查)**
    + **[Autowired提示报错](#Autowired提示报错)**
    + **[idea新项目默认配置](#idea新项目默认配置)**
    + **[热部署](#热部署)**
    
    
## mac快捷键
### mac基本
+ **启动main方法**`control + R`
+ **启动main方法 macDEBUG** `control + D`
+ **格式化代码(可选中文件夹)** `cmd + option + L`
+ **自动导包(可选中文件夹)** `control + option + O`
+ **上下换行**`option + shift + 上/下`
+ **快速修复**`cmd + enter`
+ **开启新一行**`shift + enter`
+ **切换大小写**`cmd + shift + U`
+ **返回或者前进最近编辑的地方**`cmd + option + 左/右`
+ **定位某一行**`command + L`
+ **连续选中代码块**`option + 上/下`
+ **作用域重命名**`shift + F6`
+ **删除代码**`cmd + x`
### mac查找
+ **当前窗口mac查找文本**`cmd + F`
+ **mac查找文件**`cmd + O`
+ **在全工程中mac查找关键字**`cmd + shift + F`
+ **mac查找最近打开的文件**`cmd + E`
+ **当前方法被谁使用**`option + F7`
### mac跳转


### mac替换
+ **当前窗口mac替换**`cmd + R`
+ **工程中mac替换**`cmd + shift + R`
### mac生成代码
+ **复制一行**`cmd + D`
+ **生成构造器**`cmd + N`
+ **生成try catch**`选中代码块后cmd + option + T`
+ **重写父类方法**`control + O`
+ **实现接口中的方法**`control + I`
### macDEBUG
+ **查看断点信息**`cmd + shift + F8`
+ **下一步(会进入当前方法。不会进入当前方法内嵌方法)** `F7`
+ **下一步(不进入方法)** `F8`
+ **运行到光标处，如果光标前有其他断点会进入到该断点**`option + F9`
+ **智能步入，断点所在行上有多个方法调用，会弹出进入哪个方法**`shift + F7`
+ **跳到下一个断点**`cmd + option + R`
+ **跳出方法**`shift + F8`
+ **计算表达式（可以更改变量值使其生效）** `option + F8`

## windows快捷键
### windows基本
+ **启动main方法** `shift + F10`
+ **启动光标所在类main方法** `ctrl + shift + F10`
+ **格式化代码(可以选中文件夹)** `ctrl + alt + L`
+ **优化导包(可以选中文件夹)** `ctrl + alt + O`
+ **上下换行** `shift + alt + 上/下`
+ **快速修复** `alt + enter`
+ **开启新一行** `shift + alt + enter`
+ **切换大小写** `ctrl + shift + U`
+ **返回或者前进最近编辑的地方** `ctrl + alt + 左/右`
+ **定位某一行** `ctrl + G`
+ **连续选中代码块** ``
+ **作用域重命名** `shift + F6`
+ **删除代码** `ctrl + Y`
### windows查找
+ **当前窗口查找文本** `ctrl + F`
+ **全局搜索文件** `ctrl + N`
+ **全局搜索关键字** `ctrl + shift + F`
+ **查找最近打开的文件** `ctrl + E`
+ **当前方法被谁使用* `alt + F7`
### windows跳转
+ **跳转到上一个操作**`ctrl + alt + <-`
+ **跳转到下一个操作**`ctrl + alt + ->`

### windows替换
+ **当前窗口替换** `ctrl + R`
+ **全局替换** `ctrl + shift + R`
### windows生成代码
+ **复制一行** `ctrl + D`
+ **生成构造器** `alt + insert`
+ **生成try catch** `选中代码块后ctrl + alt + T`
+ **重写父类方法** `ctrl + O`
+ **实现接口中的方法** `ctrl + I`

### windowsDEBUG
+ **查看断点信息** `ctrl + shift + F8`
+ **下一步(会进入当前方法。不会进入当前方法内嵌方法)** `F7`
+ **下一步(不会进入方法)** `F8`
+ **运行到光标处，如果光标前有其他断点会进入到该断点** `ctrl + F8`
+ **智能步入，断点所在行上有多个方法调用，会弹出进入哪个方法** `shift + F7`
+ **跳到下一个断点** `F9`
+ **跳出方法**`shift + F8`
+ **计算表达式（可以更改变量值使其生效）** `alt + F8`

## 设置
### 取消代码重复检查
file->settings->editor->inspections->general->duplicated code fragment将右侧勾去掉
### Autowired提示报错
file->settings->editor->inspections->spring->spring core->code->autowiring for bean class 将error设置成warning
### idea新项目默认配置
+ file->other settings->preferences for new projects... maven
+ file->other settings->structure for new projects... jdk
### 热部署
+ 1.导入依赖
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
  </dependency>
```
+ 2.修改了java类的地方，使用 Ctrl + Shift + F9 进行热更新
+ 3.静态页面/模板页面，使用 Ctrl + F9 进行热更新
+ 4.快捷键使用后不生效？前往File-Settings-Compiler-Build Project automatically选项开始idea自动编译    
+ 5.重启IDEA

#策略模式介绍
###意图
+ 定义一系列算法，把他们一个个封装起来，并且使他们可相互替换
###主要解决
+ 在有多种算法相似的情况下，使用if else所带来的复杂和难以维护
###何时使用
+ 一个系统有许多个类，而区分他们的只是他们直接的行为
###如何解决
+ 将这些算法封装成一个一个的类，任意的替换
###关键代码
+ 实现同一个接口
###应用实例
+ 出游的交通方式。选择自行车，汽车等都是策略
###注意事项
+ 如果一个系统的策略多余四个，就要考虑使用混合模式，解决策略类膨胀的问题
# 一 ElasticSearch简介
# 二 ElasticSearch安装与启动
+ **下载**
    + 地址: **https://www.elastic.co/cn/downloads/elasticsearch**
    + 下载zip包解压即可
+ **安装**
    + 解压zip包即可
  **启动**
    + 1.前置条件:**JDK1.8**
    + 2.**windows**下启动**bin**目录下的**elasticsearch.bat**
    + 3.输入**localhost:9200**
+ **安装head插件**
    + 1.地址:**https://github.com/mobz/elasticsearch-head**
    + 2.下载并安装**node.js**
    + 3.在控制台输入:**npm install -g grunt-cli**
    + 4.在控制台输入:**npm install**
    + 5.启动插件:**grunt server**
    + 6.输入**localhost:9100**
    + 如果打开插件无法连接,需要进行跨域设置,在**config/elasticsearch.yml**
    中加入**http.cors.enabled: true**,**http.cors.allow-origin: "⭐"**
    + 重新启动elasticsearch即可
# 三 ElasticSearch相关概念(术语)
+ **概述**
    + elasticsearch是面向文档(document oriented)的,一个文档就相当于一条记录
        ```
        DB  ->  databases   ->  tables  ->  rows        ->  columns
        ES  ->  Index       ->  types   ->  documents   ->  fields
        ```
+ **索引 index**
    + 一个索引就是拥有几分相似特征的文档集合。比如说，你可以有一个客户数据的索引，另一个产品目录的索引，还有一个订单数据的索引。
    一个索引由一个名字来标识(必须全部是小写字母)，并且当我们要对对应于这个索引中的文档进行索引，搜索，更新和删除的时候，都要使用到
    这个名字。在一个集群中，可以定义任意多的索引。
+ **类型 type**
    + 在一个索引中，你可以定义一种或多种类型。一个类型是你的索引的一个逻辑上的分类/分区，其语言完全由你来定。通常，会为居右一组
    共同字段的文档定义一个类型。比如说，我们假设你运营一个博客平台并且将你所有的数据存储到要给索引中。在这个索引中，你可以为用户
    数据定义过一个类型，为博客数据定义另一个类型，当然，也可以为评论数据定义另一个类型。
+ **字段 field**
    + 相当于是数据表的字段，对文档数据根据不同属性进行的分类标识
+ **映射 mapping**
    + mapping是处理数据的方式和规则方面做一些限制，如某个字段的数据类型，默认值，分析器，是否被索引等等，这些都是映射里面可以设置的，
    其他就是处理es里面数据的一些使用规则设置也叫做映射，按着最优处理规则处理数据对性能提高很大，因此才需要建立映射，并且需要思考如何
    建立映射才能对性能更好。
+ **文档 document**
    + 一个文档是一个可被索引的基础信息单元。比如你可以拥有某一个客户的文档，某一个产品的一个文档，当然，也可以拥有某个订单的一个文档。
    文档以JSON(javascript object notation)格式来表示，而JSON是一个到处存在的互联网数据交互格式。
    在一个index/type里面，你可以存储任意多的文档。注意，尽管一个文档，物理上存在于一个索引之中，文档必须被索引/赋予一个索引的type
+ **接近实时 NRT**
    + elasticsearch是一个接近实时的搜索平台。这意味着，从索引一个文档直到这个文档能够被搜索到有一个轻微的延迟(通常是1s)
+ **集群 cluster**   
    + 一个集群就是由一个或者多个节点组织在一起，它们共同持有整个的数据，并一起提供索引和搜索功能。一个集群由一个唯一的名字标识，
    这个名字默认是"elasticsearch"。这个名字是重要的，因为一个节点只能通过指定某个集群的名字来加入这个集群。
+ **节点 node(一个节点就是一个服务器，多个服务器组成集群)**
    + 一个节点是集群中的一个服务器，作为集群的一部分，它存储数据，参与集群的索引和搜索功能。和集群类似，一个节点也是由一个名字来标识
    的，默认情况下，这个名字是一个随机的漫威漫画角色的名字，这个名字会在启动的时候赋予节点。这个名字对于管理工作来说挺重要的，因为在这个
    管理过程中，你回去确定网络中的哪些服务器对应于elasticsearch集群中的那些节点。
    一个节点可以通过配置集群名称来加入一个指定的集群。默认情况下，每个节点都会被安排加入到一个叫做"elasticsearch"的集群中，这意味着，
    如果你在你的网络中启动了若干个节点，并假定它们能够相互发现彼此，它们将会自动地形成并加入到一个叫做"elasticsearch"的集群中。
    在一个集群里，只要你想，可以拥有任意多个节点。而且，如果当前你的网络中没有运行任何elasticsearch节点，这时启动一个节点，会默认
    创建并加入到一个叫做"elasticsearch"的集群。    
+ **分片和复制(备份) shards & replicas**
# 四 增删改查
## 增
+ **创建索引库并设置mapping**
    + http地址, **blog1**是索引名称,**PUT**请求
        ```
        http://127.0.0.1:9200/blog1
        ```
    + json内容
        ```
        {
        	"mappings":{
        		"article":{
        			"properties":{
        				"id":{
        					"type":"long",
        					"store":true
        				},
        				"title":{
        					"type":"text",
        					"store":true,
        					"index":true,
        					"analyzer":"standard"
        				},
        				"content":{
        					"type":"text",
        					"store":true,
        					"index":true,
        					"analyzer":"standard"
        				}
        			}
        		}
        	}
        }
        ```
    + 响应
        ```
        {
            "acknowledged": true,
            "shards_acknowledged": true,
            "index": "blog1"
        }
        ```
+ **先创建索引库后设置mappings**
    + 创建索引库 
        + 地址 PUT 
            ```
            http://127.0.0.1:9200/blog
            ```
    + 设置mappings
        + 地址  POST
            ```
            http://127.0.0.1:9200/blog/hello/_mappings
            ```
        + 内容
            ```
            {
            	"hello":{
            		"properties":{
            			"id":{
            				"type":"long",
            				"store":true
            			},
            			"title":{
            				"type":"text",
            				"store":true,
            				"index":true,
            				"analyzer":"standard"
            			},
            			"content":{
            				"type":"text",
            				"store":true,
            				"index":true,
            				"analyzer":"standard"
            			}
            		}
            	}
            	
            }
            ```
        + 响应
            ```
            {
                "acknowledged": true
            }
            ```
+ **创建文档document**
    + 地址 POST
        ```
        http://127.0.0.1:9200/blog/hello/1
        ```
    + 内容
        ```
        {
        	"id":1,
        	"title":"新添加的文档1",
        	"content":"新添加文档的内容"
        }
        ```
    + 响应
        ```
        {
            "_index": "blog",
            "_type": "hello",
            "_id": "1",
            "_version": 1,
            "result": "created",
            "_shards": {
                "total": 2,
                "successful": 1,
                "failed": 0
            },
            "created": true
        }
        ```
        + 也可以在es-head上面查看数据(数据浏览->blog)          
## 删          
+ **删除索引库**
    + 地址 DELETE
        ```
        http://127.0.0.1/blog2
        ```
    + 响应
        ```
        {
            "acknowledged": true
        }
        ```

+ **删除文档**
    + 地址
        ```
        http://127.0.0.1:9200/blog/hello/1
        ```
    + 响应
        ```
        {
            "found": true,
            "_index": "blog",
            "_type": "hello",
            "_id": "1",
            "_version": 2,
            "result": "deleted",
            "_shards": {
                "total": 2,
                "successful": 1,
                "failed": 0
            }
        }
        ```
## 修改      
+ **修改文档(底层就是先删除后添加)**
    + 地址
        ```
        http://127.0.0.1:9200/blog/hello/1
        ```
    + 响应
        ```
        {
            "_index": "blog",
            "_type": "hello",
            "_id": "1",
            "_version": 2,
            "result": "updated",
            "_shards": {
                "total": 2,
                "successful": 1,
                "failed": 0
            },
            "created": false
        }
        ```
## 查询      
+ **根据id查询文档**
    + 
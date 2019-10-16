## 一 ElasticSearch简介
## 二 ElasticSearch安装与启动
+ **下载**
    + 地址: **https://www.elastic.co/cn/downloads/elasticsearch**
    + 下载zip包解压即可
+ **安装**
    + 解压zip包即可
+ **启动**
    + 1.前置条件:**JDK1.8**
    + 2.**windows**下启动**bin**目录下的**elasticsearch.bat**
    + 3.输入**localhost:9200**
+ **安装head插件**
    + 1.地址:**https://github.com/mobz/elasticsearch-head**
    + 2.下载并安装**node.js**
    + 3.在控制台输入:**npm install -g grunt-cli**
    + 4.在控制台输入:**npm install**
    + 5.启动插件:在elasticsearch-head-master文件夹下输入 **grunt server**
    + 6.输入**localhost:9100**
    + 如果打开插件无法连接,需要进行跨域设置,在**config/elasticsearch.yml**
    中加入**http.cors.enabled: true**,**http.cors.allow-origin: "⭐"**
    + 重新启动elasticsearch即可
## 三 ElasticSearch相关概念(术语)
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
## 四 增删改查
### 增
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
### 删          
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
    + 地址 DELETE
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
### 修改      
+ **修改文档(底层就是先删除后添加)**
    + 地址 POST
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
### 查询      
+ **根据id查询文档**
    + 地址 GET
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
        	"found": true,
        	"_source": {
        		"id": 111,
        		"title": "修改之后的文档",
        		"content": "士大夫士大夫撒旦的方式"
        	}
        }
        ```
+ **根据关键词查询(term查询)**
    + 使用的是标准分析器(一个汉字一个关键词)
    + 地址 POST
        ```
        http://127.0.0.1:9200/blog/hello/_search
        ```
    + 请求体
        ```
        {
        	"query":{
        		"term":{
        			"title":"文"
        		}
        	}
        }
        ```
    + 响应
        ```
        {
        	"took": 5,
        	"timed_out": false,
        	"_shards": {
        		"total": 5,
        		"successful": 5,
        		"skipped": 0,
        		"failed": 0
        	},
        	"hits": {
        		"total": 2,
        		"max_score": 0.52583575,
        		"hits": [
        			{
        				"_index": "blog",
        				"_type": "hello",
        				"_id": "AW3N_26QcPyfSyVdaiJZ",
        				"_score": 0.52583575,
        				"_source": {
        					"id": 100,
        					"title": "新添加的文档1",
        					"content": "新添加文档的内容"
        				}
        			},
        			{
        				"_index": "blog",
        				"_type": "hello",
        				"_id": "1",
        				"_score": 0.28582606,
        				"_source": {
        					"id": 111,
        					"title": "修改之后的文档",
        					"content": "士大夫士大夫撒旦的方式"
        				}
        			}
        		]
        	}
        }
        ```
+ **queryString查询**
    + 将查询的汉字拆分成单个进行查询(有多余的汉字也没关系)
    + 地址 POST
        ```
        http://127.0.0.1:9200/blog/hello/_search
        ```
    + 请求体
        ```
        {
        	"query":{
        		"query_string":{
        			"default_field":"title",
        			"query":"文档啊啊啊啊啊啊"
        		}
        	}
        }
        ```
    + 响应
        ```
        {
        	"took": 15,
        	"timed_out": false,
        	"_shards": {
        		"total": 5,
        		"successful": 5,
        		"skipped": 0,
        		"failed": 0
        	},
        	"hits": {
        		"total": 2,
        		"max_score": 1.0516715,
        		"hits": [
        			{
        				"_index": "blog",
        				"_type": "hello",
        				"_id": "AW3N_26QcPyfSyVdaiJZ",
        				"_score": 1.0516715,
        				"_source": {
        					"id": 100,
        					"title": "新添加的文档1",
        					"content": "新添加文档的内容"
        				}
        			},
        			{
        				"_index": "blog",
        				"_type": "hello",
        				"_id": "1",
        				"_score": 0.5716521,
        				"_source": {
        					"id": 111,
        					"title": "修改之后的文档",
        					"content": "士大夫士大夫撒旦的方式"
        				}
        			}
        		]
        	}
        }
        ```
## 五 IK分词器和ElasticSearch集成使用
+ **标准分词器存在的问题**
    + 在进行滚字符串查询时，我们发现去搜索"搜索服务器"和"钢索"都可以搜索到数据;
    而在进行词条查询时，我们搜索"搜索"却没有搜索到数据;
    原因时ElasticSearch的标准分词器导致的，当我们创建索引时，字段使用的时标准分词器;
+ `http://127.0.0.1:9200/_analyze?analyzer=standard&pretty=true&text=我是程序员`
    + 分词按照 我 是 程 序 员 进行分词
    + 我们需要的分词效果是：我 是 程序 程序员
+ **IK分词器简介**
    + IKAnalyzer是一个开源，基于java开发的轻量级中文分词工具包
+ **下载(IK版本与ES的版本要一致)**
    + **https://github.com/medcl/elasticsearch-analysis-ik/releases** 下载zip包
    + 将zip解压到**elasticsearch-5.6.8\plugins**下
    + 重启ElasticSearch，即可加载IK分词器
+ **IK分词器测试**
    + IK提供了两个分词算法**ik_smart**和**ik_max_word**
    + 其中**ik_smart**为最少切分，**ik_max_word**为最细粒度划分
    + 1)最小切分 GET
        ```
        http://127.0.0.1:9200/_analyze?analyzer=ik_smart&pretty=true&text=我是程序员
        ```
        响应
        ```
        {
          "tokens": [
            {
              "token": "我",
              "start_offset": 0,
              "end_offset": 1,
              "type": "CN_CHAR",
              "position": 0
            },
            {
              "token": "是",
              "start_offset": 1,
              "end_offset": 2,
              "type": "CN_CHAR",
              "position": 1
            },
            {
              "token": "程序员",
              "start_offset": 2,
              "end_offset": 5,
              "type": "CN_WORD",
              "position": 2
            }
          ]
        }
        ```
    + 2)最细切分 GET
        ```
        http://127.0.0.1:9200/_analyze?analyzer=ik_max_word&pretty=true&text=我是程序员
        ```
        响应
        ```
        {
          "tokens": [
            {
              "token": "我",
              "start_offset": 0,
              "end_offset": 1,
              "type": "CN_CHAR",
              "position": 0
            },
            {
              "token": "是",
              "start_offset": 1,
              "end_offset": 2,
              "type": "CN_CHAR",
              "position": 1
            },
            {
              "token": "程序员",
              "start_offset": 2,
              "end_offset": 5,
              "type": "CN_WORD",
              "position": 2
            },
            {
              "token": "程序",
              "start_offset": 2,
              "end_offset": 4,
              "type": "CN_WORD",
              "position": 3
            },
            {
              "token": "员",
              "start_offset": 4,
              "end_offset": 5,
              "type": "CN_CHAR",
              "position": 4
            }
          ]
        }
        ```
+ **中文分词器的使用及测试**
    + 将原来的blog删除创建一个新的
        + 地址 PUT
            ```
            http://127.0.0.1:9200/blog/
            ```
        + 请求体(hello就是type)
            ``` 
            {
            	"mappings":{
            		"hello":{
            			"properties":{
            				"id":{
            					"type":"long",
            					"store":true
            				},
            				"title":{
            					"type":"text",
            					"store":true,
            					"analyzer":"ik_smart"
            				},
            				"content":{
            					"type":"text",
            					"store":true,
            					"analyzer":"ik_smart"
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
                "index": "blog"
            }
            ```
    + **添加测试数据**,添加六条此处省略
        + 地址 POST
            ```
            http://127.0.0.1:9200/blog/hello/6
            ```
        + 请求体
            ```
            {
            	"id":"6",
            	"title":"节点。 一个节点可以通过配置集群名称来加入",
            	"content":"如果当前你的网络中没有运行任何elasticsearch节点，这时启动一个节点，会默认 创建并加入到一个"
            }
            ```
        + 响应
            ```
            {
                "_index": "blog",
                "_type": "hello",
                "_id": "6",
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
    + **查询**
        + 地址 POST
            ```
            http://127.0.0.1:9200/blog/hello/_search
            ```
        + 请求体
            ```
            {
            	"query":{
            		"query_string":{
            			"default_field":"content",
            			"query":"一个"
            		}
            	}
            }
            ```
        + 响应
            ```
            {
            	"took": 21,
            	"timed_out": false,
            	"_shards": {
            		"total": 5,
            		"successful": 5,
            		"skipped": 0,
            		"failed": 0
            	},
            	"hits": {
            		"total": 4,
            		"max_score": 0.5280169,
            		"hits": [
            			{
            				"_index": "blog",
            				"_type": "hello",
            				"_id": "4",
            				"_score": 0.5280169,
            				"_source": {
            					"id": "4",
            					"title": "并且当我们要对对应于这个索",
            					"content": "档定义一个类型。比如说，我们假设你运营一"
            				}
            			},
            			{
            				"_index": "blog",
            				"_type": "hello",
            				"_id": "6",
            				"_score": 0.50090355,
            				"_source": {
            					"id": "6",
            					"title": "节点。 一个节点可以通过配置集群名称来加入",
            					"content": "如果当前你的网络中没有运行任何elasticsearch节点，这时启动一个节点，会默认 创建并加入到一个"
            				}
            			},
            			{
            				"_index": "blog",
            				"_type": "hello",
            				"_id": "3",
            				"_score": 0.3654544,
            				"_source": {
            					"id": "3",
            					"title": "一个集群就是由一个或者多个节点组织在一起，它们共同持有整个的数据",
            					"content": "一个节点是集群中的一个服务器，作为集群的一部分，它存储数据，参与集群的索引和搜索功能"
            				}
            			},
            			{
            				"_index": "blog",
            				"_type": "hello",
            				"_id": "5",
            				"_score": 0.27179778,
            				"_source": {
            					"id": "5",
            					"title": "比如你可以拥有某一个客户的文档",
            					"content": "你可以存储任意多的文档。注意，尽管一个文档，物理上存"
            				}
            			}
            		]
            	}
            }
            ```
## 六 ElasticSearch集群
### 集群的相关概念
+ **集群 cluster**
    + 一个集群就是由一个或多个节点组织在一起，她们共同持有整个的数据，并一起提供索引和搜索功能。一个集群由一个唯一的名字标识
    这个名字默认就是"elasticsearch"。这个名字是重要的，一位内要给节点只能通过指定某个集群的名字来加入这个集群。
+ **节点 node**
    + 一个节点是集群中的一个服务器，作为集群的一部分，它存储数据，参与集群的索引和搜索功能。和集群类似，一
    个节点也是由一个名字来标识的，默认情况下，这个名字是一个随机的漫威漫画角色的名字，这个名字会在启动的
    时候赋予节点。这个名字对于管理工作来说挺重要的，因为在这个管理过程中，你会去确定网络中的哪些服务器对
    应于Elasticsearch集群中的哪些节点。
    一个节点可以通过配置集群名称的方式来加入一个指定的集群。默认情况下，每个节点都会被安排加入到一个叫
    做“elasticsearch”的集群中，这意味着，如果你在你的网络中启动了若干个节点，并假定它们能够相互发现彼此，
    它们将会自动地形成并加入到一个叫做“elasticsearch”的集群中。
    在一个集群里，只要你想，可以拥有任意多个节点。而且，如果当前你的网络中没有运行任何Elasticsearch节点，
    这时启动一个节点，会默认创建并加入一个叫做“elasticsearch”的集群。
+ **分片和复制 shards&replicas**
    + 一个索引可以存储超出单个结点硬件限制的大量数据。比如，一个具有10亿文档的索引占据1TB的磁盘空间，而任
    一节点都没有这样大的磁盘空间；或者单个节点处理搜索请求，响应太慢。为了解决这个问题，Elasticsearch提供
    了将索引划分成多份的能力，这些份就叫做分片。当你创建一个索引的时候，你可以指定你想要的分片的数量。每
    个分片本身也是一个功能完善并且独立的“索引”，这个“索引”可以被放置到集群中的任何节点上。分片很重要，主
    要有两方面的原因： 1）允许你水平分割/扩展你的内容容量。 2）允许你在分片（潜在地，位于多个节点上）之上
    进行分布式的、并行的操作，进而提高性能/吞吐量。
    至于一个分片怎样分布，它的文档怎样聚合回搜索请求，是完全由Elasticsearch管理的，对于作为用户的你来说，
    这些都是透明的。
    在一个网络/云的环境里，失败随时都可能发生，在某个分片/节点不知怎么的就处于离线状态，或者由于任何原因
    消失了，这种情况下，有一个故障转移机制是非常有用并且是强烈推荐的。为此目的，Elasticsearch允许你创建分
    片的一份或多份拷贝，这些拷贝叫做复制分片，或者直接叫复制。
    复制之所以重要，有两个主要原因： 在分片/节点失败的情况下，提供了高可用性。因为这个原因，注意到复制分
    片从不与原/主要（original/primary）分片置于同一节点上是非常重要的。扩展你的搜索量/吞吐量，因为搜索可以
    在所有的复制上并行运行。总之，每个索引可以被分成多个分片。一个索引也可以被复制0次（意思是没有复制）
    或多次。一旦复制了，每个索引就有了主分片（作为复制源的原来的分片）和复制分片（主分片的拷贝）之别。分
    片和复制的数量可以在索引创建的时候指定。在索引创建之后，你可以在任何时候动态地改变复制的数量，但是你
    事后不能改变分片的数量。
    默认情况下，Elasticsearch中的每个索引被分片5个主分片和1个复制，这意味着，如果你的集群中至少有两个节
    点，你的索引将会有5个主分片和另外5个复制分片（1个完全拷贝），这样的话每个索引总共就有10个分片。
### 集群的搭建
+ **新建三份es程序,名字为`elasticsearch-5.6.8-cluster-01(02/03)`**
    + 要清空data目录
    + **`elasticsearch-5.6.8-cluster-01(02/03)/config/elasticsearch.yml`添加如下内容**
        ```
        #节点1的配置信息：
        #集群名称，保证唯一
        cluster.name: my‐elasticsearch
        #节点名称，必须不一样
        node.name: node‐1(node-2/node-3)
        #必须为本机的ip地址
        network.host: 127.0.0.1
        #服务端口号，在同一机器下必须不一样
        http.port: 9201(9202/9203)
        #集群间通信端口号，在同一机器下必须不一样
        transport.tcp.port: 9301(9302/9303)
        #设置集群自动发现机器ip集合
        discovery.zen.ping.unicast.hosts: ["127.0.0.1:9301","127.0.0.1:9302","127.0.0.1:9303"]
        ```
    + **分别启动三个es程序,在head中连接任意一台es即可**
        + 其中⭐代表主节点
        + ⚪代表从节点
### 集群使用测试
+ **创建索引**
    + 地址 PUT
        ```
        http://127.0.0.1:9201/blog
        ```   
    + 请求体
        ```
        {
            "mappings":{
            	"hello":{
            		"properties":{
            			"id":{
            				"type":"long",
            				"store":true
            			},
            			"title":{
            				"type":"text",
            				"store":true,
            				"analyzer":"ik_smart"
            			},
            			"content":{
            				"type":"text",
            				"store":true,
            				"analyzer":"ik_smart"
            			}
            		}
            	}
            }
         }
        ```             
+ **添加数据**
    + 地址 POST
        ```
        http://127.0.0.1:9201/blog/hello
        ```
    + 请求体
        ```
        {
        	"id":1,
        	"title":"这是一个测试文档",
        	"content":"这是测试内容"
        }
        ```
    + 在head中点击数据浏览即可查看到内容
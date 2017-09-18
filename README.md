#
mall商城

项目描述:模仿淘宝商城写的一个商城网站。

前台客户功能:首页、分类页、商品详情页、查询商品、商品排序,用户注册、登录、购物车管理、订单状态流转、结算、支付、收货、评价。

后台管理功能:管理商品、订单、类目、商品规格属性、用户管理以及内容发布等功能。

后端技术：spring、SpringMVC、Mybatis

前端技术：JSP、JSTL、jQuery、Ajax、Bootstrap、KindEditor（富文本编辑器）、CSS+DIV

缓存技术：Redis

权限控制：shiro

远程调用技术：httpclient（调用系统服务）

数据库：MySQL

服务器：tomcat

版本控制：git


seckill模块

项目描述：模仿慕课网的课程写的商品秒杀模块。

解决重复提交、秒杀接口暴露等问题。为了提高QPS, 采用缓存数据、应用存储过程、应用Redis队列来解决并发安全问题。



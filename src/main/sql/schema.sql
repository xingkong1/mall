--秒杀商品库存表
| seckill | CREATE TABLE seckill (
  seckill_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  name varchar(120) NOT NULL COMMENT '商品名称',
  number int(11) NOT NULL COMMENT '库存数量',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

  start_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀开
始时间',
  end_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束
时间',
  PRIMARY KEY (`seckill_id`),
  KEY idx_end_time (`end_time`),
  KEY idx_create_time (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'; |
--秒杀成功明细表
create table success_killed(
seckill_id bigint not null comment '秒杀商品id',
user_phone bigint not null comment '用户手机号',
state tinyint not null default -1 comment '状态显示：-1:无效 0:成功 1:以付款',
create_time timestamp NOT NULL COMMENT '创建时间',
primary key(seckill_id,user_phone),/*联合主键*/
KEY idx_create_time (`create_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表' ;

--用户登录认证相关的信息
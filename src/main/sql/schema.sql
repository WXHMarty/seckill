--数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;
--使用数据库
USE seckill;
--创建秒杀库存表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` VARCHAR(120) NOT NULL COMMENT '商品名称',
`number` INT NOT NULL COMMENT '库存数量',
`start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
`end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time (start_time),
key idx_end_time (end_time),
key idx_create_time (create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--初始化数据添加
INSERT INTO seckill 
	(name, number, start_time, end_time) 
VALUES 
	('1000元秒杀手机', 100, '2016-12-12 00:00:00', '2016-12-13 00:00:00'),
	('500元秒杀U盘', 200, '2016-12-11 00:00:00', '2016-12-12 00:00:00'),
	('2000元秒杀电脑', 50, '2016-12-10 00:00:00', '2016-12-11 00:00:00'),
	('200元秒杀电视机', 300, '2016-12-13 00:00:00', '2016-12-14 00:00:00'),
	('100元秒杀显示器', 400, '2016-12-14 00:00:00', '2016-12-15 00:00:00'),
	('40元秒杀充电器', 500, '2016-12-12 00:00:00', '2016-12-13 00:00:00'),
	('200元秒杀音响', 600, '2016-12-11 00:00:00', '2016-12-12 00:00:00'),
	('100元秒杀手表', 700, '2016-12-10 00:00:00', '2016-12-11 00:00:00'),
	('2000元秒杀冰箱', 800, '2016-12-13 00:00:00', '2016-12-14 00:00:00'),
	('3000元秒杀洗衣机', 900, '2016-12-14 00:00:00', '2016-12-15 00:00:00');
	
--秒杀成功明细表
CREATE TABLE success_killed(
`seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
`user_phone` BIGINT NOT NULL COMMENT '用户手机号',
`state` TINYINT NOT NULL COMMENT '状态标识，-1：无效  0：成功  1：已付款  2：已发货',
`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id, user_phone),/*使用联合主键*/
key idx_create_time (create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--链接数据库控制台 masql -uroot -proot

--���ݿ��ʼ���ű�

--�������ݿ�
CREATE DATABASE seckill;
--ʹ�����ݿ�
USE seckill;
--������ɱ����
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '��Ʒ���id',
`name` VARCHAR(120) NOT NULL COMMENT '��Ʒ����',
`number` INT NOT NULL COMMENT '�������',
`start_time` TIMESTAMP NOT NULL COMMENT '��ɱ����ʱ��',
`end_time` TIMESTAMP NOT NULL COMMENT '��ɱ����ʱ��',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
PRIMARY KEY (seckill_id),
key idx_start_time (start_time),
key idx_end_time (end_time),
key idx_create_time (create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='��ɱ����';

--��ʼ���������
INSERT INTO seckill 
	(name, number, start_time, end_time) 
VALUES 
	('1000Ԫ��ɱ�ֻ�', 100, '2016-12-12 00:00:00', '2016-12-13 00:00:00'),
	('500Ԫ��ɱU��', 200, '2016-12-11 00:00:00', '2016-12-12 00:00:00'),
	('2000Ԫ��ɱ����', 50, '2016-12-10 00:00:00', '2016-12-11 00:00:00'),
	('200Ԫ��ɱ���ӻ�', 300, '2016-12-13 00:00:00', '2016-12-14 00:00:00'),
	('100Ԫ��ɱ��ʾ��', 400, '2016-12-14 00:00:00', '2016-12-15 00:00:00'),
	('40Ԫ��ɱ�����', 500, '2016-12-12 00:00:00', '2016-12-13 00:00:00'),
	('200Ԫ��ɱ����', 600, '2016-12-11 00:00:00', '2016-12-12 00:00:00'),
	('100Ԫ��ɱ�ֱ�', 700, '2016-12-10 00:00:00', '2016-12-11 00:00:00'),
	('2000Ԫ��ɱ����', 800, '2016-12-13 00:00:00', '2016-12-14 00:00:00'),
	('3000Ԫ��ɱϴ�»�', 900, '2016-12-14 00:00:00', '2016-12-15 00:00:00');
	
--��ɱ�ɹ���ϸ��
CREATE TABLE success_killed(
`seckill_id` BIGINT NOT NULL COMMENT '��ɱ��Ʒid',
`user_phone` BIGINT NOT NULL COMMENT '�û��ֻ���',
`state` TINYINT NOT NULL COMMENT '״̬��ʶ��-1����Ч  0���ɹ�  1���Ѹ���  2���ѷ���',
`create_time` TIMESTAMP NOT NULL COMMENT '����ʱ��',
PRIMARY KEY (seckill_id, user_phone),/*ʹ����������*/
key idx_create_time (create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ɱ����';

--�������ݿ����̨ masql -uroot -proot

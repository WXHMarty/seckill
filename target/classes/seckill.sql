-- 秒杀执行的存储过程
DELIMITER $$ --把换行符;转换成$$
-- 定义存储过程
-- in:输入参数; out:输出参数;
-- row_count()返回上一条修改类型SQL(insert, update, delete)的影响行数
-- =0表示未修改; >0表示修改影响的行数; <0表示sql错误或未执行修改SQL
CREATE PROCEDURE `seckill`.`execute_seckill`
	(in v_seckill_id bigint, in v_phone bigint, in v_kill_time timestamp, out r_result int)
BEGIN
	DECLARE insert_count int DEFAULT 0;
	START TRANSACTION;
	INSERT ignore into success_killed
		(seckill_id,user_phone,create_time)
	VALUES
		(v_seckill_id,v_phone,v_kill_time);
	SELECT row_count() INTO insert_count;
	IF (insert_count = 0) THEN
		ROLLBACK;
		SET r_result = -1;
	ELSEIF (insert_count < 0) THEN
		ROLLBACK;
		SET r_result = -2;
	ELSE
		UPDATE seckill
		SET number = number-1
		WHERE seckill_id = v_seckill_id
			AND end_time > v_kill_time
			AND start_time < v_kill_time
			AND number > 0;
		SELECT row_count() INTO insert_count;
		IF (insert_count = 0) THEN
			ROLLBACK;
			SET r_result = 0;
		ELSEIF (insert_count < 0) THEN
			ROLLBACK;
			SET r_result = -2;
		ELSE
			COMMIT;
			SET r_result = 1;
		END IF;
	END IF;
END;
$$
-- $$ 代表存储过程定义结束

-- 下面是测试SQL语句
DELIMITER ;
set @r_result=-3;
-- 执行存储过程
call execute_seckill(1003,18801405609,now(),@r_result);
-- 获取结果
select @r_result;
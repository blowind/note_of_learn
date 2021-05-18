

---------------------------- MyBatis中使用oracle遇到的各种奇葩问题  ----------------------------
【ORA-00911: 无效字符 问题和解决】
mybatis中包含的语句不能带有分号;






 -- 建表语句
create table JZF_INSURE_REPORT (
	insure_id VARCHAR2(20) PRIMARY KEY,
	insured_name VARCHAR2(20) not null,
	sex NUMBER(1) not null,
	birthday date not null,
	cert_type NUMBER(1) not null,
	danger_course VARCHAR2(300) not null,
	accident_status NUMBER(1) not null,
	cure_hospital_name VARCHAR2(50),
	reporter_name VARCHAR2(20) not null,
	reporter_cert_number VARCHAR2(18),
	reporter_phone VARCHAR2(11) not null,
	relation VARCHAR2(10) not null,
	auth_status NUMBER(1) not null
);

CREATE TABLE FIREND(
    FID NUMBER(3),
    NAME VARCHAR2(20),
    GRADE NUMBER(3),
	isnew INT DEFAULT 0,
	PRIMARY KEY (FID),
    FOREIGN KEY(GRADE) REFERENCES QQ(GARDE) ON DELETE CASCADE
);

-- 创表后添加外键，业务上基本不用外键
alter TABLE 表名 ADD constraint   外键约束名 FOREIGN KEY(列名) REFERENCES   引用外键表(列名) 
ON UPDATE RESTRICT    --  同步更新
ON DELETE RESTRICT    --  同步删除;

-- 修改表名：
rename 旧表名 to 新表名；
rename user to newuser;

-- 删除表
drop table JZF_INSURE_REPORT;

--增加列
alter table test add address varchar2(40);

--删除列
alter table test drop column address;

--修改列的名称
alter table test modify address addresses varchar(40);

--创建自增的序列
create sequence class_seq increment by 1 start with 1 MAXVALUE 999999 NOCYCLE NOCACHE;
-- 可直接复制使用的创建序列号的sql, 在使用的时候用 PRPDEV.SEQ_CJZX_JSONLOG_ID.nextval 作为自生成的id
CREATE SEQUENCE  "PRPDEV"."SEQ_CJZX_JSONLOG_ID"  MINVALUE 1 MAXVALUE 99999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE 

--插入数据
insert into classes values(class_seq.nextval,'软件一班');

--更新数据
update stu_account set username='aaa' where count_id=2;

--创建唯一索引
create unique index username on stu_account(username);   -- 唯一索引不能插入相同的数据


-- 通过设置会话的环境变量，让Oracle对大小写不敏感
ALTER SESSION SET NLS_COMP=ANSI;
ALTER SESSION SET NLS_SORT=binary_ci;

-- 默认大小写敏感，通过上面的环境变量设置，可以做到大小写不敏感
SELECT  *  FROM tab WHERE val = 'abc123';

-- 使用LIKE进行匹配查询的，不管是否使用上面的会话设置命令，依然大小写敏感
SELECT  * FROM tab  WHERE val LIKE 'a%'; 

-- 规避LIKE大小写敏感的方法：使用正则表达式变通处理，
SELECT  * FROM   tab  WHERE  REGEXP_LIKE (val, '^a', 'i');
-- 注1：REGEXP_LIKE 的第3个参数'i' 表示大小写敏感。
-- 注2：对于环境变量NLS_COMP 与NLS_SORT的设置，会影响REGEXP_LIKE 执行的结果（假如第3个参数不填写的话）

-- 使查询结果返回中间的10到100行
select * from OB_CALL_DATA_LOG rownum<101  minus  select * from OB_CALL_DATA_LOG rownum>9


-- 修改字段类型，假设字段数据为空，则不管改为什么字段类型，可以直接执行
alter table tb modify (name nvarchar2(20));

-- 修改字段类型，假设字段有数据，则改为nvarchar2(20)可以直接执行
alter table tb modify (name nvarchar2(20));


-- 将有数据的字段类型改为varchar2，直接改回弹出“ORA-01439:要更改数据类型,则要修改的列必须为空”，使用如下方式变相变更
alter table JZF_INSURE_REPORT rename column CERT_TYPE to CERT_TYPE_tmp;
alter table JZF_INSURE_REPORT add CERT_TYPE VARCHAR2(1);
update JZF_INSURE_REPORT set CERT_TYPE = CERT_TYPE_tmp;
alter table JZF_INSURE_REPORT drop column CERT_TYPE_tmp;


-- 获取系统日期：  SYSDATE()
-- 格式化日期：    TO_CHAR(SYSDATE(),'YY/MM/DD HH24:MI:SS) 或 TO_DATE(SYSDATE(),'YY/MM/DD HH24:MI:SS)
-- 格式化数字：    TO_NUMBER

TO_CHAR  把日期或数字转换为字符串 
TO_CHAR(number, '格式') 
TO_CHAR(salary, '$99,999.99') 
TO_CHAR(date, '格式')

TO_DATE  把字符串转换为数据库中的日期类型
TO_DATE(char, '格式')

TO_NUMBER  将字符串转换为数字 
TO_NUMBER(char, '格式')


select sysdate from dual;
mi是分钟，输出 2009-12-25 14:23:31
select to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') from dual;
mm会显示月份，输出 2009-12-25 14:12:31 
select to_char(sysdate,'yyyy-MM-dd HH24:mm:ss') from dual;
输出 09-12-25 14:23:31
select to_char(sysdate,'yy-mm-dd hh24:mi:ss') from dual  
输出 2009-12-25 14:23:31


转换的格式：

表示 year 的：y 表示年的最后一位 、
                     yy 表示年的最后2位 、 
                     yyy 表示年的最后3位 、
                     yyyy 用4位数表示年

表示month的： mm 用2位数字表示月 、
                       mon 用简写形式， 比如11月或者nov 、
                       month 用全称， 比如11月或者november

表示day的：dd  表示当月第几天 、
                  ddd 表示当年第几天 、
                  dy  当周第几天，简写， 比如星期五或者fri 、
                  day 当周第几天，全称， 比如星期五或者friday

表示hour的：hh   2位数表示小时 12进制 、 
                   hh24 2位数表示小时 24小时

表示minute的：mi 2位数表示分钟

表示second的：ss 2位数表示秒 60进制

表示季度的：q 一位数 表示季度 （1-4）

另外还有ww 用来表示当年第几周 w用来表示当月第几周。

24小时制下的时间范围：00：00：00-23：59：59
12小时制下的时间范围：1：00：00-12：59：59

数字格式:  9  代表一个数字 
		   0  强制显示0 
		   $  放置一个$符 
		   L  放置一个浮动本地货币符 
		   .  显示小数点 
		   ,  显示千位指示符

select to_date('2009-12-25 14:23:31','yyyy-mm-dd,hh24:mi:ss') from dual 
-- 而如果把上式写作： select to_date('2009-12-25 14:23:31','yyyy-mm-dd,hh:mi:ss') from dual 则会报错，因为小时hh是12进制，14为非法输入，不能匹配。

select count(*) as datas from YY_GH_HYXX where pbrq between to_date(to_char(sysdate,’yyyy/mm/dd’),’yyyy/mm/dd’) and to_date(to_char(sysdate,’yyyy/dd’),’yyyy/dd’)

-- 输出 $10,000,00 ：
select to_char(1000000,'$99,999,99') from dual;

-- 输出 RMB 10,000,00 ： 
select to_char(1000000,'L99,999,99') from dual;

-- 输出 1000000.12 ：
select trunc(to_number('1000000.123'),2) from dual;
select to_number('1000000.123') from dual;


-- 在系统时间上添加12小时后，转为字符串
update FORMTABLE_MAIN_536 set INSERTTIME=SYSDATE, OFFERDEADLINE=to_char(sysdate+1/2,'yyyy-mm-dd HH24:MI:SS') where REQUESTID=4184191

加天数N可以用如下方法实现,select sysdate+N from dual ,
sysdate+1 加一天
sysdate+1/24 加1小时
sysdate+1/(24*60) 加1分钟
sysdate+1/(24*60*60) 加1秒钟
类推至毫秒0.001秒

加法 
select sysdate,add_months(sysdate,12) from dual;        --加1年 
select sysdate,add_months(sysdate,1) from dual;        --加1月 
select sysdate,to_char(sysdate+7,'yyyy-mm-dd HH24:MI:SS') from dual;  --加1星期 
select sysdate,to_char(sysdate+1,'yyyy-mm-dd HH24:MI:SS') from dual;  --加1天 
select sysdate,to_char(sysdate+1/24,'yyyy-mm-dd HH24:MI:SS') from dual;  --加1小时 
select sysdate,to_char(sysdate+1/24/60,'yyyy-mm-dd HH24:MI:SS') from dual;  --加1分钟 
select sysdate,to_char(sysdate+1/24/60/60,'yyyy-mm-dd HH24:MI:SS') from dual;  --加1秒 
减法 
select sysdate,add_months(sysdate,-12) from dual;        --减1年 
select sysdate,add_months(sysdate,-1) from dual;        --减1月 
select sysdate,to_char(sysdate-7,'yyyy-mm-dd HH24:MI:SS') from dual;  --减1星期 
select sysdate,to_char(sysdate-1,'yyyy-mm-dd HH24:MI:SS') from dual;  --减1天 
select sysdate,to_char(sysdate-1/24,'yyyy-mm-dd HH24:MI:SS') from dual;  --减1小时 
select sysdate,to_char(sysdate-1/24/60,'yyyy-mm-dd HH24:MI:SS') from dual;  --减1分钟 
select sysdate,to_char(sysdate-1/24/60/60,'yyyy-mm-dd HH24:MI:SS') from dual;  --减1秒


【批量插入】
insert all  
into student(id,name) values(1,'张三')
into student(id,name) values(2,'李四')
select 1 from dual
-- 注意最后的select不能少，否则会报语法错误，添加后只是单纯的执行select语句，没有额外影响

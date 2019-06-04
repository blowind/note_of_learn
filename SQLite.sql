sqlite_master   系统视图


SQLite  中的数据结构，因为支持动态类型，这些类型对应的列都可以存储别的类型信息

integer  自增的int类型

int

text

real

blob

null


字符串内容强烈建议用单引号界定，如果字符串内容包括单引号，使用两个连续的单引号
'Kenny''s chicken'   

科学计数法 6.0221415E23

二进制  x'0ffe'   每一位必须是16进制数

单行注释用  --
多行注释用  /*  */


SQLite中的时间/日期函数：
datetime()    --  产生日期和时间
datetime()的用法是： datetime(日期/时间,修正符,修正符...)
在时间/日期函数里可以使用如下格式的字符串作为参数：
YYYY-MM-DD
YYYY-MM-DD HH:MM
YYYY-MM-DD HH:MM:SS
YYYY-MM-DD HH:MM:SS.SSS
HH:MM
HH:MM:SS
HH:MM:SS.SSS
now
其中now是产生现在的时间。
select datetime('now');        --    得到格林威治时区的当前时间
select datetime('2006-10-17');  --    2006-10-17  12:00:00
select datetime('2006-10-17 00:20:00',' 1 hour','-12 minute');  ---  按参数顺序挨个加减
select datetime('now','start of year');    ---   2006-01-01 00:00:00  一年时间的开始
select datetime('now','start of month');   ---   2006-10-01 00:00:00  一月的开始
select datetime('now','start of day');     ---   2006-10-17 00:00:00  一天的开始
select datetime('now',' 10 hour','start of day',' 10 hour');  -- 2006-10-17 10:00:00 按参数顺序挨个处理
select datetime('now','localtime');



                ###########在系统shell中执行sqlite命令 #############

sqlite3 test.db   在外部命令行中创建一个数据库

sqlite3 test.db .dump > test.sql    数据库导出为sql语句，所谓的SQL转储

sqlite3 test.db "select * from test"   查询命令

sqlite3 test2.db < test.sql            根据test.sql中的sql语句生成test2.db数据库

sqlite3 -init test.sql test3.db .exit     生成数据库test3.db并退出CLP命令行模式    

sqlite3 test.db vacuum                    释放已删除对象的空间

               ############   SQLite 交互模式CLP         #############

.开头的是sqlite交互命令，其他都是SQL语句命令
.h 帮助
.e 退出

.tables  返回数据库中所有表和视图名字

.indices [table name]   显示一个表的索引

.schema   返回所有数据库对象(table index view triger)的定义语句

.schema test    返回test表的定义

.output file.sql   将输出重定向到file.sql文件
.output stdout     将输出重定向到标准输出

.dump  整个数据库输出为sql语句

.read  导入由SQL语句构成的文件，例如dump的导出结果

.import [file][table]  导入类似csv这类有分隔符的数据文件

.separator   指定分隔符

.show  显示用户shell中定义的所有设置，包括分隔符

.mode csv|column|html|insert|line|list|tabs|tcl   设置输出数据的格式，默认为csv

.mode column   显示格式，以列模式显示结果

.headers on    显示格式，查询结果会带有字段名

.nullvalue NULL   用一个字符串NULL来显示null值，默认是空串

.width     设置输出结果中各个列的宽度




   ！！！！！！！！！注意到下面命令里的;都不能少 ！！！！！！！！！！！！
   
创建相关::::::::::::::::::::::   
创建表：create [temp] table table_name (column_definitions [, constraints]);
			  临时          表名           列定义				
											
								
create table test (id integer primary key, value text);   创建表test，带两列

create table contacts (id integer primary key,
						name text not null collate nocase,       此列不能为null，排序不分大小写
						phone text not null default 'UNKNOWN',
						unique (name, phone) );      表级别的约束


创建索引
create index test_idx on test (value);    创建索引


创建视图
create view schema as select * from sqlite_master;    创建视图


修改相关:::::::::::::
修改表：alter table table_name { rename to name | add column column_def }
                              修改表名             增加新列
							  
alter table contacts add column email text not null default '' collate nocase;
修改表contacts，增加列email，类型为text，非空，默认值为''，不区分大小写

插入：
insert into test (id, value) values(1, 'eenie');  
向表中插入数据，前一个圆括号内指定要插入的列，后一个圆括号内指定插入的值


查找相关:::::::::::::::::::
查找表：  select [distinct] heading from tables     参数为表或者视图
									where predicate
									group by columns
									having predicate
									order by columns
									limit count, offset;



select * from test;   显示所有结果

select last_insert_rowid();        最后插入的自动增量值

select type, name, tbl_name sql from sqlite_master order by type;    
查找系统视图数据，获得类型，名字，对应的表，生成时的sql语句

select * from test where value like 'm%';
查找test表中以字母m开头的值


删除:
delete from test where id=1;

drop table test;    删除表test

drop view schema;      删除视图







SELECT

UPDATE

SELECT ON REPLACE

ATTACH :   将第二个数据库附到当前连接
ATTACH database bar.db as bar;



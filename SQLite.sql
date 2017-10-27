sqlite_master   ϵͳ��ͼ


SQLite  �е����ݽṹ����Ϊ֧�ֶ�̬���ͣ���Щ���Ͷ�Ӧ���ж����Դ洢���������Ϣ

integer  ������int����

int

text

real

blob

null


�ַ�������ǿ�ҽ����õ����Ž綨������ַ������ݰ��������ţ�ʹ�����������ĵ�����
'Kenny''s chicken'   

��ѧ������ 6.0221415E23

������  x'0ffe'   ÿһλ������16������

����ע����  --
����ע����  /*  */


SQLite�е�ʱ��/���ں�����
datetime()    --  �������ں�ʱ��
datetime()���÷��ǣ� datetime(����/ʱ��,������,������...)
��ʱ��/���ں��������ʹ�����¸�ʽ���ַ�����Ϊ������
YYYY-MM-DD
YYYY-MM-DD HH:MM
YYYY-MM-DD HH:MM:SS
YYYY-MM-DD HH:MM:SS.SSS
HH:MM
HH:MM:SS
HH:MM:SS.SSS
now
����now�ǲ������ڵ�ʱ�䡣
select datetime('now');        --    �õ���������ʱ���ĵ�ǰʱ��
select datetime('2006-10-17');  --    2006-10-17  12:00:00
select datetime('2006-10-17 00:20:00',' 1 hour','-12 minute');  ---  ������˳�򰤸��Ӽ�
select datetime('now','start of year');    ---   2006-01-01 00:00:00  һ��ʱ��Ŀ�ʼ
select datetime('now','start of month');   ---   2006-10-01 00:00:00  һ�µĿ�ʼ
select datetime('now','start of day');     ---   2006-10-17 00:00:00  һ��Ŀ�ʼ
select datetime('now',' 10 hour','start of day',' 10 hour');  -- 2006-10-17 10:00:00 ������˳�򰤸�����
select datetime('now','localtime');



                ###########��ϵͳshell��ִ��sqlite���� #############

sqlite3 test.db   ���ⲿ�������д���һ�����ݿ�

sqlite3 test.db .dump > test.sql    ���ݿ⵼��Ϊsql��䣬��ν��SQLת��

sqlite3 test.db "select * from test"   ��ѯ����

sqlite3 test2.db < test.sql            ����test.sql�е�sql�������test2.db���ݿ�

sqlite3 -init test.sql test3.db .exit     �������ݿ�test3.db���˳�CLP������ģʽ    

sqlite3 test.db vacuum                    �ͷ���ɾ������Ŀռ�

               ############   SQLite ����ģʽCLP         #############

.��ͷ����sqlite���������������SQL�������
.h ����
.e �˳�

.tables  �������ݿ������б����ͼ����

.indices [table name]   ��ʾһ���������

.schema   �����������ݿ����(table index view triger)�Ķ������

.schema test    ����test��Ķ���

.output file.sql   ������ض���file.sql�ļ�
.output stdout     ������ض��򵽱�׼���

.dump  �������ݿ����Ϊsql���

.read  ������SQL��乹�ɵ��ļ�������dump�ĵ������

.import [file][table]  ��������csv�����зָ����������ļ�

.separator   ָ���ָ���

.show  ��ʾ�û�shell�ж�����������ã������ָ���

.mode csv|column|html|insert|line|list|tabs|tcl   ����������ݵĸ�ʽ��Ĭ��Ϊcsv

.mode column   ��ʾ��ʽ������ģʽ��ʾ���

.headers on    ��ʾ��ʽ����ѯ���������ֶ���

.nullvalue NULL   ��һ���ַ���NULL����ʾnullֵ��Ĭ���ǿմ�

.width     �����������и����еĿ��




   ������������������ע�⵽�����������;�������� ������������������������
   
�������::::::::::::::::::::::   
������create [temp] table table_name (column_definitions [, constraints]);
			  ��ʱ          ����           �ж���				
											
								
create table test (id integer primary key, value text);   ������test��������

create table contacts (id integer primary key,
						name text not null collate nocase,       ���в���Ϊnull�����򲻷ִ�Сд
						phone text not null default 'UNKNOWN',
						unique (name, phone) );      �����Լ��


��������
create index test_idx on test (value);    ��������


������ͼ
create view schema as select * from sqlite_master;    ������ͼ


�޸����:::::::::::::
�޸ı�alter table table_name { rename to name | add column column_def }
                              �޸ı���             ��������
							  
alter table contacts add column email text not null default '' collate nocase;
�޸ı�contacts��������email������Ϊtext���ǿգ�Ĭ��ֵΪ''�������ִ�Сд

���룺
insert into test (id, value) values(1, 'eenie');  
����в������ݣ�ǰһ��Բ������ָ��Ҫ������У���һ��Բ������ָ�������ֵ


�������:::::::::::::::::::
���ұ�  select [distinct] heading from tables     ����Ϊ�������ͼ
									where predicate
									group by columns
									having predicate
									order by columns
									limit count, offset;



select * from test;   ��ʾ���н��

select last_insert_rowid();        ��������Զ�����ֵ

select type, name, tbl_name sql from sqlite_master order by type;    
����ϵͳ��ͼ���ݣ�������ͣ����֣���Ӧ�ı�����ʱ��sql���

select * from test where value like 'm%';
����test��������ĸm��ͷ��ֵ


ɾ��:
delete from test where id=1;

drop table test;    ɾ����test

drop view schema;      ɾ����ͼ







SELECT

UPDATE

SELECT ON REPLACE

ATTACH :   ���ڶ������ݿ⸽����ǰ����
ATTACH database bar.db as bar;



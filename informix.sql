
 
 create table "informix".testdb
(
 id integer not null,
 customerid varchar(32),
 businesssum decimal(24,6),    
 createtime DATE NOT NULL DEFAULT TODAY,
 PRIMARY KEY (id)
)

  create table "informix".testfield
(
 id integer not null,
 customerid varchar(32),
 ProfitRate NUMERIC(9,6),
 InputDate DATE,
 StatisticsYM DATETIME year to second,
 InsertTimeForHis TIMESTAMP(0) WITHOUT TIME ZONE,
 Tkey TIMESTAMP(0),
 anotherId long varbit,
 PRIMARY KEY (id)
)

  create table "informix".testfield
(
 id integer not null,
 customerid varchar(32),
 ProfitRate NUMERIC(9,6),
 InputDate DATE,
 StatisticsYM DATETIME year to second,
 PRIMARY KEY (id)
)

 
insert into testdb(id, customerid, businesssum, createtime) values(1, '1234', 1.23, '2020-06-04');

insert into testfield(id, customerid, ProfitRate, InputDate, StatisticsYM)  values (1, 'hahah', 321.123456, 2020-06-29, '2020-06-29 16:27:54')

-- 删表
drop table testdb;

-- 创建索引
create index idx_pkey_prpcaddress on prpcaddress(pkey);

-- 修改表锁为行锁
alter table prpcitem lock mode(ROW);

-- 新增列字段
alter table  prpcengage add relatedcontent varchar(50);
alter table prpcinsurednature add  usedamount numeric(20,2);

-- 修改列字段
alter table prpcitemkind modify calpremium numeric(24,2);


-- 查看表是否有死锁
切换到 sysmaster数据库，使用如下sql，其中dbsname根据操作的数据库名修改
select *  from  syslocks where  dbsname="zj3300prp3gdb"
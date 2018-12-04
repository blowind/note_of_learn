create table t_role(
	id int(12) not null auto_increment,
	role_name varchar(60) not null,
	note varchar(256),
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table t_user (
	id int(12) not null auto_increment,
	user_name varchar(60) not null,
	pwd varchar(100) not null,
	available INT(1) DEFAULT 1 CHECK(available IN (0, 1)),
	note varchar(256),
	primary key (id),
	unique(user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table t_user_role (
	id int(12) not null auto_increment,
	role_id int(12) not null,
	user_id int(12) not null,
	primary key (id),
	unique(role_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table t_user_role add constraint FK_Reference_1 foreign key
(role_id) references t_role (id) on delete restrict on update restrict;
alter table t_user_role add constraint FK_Reference_2 foreign key
(user_id) references t_user (id) on delete restrict on update restrict;

INSERT INTO `t_role` VALUES ('1', 'admin', 'adminnnn');
INSERT INTO `t_role` VALUES ('2', 'user', 'userrrrr');

INSERT INTO `t_user` VALUES ('1', 'hahaha', '52377d7a9632bd50bd5d3efbd17adc0c4d33169ac04e5b8119f7d924ed166c8dda64298518a3f93d', '1', 'jiluyixia');

INSERT INTO `t_user_role` VALUES ('1', '1', '1');
INSERT INTO `t_user_role` VALUES ('2', '2', '1');
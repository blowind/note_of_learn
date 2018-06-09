insert into SYS_USER (id, username, password) VALUES (1, "zxf", "zxf");
insert into SYS_USER (id, username, password) VALUES (2, "wisely", "wisely");

insert into SYS_ROLE (id, name) VALUES (1, "ROLE_ADMIN");
insert into SYS_ROLE (id, name) VALUES (2, "ROLE_USER");

insert into SYS_USER_ROLES (SYS_USER_ID, ROLES_ID) VALUES (1, 1);
insert into SYS_USER_ROLES (SYS_USER_ID, ROLES_ID) VALUES (2, 2);
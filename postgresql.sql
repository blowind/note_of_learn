-- LIKE关键字里面拼接字符串
SELECT * FROM public."user" WHERE UPPER(username) LIKE UPPER('%' || #{username} || '%');

SELECT * FROM public."user" WHERE CONCAT(username) LIKE CONCAT('%', #{username}, '%');

SELECT * FROM public."user" WHERE username LIKE '%' || #{username} || '%';

-- regiontag字段为空的时候替换成其他值
select lng, lat, COALESCE(NULLIF(trim(regiontag), ''), '无') regiontag  from prplregistgeozjsub_next
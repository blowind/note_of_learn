字符串操作：
SET   		set hello word
GET   		get hello
DEL   		del hello

列表操作：
RPUSH  		rpush list-key item 
			rpush list-key item2
			rpush list-key item
LRANGE 		lrange list-key 0 -1
LINDEX 		lindex list-key 1
RPOP   		lpop list-key

集合操作：
SADD     	sadd set-key item
			sadd set-key item2
			sadd set-key item3
SMEMBERS    smembers set-key
SISMEMBER   sismember set-key item4
SREM		srem set-key item2


散列操作：
HSET		hset hash-key sub-key1 value1
			hset hash-key sub-key2 value2
			hset hash-key sub-key1 value1
HGETALL		hgetall hash-key
HGET		hget hash-key sub-key1
HDEL		hdel hash-key sub-key2

有序集合操作：
ZADD				zadd zset-key 728 member1
					zadd zset-key 982 member0
ZRANGE				zrange zset-key 0 -1 withscores
ZRANGEBYSOCRE		zrangebyscore zset-key 0 800 withscores
ZREM				zrem zset-key member1
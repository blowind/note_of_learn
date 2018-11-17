package com.zxf.berkeley.db.config;

import com.sleepycat.je.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: BerkeleyDBConfig
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 17:27
 * @Version: 1.0
 **/
@Configuration
public class BerkeleyDBConfig {

    private String fileName;

    private String dbName;

    public Database database(@Autowired Environment env) {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        /*如果设置为true,则支持事务处理，默认是false，不支持事务*/
        dbConfig.setTransactional(true);
        /*以只读方式打开database,默认是false*/
        dbConfig.setReadOnly(false);
        /*以独占的方式打开，也就是说同一个时间只能有一实例打开这个database。如果true，只能创建，这样如果db存在的话，则打开失败*/
        dbConfig.setExclusiveCreate(false);
        /*设置一个key是否允许存储多个值，true代表允许，默认false*/
        dbConfig.setSortedDuplicates(false);
        /*true为进行缓冲写库，false则不进行缓冲写库*/
        dbConfig.setDeferredWrite(true);

        Database db = env.openDatabase(null, dbName, dbConfig);
        return db;
    }

    public Environment environment() throws DatabaseException {
        EnvironmentConfig eConfig = new EnvironmentConfig();
        /*数据库不存在的时候创建一个*/
        eConfig.setAllowCreate(true);
        eConfig.setTransactional(true);
        eConfig.setReadOnly(false);
        eConfig.setTxnTimeout(10000, TimeUnit.MILLISECONDS);
        eConfig.setLockTimeout(10000, TimeUnit.MILLISECONDS);

//        EnvironmentMutableConfig emConfig = new EnvironmentMutableConfig();
//        /*设置je的cache占用jvm内存的百分比*/
//        emConfig.setCachePercent(50);
//        /*设定缓存的大小为123456Bytes*/
//        emConfig.setCacheSize(123456);
//        /*设定事务提交时是否写更改的数据到磁盘，true不写磁盘*/
//        emConfig.setTxnNoSyncVoid(true);
//        /*设定事务在提交时是否写缓冲的log到磁盘。如果写磁盘会影响性能，不写会影响事务的安全*/
//        emConfig.setTxnWriteNoSyncVoid(false);

        File file = new File(fileName);
        if(!file.exists()) {
            file.mkdir();
        }

        Environment env = new Environment(file, eConfig);
        return env;
    }
}

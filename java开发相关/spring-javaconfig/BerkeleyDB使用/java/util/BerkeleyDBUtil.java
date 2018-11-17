package com.zxf.berkeley.db.util;

import com.sleepycat.je.*;

import java.io.IOException;

/**
 * @ClassName: BerkeleyDBUtil
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/17 18:51
 * @Version: 1.0
 **/
@Component
public class BerkeleyDBUtil {

    public boolean writeString(String key, String value, boolean isOverwrite) {
        try{
            DatabaseEntry keyEntry = new DatabaseEntry(key.trim().getBytes("UTF-8"));
            DatabaseEntry valueEntry = new DatabaseEntry(value.getBytes("UTF-8"));
    }catch (IOException ioe) {
            ioe.printStackTrace();

            OperationStatus res = null;
            Transaction trans = null;
            try{
                TransactionConfig txConfig = new TransactionConfig();
                txConfig.setSerializableIsolation(true);
                trans =
            }
        }
    }
}

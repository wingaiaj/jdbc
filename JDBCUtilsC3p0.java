package com.zx.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

/**
 * @ClassName JDBCUtilsc3p0
 * @Description TODO
 * @Author xpower
 * @Date 2022/5/31 20:32
 * @Version 1.0
 */
public class JDBCUtilsC3p0 {

        //c3p0数据库连接池
     private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("hellc3p0");
    public static Connection getConnection()throws Exception{
        return comboPooledDataSource.getConnection();

    }
}

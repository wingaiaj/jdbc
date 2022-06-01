package com.zx.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ClassName JDBCUtilsDruid
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/1 10:13
 * @Version 1.0
 */
public class JDBCUtilsDruid {
    //德鲁伊数据库连接池
     private static DataSource dataSource = null;
    static {
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            Properties properties = new Properties();
            properties.load(is);

            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}


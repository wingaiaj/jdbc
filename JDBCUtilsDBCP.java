package com.zx.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @ClassName JDBCUtilsDBCP
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/1 9:18
 * @Version 1.0
 */
public class JDBCUtilsDBCP {
    //DBCP数据库连接池
    private static DataSource dataSource = null;

    static {
        try {
            Properties pros = new Properties();
            //方式一
            //InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
            //方式二
            FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(is);
            dataSource = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}

package com.zx.transaction;

import com.zx.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @ClassName Connection
 * @Description TODO
 * @Author xpower
 * @Date 2022/5/30 15:06
 * @Version 1.0
 */
public class ConnectionTest {
    @Test
    public void testGetConnection()throws Exception{
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection);
    }

}

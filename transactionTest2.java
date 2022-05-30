package com.zx.transaction;

import com.zx.util.JDBCUtils;
import org.junit.Test;
import com.zx.transaction.User;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TransationTest2
 * @Description TODO
 * @Author xpower
 * @Date 2022/5/30 20:27
 * @Version 1.0
 */
public class transactionTest2 {
    @Test
    public void testTransactionSelect() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            //获取当前连接的隔离级别
            System.out.println(connection.getTransactionIsolation());
            //设置当前数据库的隔离级别
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            //取消自动提交数据
            connection.setAutoCommit(false);

            String sql = "select user,password,balance from user_table where user = ?";
            User CC = getInstance(connection, User.class, sql, "CC");
            System.out.println(CC);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JDBCUtils.closeResource(connection, null);
        }
    }

    @Test
    public void testTransactionUpdate() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            //取消自动提交数据
            connection.setAutoCommit(false);
            String sql = "update user_table set balance = ? where user = ?";
            update(connection, sql, 5000, "CC");
            Thread.sleep(15000);
            System.out.println("修改结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JDBCUtils.closeResource(connection, null);
        }
    }

    public <T> T getInstance(Connection connection, Class<T> clazz, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            //预编译sql语句
            preparedStatement = connection.prepareStatement(sql);
            //设置占位符
            for (int i = 0; i < args.length; i++) {

                preparedStatement.setObject(i + 1, args[0]);
            }
            //执行sql获取结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取列数
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {//判断结果集下一位是否有元素
                //获取当前形参对象
                T t = clazz.newInstance();
                //取出每行的数据
                for (int i = 0; i < columnCount; i++) {
                    //获取列的参数
                    Object value = resultSet.getObject(i + 1);
                    //获取列别名
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //反射给对象赋值
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    //设置位可编辑
                    declaredField.setAccessible(true);
                    //将当前对象属性 赋值
                    declaredField.set(t, value);
                }
                // 返回当前对象
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }

    //通用的增删改操作
    public boolean update(Connection connection, String sql, Object... args) {

        java.sql.PreparedStatement preparedStatement = null;
        try {

            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //设置
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行sql
            int i = preparedStatement.executeUpdate();
            if (i > 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(null, preparedStatement);
        }
        return false;
    }

}

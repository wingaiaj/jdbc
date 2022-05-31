package com.zx.dao2;

import com.zx.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName BaseDAO
 * @Description TODO
 * @Author xpower
 * @Date 2022/5/31 8:20
 * @Version 1.0
 */
//封装了针对于数据表的通用的操作  优化版 version-3.0
    //子类定义父类的泛型参数
public abstract class BaseDAO<T> {
    private Class<T> clazz = null;

    //代码块随着子类对象的创建执行     子类对象创建 子类构造器()->super()代码块执行 this当前子类对象 获取当前子类的运行时类的实例 this获取当前父类的泛型参数
    //
    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();//获取当前调用者的父类泛型参数
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;//
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();//获取父类的泛型参数 可能有多个返回数组
        clazz = (Class<T>) actualTypeArguments[0];
    }

    //返回数据表中的单条记录 version2.0（包括事物）
    public T getInstance(Connection connection, String sql, Object... args) {
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

    // version2.0（包括事物）
    //通用的增删改操作
    public boolean update(Connection connection, String sql, Object... args) {

        PreparedStatement preparedStatement = null;
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

    //返回数据表中的多条记录 version2.0（包括事物）
    public List<T> getForList(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //返回结果集
            resultSet = preparedStatement.executeQuery();
            //结果集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取列数
            int columnCount = metaData.getColumnCount();
            //创建集合
            list = new ArrayList<>();
            while (resultSet.next()) {
                //创建对象
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);
                    String LabelName = metaData.getColumnLabel(i + 1);
                    Field declaredField = clazz.getDeclaredField(LabelName);
                    declaredField.setAccessible(true);
                    declaredField.set(t, value);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }

    public <E> E getValue(Connection connection, String sql, Object... args) {
        //预编译sql
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行sql

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return (E) resultSet.getObject(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }
}

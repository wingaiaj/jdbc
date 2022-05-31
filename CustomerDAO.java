package com.zx.dao2;

import com.zx.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @ClassName CustomerDAO
 * @Description TODO
 * @Author xpower
 * @Date 2022/5/31 8:43
 * @Version 1.0
 */
//此接口用于规范针对于customers表的常用操作
public interface CustomerDAO {
    //将customer对象添加到数据库中
    void insert(Connection connection, Customer customer);

    //删除数据表
    void deleteById(Connection connection, int id);

    //更新数据表
    void update(Connection connection, Customer customer);

    //单行数据
    Customer getCustomerById(Connection connection, int id);

    //查询多行记录
    List<Customer> getAll(Connection connection);

    //返回数据条目数
    long getCount(Connection connection);

    //返回生日最大的
    Date getMaxBirth(Connection connection);

}

package com.zx.dao2;

import com.zx.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @ClassName CustomerDAOImpl
 * @Description TODO
 * @Author xpower
 * @Date 2022/5/31 9:34
 * @Version 1.0
 */
public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {

    @Override
    public void insert(Connection connection, Customer customer) {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        update(connection, sql, customer.getName(), customer.getEmail(), customer.getBirth());
    }

    @Override
    public void deleteById(Connection connection, int id) {
        String sql = "delete from customers where id = ?";
        update(connection, sql, id);
    }

    @Override
    public void update(Connection connection, Customer customer) {
        String sql = "update customers set name =? ,email = ?, birth = ? where id = ? ";
        update(connection, sql, customer.getName(), customer.getEmail(), customer.getBirth(), customer.getId());
    }

    @Override
    public Customer getCustomerById(Connection connection, int id) {
        String sql = "select name,email,birth from customers where id = ?";
        return getInstance(connection, sql, id);
    }

    @Override
    public List<Customer> getAll(Connection connection) {
        String sql = "select id,name,email,birth from customers";
        List<Customer> forList = getForList(connection, sql);
        return forList;

    }

    @Override
    public long getCount(Connection connection) {
        String sql = "select count(id) from customers";
        return getValue(connection, sql);
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        //String sql = "select MAX(birth) from ?";
        // return getInstance(connection,Customer.class,sql,"customers").getBirth();
        String sql = "select MAX(birth) from customers";
        return getValue(connection, sql);
    }
}

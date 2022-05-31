package com.zx.dao2.junit;

import com.zx.bean.Customer;
import com.zx.dao2.CustomerDAOImpl;
import com.zx.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @ClassName CustomerDAOImplTest
 * @Description TODO
 * @Author xpower
 * @Date 2022/5/31 10:55
 * @Version 1.0
 */
public class CustomerDAOImplTest {
    private CustomerDAOImpl dao = new CustomerDAOImpl();

    @Test
    public void insert() {//通过
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(1, "嘉然", "a-soul@126.com", new Date(1009555200L));
            dao.insert(connection, customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    @Test
    public void deleteById() {//通过

        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            dao.deleteById(connection, 19);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    @Test
    public void update() {//通过
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(28, "zx222", "b1126@.com", new Date(42222L));
            dao.update(connection, customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    @Test
    public void getCustomerById() {//通过

        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customerById = dao.getCustomerById(connection, 32);
            System.out.println(customerById);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);

        }
    }

    @Test
    public void getAll() {//.
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            List<Customer> all = dao.getAll(connection);
            all.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    @Test
    public void getCount() {//通过
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            long count = dao.getCount(connection);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    @Test
    public void getMaxBirth() {

        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(connection);
            System.out.println(maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }
}

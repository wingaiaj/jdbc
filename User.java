package com.zx.transaction;

/**
 * @ClassName User
 * @Description TODO
 * @Author xpower
 * @Date 2022/5/30 20:35
 * @Version 1.0
 */
public class User {
    private String user;
    private String password;
    private int balance;

    public User(String user, String password, int balance) {
        this.user = user;
        this.password = password;
        this.balance = balance;
    }

    public User() {
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}

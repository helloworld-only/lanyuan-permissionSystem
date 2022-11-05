package com.authSys.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.authSys.entity.UserEntity;
import com.authSys.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class Client {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("component-spring.xml");
        UserService  userService = (UserService) context.getBean("userService");
        UserEntity user = userService.getById(1);
        System.out.println(user);
//        dao();
    }

    public static void dao(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("component-spring.xml");
        DruidDataSource  dataSource = (DruidDataSource) context.getBean("dataSource");
        System.out.println(dataSource.getPassword());
        System.out.println(dataSource.getUsername());

        DruidPooledConnection connection = null;
        try {
            connection = dataSource.getConnection(100);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(connection);
    }
}

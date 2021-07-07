package com.example.springbootdemo.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Properties;

public class JdbcUtil {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(JdbcUtil.class);

    /**
     * 开发专用
     *
     * #jdbc.driverClassName=org.sqlite.JDBC
     * #jdbc.url=jdbc:sqlite:WEB-APPPATH/db/jes2.db #jdbc.username=
     * #jdbc.password=
     */
    private static JdbcTemplate freeJdbcTemplate = null;

    public static JdbcTemplate getFreeJdbcTemplate() {
        if (freeJdbcTemplate != null) {
            return freeJdbcTemplate;
        }
        Properties jdbcProperties = new Properties();
        try {
            jdbcProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc4d.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driver = jdbcProperties.getProperty("jdbc.driverClassName");
        String url = jdbcProperties.getProperty("jdbc.url");
        String user = jdbcProperties.getProperty("jdbc.username");
        String pass = jdbcProperties.getProperty("jdbc.password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //SingleConnectionDataSource dataSource = new SingleConnectionDataSource(url, user, pass, true);
        //freeJdbcTemplate = new JdbcTemplate(dataSource);

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(pass);
        freeJdbcTemplate = new JdbcTemplate(druidDataSource);
        return freeJdbcTemplate;
    }
}
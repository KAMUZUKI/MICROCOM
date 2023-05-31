package com.mu.utils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @author MUZUKI
 * @Classname DataSourceUtil
 * @Description TODO
 * @Date 2023/5/22 11:34
 */

public class MySQLDataSourceUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/microcom?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String USER = "root";
    private static final String PASSWORD = "muzuki";

    public static MysqlDataSource getDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        mysqlDataSource.setUser(USER);
        mysqlDataSource.setPassword(PASSWORD);
        return mysqlDataSource;
    }
}
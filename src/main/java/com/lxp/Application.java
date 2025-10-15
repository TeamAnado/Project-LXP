package com.lxp;

import com.lxp.config.DBConfig;
import com.lxp.exception.LXPExceptionHandler;

import java.sql.Connection;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             DBConfig dbConfig = new DBConfig();
             Connection connection = dbConfig.getConnection()) {

            //todo 기능 정의

        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
    }
}

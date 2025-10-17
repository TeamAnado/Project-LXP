package com.lxp;

import com.lxp.global.config.DBConfig;
import com.lxp.global.exception.LXPExceptionHandler;
import java.sql.Connection;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             Connection connection = DBConfig.getInstance().getConnection()) {

            //todo 기능 정의

        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
    }
}

package com.tz_tech.module;

import com.tz_tech.module.business.flow.RegisterServiceRepository;
import com.tz_tech.module.common.utils.BaseInfoLoadFromDB;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        //启动生产者消费者模式不断扫描流程表
        RegisterServiceRepository repository = new RegisterServiceRepository();
        repository.init();
        //从数据库里加载一些数据缓存起来
        BaseInfoLoadFromDB load = new BaseInfoLoadFromDB();
        load.init();
    }
}

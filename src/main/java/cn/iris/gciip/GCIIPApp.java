package cn.iris.gciip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("cn.iris.gciip.mapper")
public class GCIIPApp {

    public static void main(String[] args) {
        SpringApplication.run(GCIIPApp.class, args);
    }

}

package net.gittab.fsmsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author rookiedev
 */
@SpringBootApplication
@MapperScan(basePackages = "net.gittab.fsmsample.mapper")
public class FsmSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FsmSampleApplication.class, args);
    }

}

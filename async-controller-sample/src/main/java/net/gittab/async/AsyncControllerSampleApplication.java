package net.gittab.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AsyncControllerSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncControllerSampleApplication.class, args);
	}

}

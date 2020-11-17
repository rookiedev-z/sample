package net.gittab.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author rookiedev.
 */
@ServletComponentScan
@SpringBootApplication
public class DistributedLockSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedLockSampleApplication.class, args);
	}

}
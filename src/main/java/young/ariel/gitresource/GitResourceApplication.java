package young.ariel.gitresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@EnableFeignClients
@ConfigurationPropertiesScan
@SpringBootApplication
public class GitResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitResourceApplication.class, args);
	}

}
